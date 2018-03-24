package com.datascope.ui.login;

import com.datascope.bounded.contexts.site.domain.Site;
import com.datascope.bounded.contexts.site.service.interfaces.ISiteService;
import com.datascope.bounded.contexts.site.service.interfaces.callbacks.GetSitesCallback;
import com.datascope.bounded.contexts.user.domain.User;
import com.datascope.bounded.contexts.user.service.interfaces.IUserService;
import com.datascope.bounded.contexts.user.service.interfaces.callbacks.GetUsersCallback;
import com.datascope.bounded.contexts.user.service.interfaces.callbacks.LoginUserCallback;
import com.datascope.ui.generated.LoginDesign;
import com.datascope.ui.utils.notifications.Notifications;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

import javax.annotation.PostConstruct;
import java.util.Set;

@UIScope
@SpringView(name = LoginView.NAME)
public class LoginView extends LoginDesign implements View, LoginUserCallback, GetUsersCallback, GetSitesCallback {

    static final String NAME = "LoginView";

    private IUserService service;
    private ISiteService siteService;
    private Notifications notification;

    public LoginView(
            IUserService userService,
            ISiteService siteService,
            Notifications notification) {
        this.service = userService;
        this.siteService = siteService;
        this.notification = notification;
    }

    @PostConstruct
    private void init() {
        initComponents();
        loadSites();
    }

    private void loadSites() {
        siteService.getSites(this);
    }

    @Override
    public void loginSuccess(User user) {
        notification.success(user.getToken());
       // getUI().getNavigator().navigateTo(AreasView.NAME);
    }

    private void loadUsers() {
        service.getUsers(this);
    }

    @Override
    public void usersFound(User.List users) {
        getUsersGrid().setItems(users);
        getUsersGrid().select(users.get(0));
    }

    @Override
    public void sitesFound(Site.List sites) {
        getCbSites().setItems(sites);
        getCbSites().setSelectedItem(sites.get(0));
    }

    private void loginUser(int userId){
        service.login(userId, getEtPassword().getValue(), this);
    }

    //region init components
    private void initComponents() {
        getBtnLogin().addClickListener((e) -> {
            Set<User> users = getUsersGrid().getSelectedItems();
            if (!users.isEmpty()) {
                User selected = users.iterator().next();
                loginUser(selected.getId());
            }
        });

        getCbSites().addValueChangeListener((e) -> {
            Site selected = e.getValue();

//            // TODO: Make sure scope is ok!!! avoid STATIC SHIT
//            Rest.setSiteId(selected.getId());

            loadUsers();
        });
    }
    //endregion init components

    //region error notifications
    @Override
    public void loginFailed() {
        notification.warn("login.failed");
    }

    @Override
    public void sitesNotFound() {
        notification.error("no.sites.found");
    }

    @Override
    public void usersNotFound() {
        getUsersGrid().setItems(User.List.empty());
        //notification.tray("no.users.on.site");
        // TODO: deosn't work message on load for some reason??
    }
    //endregion error notification
}
