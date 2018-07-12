package com.datascope.ui.utils.factories;

import com.datascope.ui.area.AreasView;
import com.datascope.ui.company.CompanyView;
import com.datascope.ui.email.EmailGroupView;
import com.datascope.ui.hotspot.HotspotView;
import com.datascope.ui.report.ReportsView;
import com.datascope.ui.usermanagement.UserManagementView;
import com.datascope.ui.utils.helper.Labels;
import com.github.appreciated.app.layout.behaviour.Behaviour;
import com.github.appreciated.app.layout.builder.AppLayout;
import com.github.appreciated.app.layout.builder.design.AppBarDesign;
import com.github.appreciated.app.layout.builder.elements.builders.CDISubmenuBuilder;
import com.github.appreciated.app.layout.builder.entities.DefaultBadgeHolder;
import com.github.appreciated.app.layout.builder.entities.DefaultNotification;
import com.github.appreciated.app.layout.builder.entities.DefaultNotificationHolder;
import com.github.appreciated.app.layout.builder.providers.DefaultSpringNavigationElementInfoProvider;
import com.github.appreciated.app.layout.component.MenuHeader;
import com.github.appreciated.app.layout.component.button.AppBarButton;
import com.github.appreciated.app.layout.component.button.AppBarNotificationButton;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.spring.navigator.SpringViewProvider;
import org.springframework.stereotype.Component;

import java.io.Serializable;

import static com.github.appreciated.app.layout.builder.AppLayoutConfiguration.Position.HEADER;

@Component
@UIScope
public class MenuBuilder implements Serializable{

    private DefaultNotificationHolder notifications = new DefaultNotificationHolder();
    private DefaultBadgeHolder badge = new DefaultBadgeHolder();
    private SpringViewProvider provider;
    private Labels labels;

    public MenuBuilder(SpringViewProvider provider, Labels labels) {
        this.provider = provider;
        this.labels = labels;
    }

    public com.vaadin.ui.Component build(Runnable onSignOutClick) {
        notifications.addNotification(new DefaultNotification("New notification", "Do something please", DefaultNotification.Priority.MEDIUM));
        badge.setCount(3);

        return AppLayout.getCDIBuilder(Behaviour.LEFT_RESPONSIVE_HYBRID)
                .withViewProvider(() -> provider)
                .withNavigationElementInfoProvider(new DefaultSpringNavigationElementInfoProvider())
                .withTitle(labels.get("application.name"))
                .addToAppBar(new AppBarNotificationButton(notifications, true))
                .addToAppBar(new AppBarButton(VaadinIcons.SIGN_OUT, (e) -> onSignOutClick.run()))
                .withDesign(AppBarDesign.MATERIAL)
                .add(new MenuHeader(labels.get("application.version"), new ThemeResource("logo.png")), HEADER)
                .add(HotspotView.class)
                .add(badge, ReportsView.class)
                .add(CDISubmenuBuilder.get(labels.get("menu.admin"), VaadinIcons.COG)
                        .add(AreasView.class)
                        .add(EmailGroupView.class)
                        .add(CompanyView.class)
                        .add(UserManagementView.class)
                        .build())
                .build();
    }
}
