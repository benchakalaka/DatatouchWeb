package com.datascope.ui;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

@SpringUI
@SpringView(name = "")
public class ErrorView extends VerticalLayout implements View {

    public ErrorView() {
    }

    @PostConstruct
    public void init() {
        addComponent(new Label("Default View"));
    }
}
