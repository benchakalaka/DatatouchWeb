package com.datascope.ui;


import com.datascope.ui.area.AreasView;
import com.datascope.ui.company.CompanyView;
import com.datascope.ui.email.EmailGroupView;
import com.datascope.ui.hotspot.HotspotView;
import com.datascope.ui.report.ReportsView;
import com.datascope.ui.utils.helper.Labels;
import com.github.appreciated.app.layout.behaviour.AppLayoutComponent;
import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.AppLayout;
import com.github.appreciated.app.layout.builder.design.AppBarDesign;
import com.github.appreciated.app.layout.builder.entities.DefaultBadgeHolder;
import com.github.appreciated.app.layout.builder.entities.DefaultNotification;
import com.github.appreciated.app.layout.builder.entities.DefaultNotificationHolder;
import com.github.appreciated.app.layout.builder.providers.DefaultSpringNavigationElementInfoProvider;
import com.github.appreciated.app.layout.component.MenuHeader;
import com.github.appreciated.app.layout.component.button.AppBarNotificationButton;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Viewport;
import com.vaadin.navigator.PushStateNavigation;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;

import static com.github.appreciated.app.layout.builder.AppLayoutConfiguration.Position.HEADER;


@SpringUI
@Title("Datatouch")
@Theme("tests-valo-flat")
@Push
@PushStateNavigation
@Viewport("width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no")
//tests-valo-blueprint,dark,facebook,flatdark,flat,light,metro,reindeer
public class DatatouchUI extends UI {

    // TODO: Add Mapper
    // TODO: EventBus Guava, for subscribe to UI events
    private SpringViewProvider provider;

    private Labels labels;
    private DefaultNotificationHolder notifications = new DefaultNotificationHolder();
    private DefaultBadgeHolder badge = new DefaultBadgeHolder();

    public DatatouchUI(SpringViewProvider provider, Labels labels) {
        this.provider = provider;
        this.labels = labels;
    }

    @Override
    protected void init(VaadinRequest request) {

        notifications.addNotification(new DefaultNotification("New notification", "Do something please", DefaultNotification.Priority.MEDIUM));
        badge.increase();
        badge.increase();
        badge.increase();

        AppLayoutComponent menu = AppLayout.getCDIBuilder(Behaviour.LEFT_RESPONSIVE_HYBRID)
                .withViewProvider(() -> provider)
                .withNavigationElementInfoProvider(new DefaultSpringNavigationElementInfoProvider())
                .withTitle("DatatouchWeb")
                .addToAppBar(new AppBarNotificationButton(notifications, true))
                .withDesign(AppBarDesign.DEFAULT)
                .add(new MenuHeader("Version 0.0.1", new ThemeResource("logo.png")), HEADER)
                .add(badge, EmailGroupView.class)
                .add(CompanyView.class)
                .add(HotspotView.class)
                .add(AreasView.class)
                .add(ReportsView.class)
//                .add(CDISubmenuBuilder.get("My Submenu", VaadinIcons.PLUS)
//                        .add(AreasView.class)
//                        .add(EmailGroupView.class)
//                        .add(HotspotView.class)
//                        .build())
                .build();

        setContent(menu);

    }

}