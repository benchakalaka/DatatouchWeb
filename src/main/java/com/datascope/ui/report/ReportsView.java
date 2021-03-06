package com.datascope.ui.report;

import com.datascope.bounded.contexts.report.domain.ReportGroup;
import com.datascope.bounded.contexts.report.service.interfaces.IReportService;
import com.datascope.bounded.contexts.report.service.interfaces.callbacks.GetReportGroupsCallback;
import com.datascope.bounded.contexts.settingslab.domain.SettingsLab;
import com.datascope.bounded.contexts.settingslab.service.callbacks.ISettingsLabService;
import com.datascope.ui.generated.ReportDesign;
import com.datascope.ui.report.callbacks.ReportSelectedCallback;
import com.datascope.ui.report.callbacks.SelectReportGeneratedDateCallback;
import com.datascope.ui.report.elements.ReportGroupGridItem;
import com.datascope.ui.report.helpers.ReportsViewController;
import com.datascope.ui.utils.notifications.Notifications;
import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.github.appreciated.app.layout.annotations.NavigatorViewName;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.TreeGrid;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@UIScope
@MenuCaption("Reports")
@MenuIcon(VaadinIcons.CHART_3D)
@NavigatorViewName(ReportsView.NAME)
@SpringView(name = ReportsView.NAME)
public class ReportsView extends ReportDesign implements View,
        GetReportGroupsCallback,
        SelectReportGeneratedDateCallback,
        ReportSelectedCallback {

    // TODO: and select first report
    static final String NAME = "ReportsView";


    private TreeGrid<ReportGroupGridItem> reportsTree = new TreeGrid<>();
    private IReportService service;
    private ISettingsLabService settingsLabService;
    private Notifications notification;

    private ReportsViewController controller;

    public ReportsView(
            IReportService service,
            ISettingsLabService settingsLabService,
            Notifications notification,
            ReportsViewController helper) {
        this.service = service;
        this.settingsLabService = settingsLabService;
        this.notification = notification;
        controller = helper;
    }

    @PostConstruct
    public void init() {
        controller.initDatePicker(getDatePicker(), this::getGroups);
        controller.initTree(reportsTree, this);
        getHorizontalSplit().addComponent(reportsTree);
        controller.initComboBox(getCbModules(), this::getGroups);
        settingsLabService.getSettingsLab(this::onSettingsLabFound);
    }

    private void onSettingsLabFound(SettingsLab settingsLab) {
        controller.showModules(getCbModules(), settingsLab.getModules());
    }

    @Override
    public void reportGroupsFound(ReportGroup.List reports) {
        controller.setReports(reports.toGridItems());
        controller.expandAll(reportsTree);
        controller.setDefaultSelection(reportsTree, this);
    }

    @Override
    public void onReportSelected(String reportUrl) {
        getBrowserFrame().setSource(new ExternalResource(reportUrl));
    }

    private void getGroups() {
        this.service.getReportGroups(getSelectedModuleId(), getSelectedDate(), this);
    }

    @Override
    public void noReportGroupsFound() {
        controller.clearReportsTree();
    }

    @Override
    public void onReportGeneratedDateChanged(LocalDate selectedDate) {
        getGroups();
    }

    private LocalDate getSelectedDate() {
        return getDatePicker().getValue();
    }

    private int getSelectedModuleId() {
        return getCbModules().getSelectedItem().isPresent()
                ? getCbModules().getSelectedItem().get().getId()
                : 0;
    }
}