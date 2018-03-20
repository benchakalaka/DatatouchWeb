package com.datascope.ui.report;

import com.datascope.bounded.contexts.report.domain.ReportGroup;
import com.datascope.bounded.contexts.report.service.interfaces.IReportService;
import com.datascope.bounded.contexts.report.service.interfaces.callbacks.GetReportGroupsCallback;
import com.datascope.ui.generated.ReportDesign;
import com.datascope.ui.report.callbacks.ReportSelectedCallback;
import com.datascope.ui.report.callbacks.SelectReportGeneratedDateCallback;
import com.datascope.ui.report.elements.ReportGroupGridItem;
import com.datascope.ui.report.helpers.ReportsViewController;
import com.datascope.ui.utils.notifications.Messages;
import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.github.appreciated.app.layout.annotations.NavigatorViewName;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.TreeGrid;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@UIScope


@MenuCaption("Reports")
@MenuIcon(VaadinIcons.ADD_DOCK)
@NavigatorViewName(ReportsView.NAME)
@SpringView(name = ReportsView.NAME)
public class ReportsView extends ReportDesign implements View, GetReportGroupsCallback, SelectReportGeneratedDateCallback, ReportSelectedCallback {

    // TODO: Expand all nodes, and select first report
    static final String NAME = "ReportsView";

    private TreeGrid<ReportGroupGridItem> reportsTree = new TreeGrid<>();
    private IReportService service;
    private Messages notification;

    private ReportsViewController controller;

    public ReportsView(
            IReportService service,
            Messages notification,
            ReportsViewController helper) {
        this.service = service;
        this.notification = notification;
        controller = helper;
    }

    @PostConstruct
    public void init() {
        controller.initDatePicker(getDatePicker(), this);
        controller.initTree(reportsTree, this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //TODO: unfortunately, on second time it's cause issue
        getHorizontalSplit().addComponent(reportsTree);
        getGroups(getSelectedDate());
    }

    @Override
    public void reportGroupsFound(ReportGroup.List reports) {
        controller.setReports(reports.toGridItems());
    }

    @Override
    public void onReportSelected(String reportUrl) {
        getBrowserFrame().setSource(new ExternalResource(reportUrl));
    }

    private void getGroups(LocalDate selectedDate) {
        this.service.getReportGroups(71538, selectedDate, this);
    }

    @Override
    public void noReportGroupsFound() {
        notification.warn("no.report.groups.found");
        controller.clearReportsTree();
    }

    @Override
    public void onReportGeneratedDateChanged(LocalDate selectedDate) {
        getGroups(selectedDate);
    }

    private LocalDate getSelectedDate() {
        return getDatePicker().getValue();
    }

}