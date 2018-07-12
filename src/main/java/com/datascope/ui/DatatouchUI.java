package com.datascope.ui;

import com.datascope.bounded.contexts.core.services.concrete.CookieRepository;
import com.datascope.ui.utils.factories.LoginBuilder;
import com.datascope.ui.utils.factories.MenuBuilder;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

@SpringUI
@Title("Datatouch")
@Theme("datascope")
// TODO: Mapper
// TODO: EventBus
// TODO: Inject Strings directly
// TODO: UnitTests
// TODO: Regression UI Tests
// TODO: Loading Progress Views
// TODO: Login
// TODO: Get all params from request
// TODO: Load Area
public class DatatouchUI extends UI {

    private MenuBuilder menuBuilder;
    private LoginBuilder loginBuilder;
    private CookieRepository cookieRepository;

    public DatatouchUI(MenuBuilder menuBuilder, LoginBuilder loginBuilder, CookieRepository cookieRepository) {
        this.menuBuilder = menuBuilder;
        this.loginBuilder = loginBuilder;
        this.cookieRepository = cookieRepository;
    }

    @Override
    protected void init(VaadinRequest request) {
        cookieRepository.setupInitialValuesIfNotExists();
        if (isLoggedOn()) {
            setContent(menuBuilder.build(this::signOut));
        } else {
            setContent(loginBuilder.buildLogin());
        }
    }

    private boolean isLoggedOn() {
        return cookieRepository.hasUser();
    }

    private void signOut() {
        cookieRepository.clearLoginInfo();
        // redirect to root url
        getUI().getPage().setLocation("/");
    }

}