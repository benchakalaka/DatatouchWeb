package com.datascope;

import com.datascope.components.ui.LoginView;
import com.datascope.components.ui.MenuView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringNavigator;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
@Theme("valo")
public class DatatouchUI extends UI{

    private LoginView loginView;
    private MenuView menuView;

//    @Autowired
//    SpringNavigator navigator;

    public DatatouchUI(LoginView loginView, MenuView menuView)
    {
        this.loginView = loginView;
        this.menuView = menuView;
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        initNavigator();
        setContent(loginView);
        //navigator.navigateTo(MenuView.NAME);
    }

    private void initNavigator() {
        Navigator navigator = new Navigator(this,this);
        navigator.addView(LoginView.NAME,loginView);
        navigator.addView(MenuView.NAME,menuView);
        getUI().setNavigator(navigator);
    }
}
