package com.datascope.ui.utils.factories;

import com.datascope.ui.login.LoginView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@UIScope
public class LoginBuilder implements Serializable {

    private SpringViewProvider provider;

    public LoginBuilder(SpringViewProvider provider) {
        this.provider = provider;
    }


    public com.vaadin.ui.Component buildLogin() {
        return provider.getView(LoginView.NAME).getViewComponent();
    }
}
