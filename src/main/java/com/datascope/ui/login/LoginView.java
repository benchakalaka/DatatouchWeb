package com.datascope.ui.login;

import com.datascope.bounded.contexts.core.services.concrete.CookieRepository;
import com.datascope.bounded.contexts.site.domain.Site;
import com.datascope.bounded.contexts.site.service.interfaces.ISiteService;
import com.datascope.bounded.contexts.site.service.interfaces.callbacks.GetSitesCallback;
import com.datascope.bounded.contexts.sitesettings.domain.UserSettings;
import com.datascope.bounded.contexts.sitesettings.service.interfaces.ISiteSettingsService;
import com.datascope.bounded.contexts.user.domain.User;
import com.datascope.bounded.contexts.user.service.interfaces.IUserService;
import com.datascope.bounded.contexts.user.service.interfaces.callbacks.LoginUserCallback;
import com.datascope.ui.generated.LoginDesign;
import com.datascope.ui.login.controller.LoginViewController;
import com.datascope.ui.login.elements.UserSettingsLoginGridItem;
import com.datascope.ui.utils.notifications.Notifications;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.Set;

@UIScope
@SpringView(name = LoginView.NAME)
public class LoginView extends LoginDesign implements View, LoginUserCallback, GetSitesCallback {

    public static final String NAME = "LoginView";

    private IUserService service;
    private ISiteService siteService;
    private ISiteSettingsService siteSettingsService;
    private Notifications notification;

    private LoginViewController controller;

    private CookieRepository cookieRepository;

    private int initialSiteId;

    public LoginView(
            IUserService userService,
            ISiteService siteService,
            ISiteSettingsService siteSettingsService,
            Notifications notification,
            LoginViewController controller,
            CookieRepository cookieRepository) {
        this.service = userService;
        this.siteService = siteService;
        this.siteSettingsService = siteSettingsService;
        this.notification = notification;
        this.controller = controller;
        this.cookieRepository = cookieRepository;

        initialSiteId = cookieRepository.getSiteId();
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
    public void sitesFound(Site.List sites) {
        getCbSites().setItems(sites);
        if (CollectionUtils.isNotEmpty(sites)) {
            Site selected = sites.findSiteById(initialSiteId).orElse(sites.get(0));
            getCbSites().setSelectedItem(selected);
        }
    }

    private void loadSiteUsers() {
        siteSettingsService.getUserSettings(this::setupUserSettings);
    }

    private void setupUserSettings(UserSettings.List userSettingsList) {
        if (userSettingsList.isEmpty()) {
            notification.tray("no.users.on.site");
        }

        controller.setupGridItems(getUsersGrid(), userSettingsList);
    }

    private void loginUser(int userId){
        service.login(userId, getEtPassword().getValue(), this);
    }

    @Override
    public void loginSuccess(User user) {
        notification.success(user.getToken());
        cookieRepository.setLoginInfo(user.getId(), user.getToken());

        // redirect to root url
        getUI().getPage().setLocation("/");
    }

    //region init components
    private void initComponents() {
        controller.initGrid(getUsersGrid());
        controller.initSitesCombobox(getCbSites(), site -> {
            cookieRepository.setSiteId(site.getId());
            if (initialSiteId != site.getId()) {
                // User click selection
                // reload this page
                getUI().getPage().setLocation("/");
            } else {
                loadSiteUsers();
            }
        });

        getBtnLogin().addClickListener((e) -> {
            Set<UserSettingsLoginGridItem> users = getUsersGrid().getSelectedItems();
            if (!users.isEmpty()) {
                UserSettingsLoginGridItem selected = users.iterator().next();
                loginUser(selected.getId());
            }
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
    //endregion error notification
}
