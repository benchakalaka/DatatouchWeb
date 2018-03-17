package com.datascope.application.ui;

import com.datascope.application.ui.company.CompanyView;
import com.datascope.application.ui.hotspot.HotspotView;
import com.datascope.application.ui.report.ReportsView;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;


@SpringUI
@Title("Datatouch")
@Theme("tests-valo-flat")
public class DatatouchUI extends UI{


    private SpringViewProvider provider;

    public DatatouchUI(SpringViewProvider provider)
    {
        this.provider = provider;
    }

    @Override
    protected void init(VaadinRequest request) {
        Navigator navigator = new Navigator(this, this);
        navigator.addProvider(provider);
        setNavigator(navigator);
        getNavigator().navigateTo(CompanyView.NAME);
    }
    //tests-valo-blueprint;
    //"tests-valo-dark");
    //"tests-valo-facebook");
    // "tests-valo-flatdark");
    // "tests-valo-flat");
    //"tests-valo-light");
    //tests-valo-metro");
    //tests-valo-reindeer");
}