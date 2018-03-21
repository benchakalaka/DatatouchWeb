package com.datascope.ui;

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

    public DatatouchUI(MenuBuilder menuBuilder) {
        this.menuBuilder = menuBuilder;
    }

    @Override
    protected void init(VaadinRequest request) {
        setContent(menuBuilder.build());
    }
}