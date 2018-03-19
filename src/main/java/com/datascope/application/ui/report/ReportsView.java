package com.datascope.application.ui.report;

import com.datascope.application.ui.generated.ReportDesign;
import com.datascope.application.ui.report.callbacks.ReportSelectedCallback;
import com.datascope.application.ui.report.callbacks.SelectReportGeneratedDateCallback;
import com.datascope.application.ui.report.elements.ReportGroupGridItem;
import com.datascope.application.ui.report.helpers.ReportViewUiHelper;
import com.datascope.application.ui.utils.notifications.DatatouchNotification;
import com.datascope.bounded.contexts.report.domain.ReportGroup;
import com.datascope.bounded.contexts.report.service.interfaces.IReportService;
import com.datascope.bounded.contexts.report.service.interfaces.callbacks.GetReportGroupsCallback;
import com.vaadin.navigator.View;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.TreeGrid;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@UIScope
@SpringView(name = ReportsView.NAME)
public class ReportsView extends ReportDesign implements View, GetReportGroupsCallback, SelectReportGeneratedDateCallback, ReportSelectedCallback {

    // TODO: Expand all nodes, and select first report
    public static final String NAME = "ReportsView";

    private TreeGrid<ReportGroupGridItem> reportsTree = new TreeGrid<>();
    private IReportService service;
    private DatatouchNotification notification;

    private ReportViewUiHelper uiHelper;

    public ReportsView(
            IReportService service,
            DatatouchNotification notification,
            ReportViewUiHelper helper) {
        this.service = service;
        this.notification = notification;
        uiHelper = helper;
    }

    @PostConstruct
    public void init() {
        uiHelper.initDatePicker(getDatePicker(), this);
        uiHelper.initTree(reportsTree, this);
        getHorizontalSplit().addComponent(reportsTree);
        getGroups(getSelectedDate());
    }

    @Override
    public void reportGroupsFound(ReportGroup.List reports) {
        uiHelper.setReports(reports.toGridItems());
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
        uiHelper.clearReportsTree();
    }

    @Override
    public void onReportGeneratedDateChanged(LocalDate selectedDate) {
        getGroups(selectedDate);
    }

    private LocalDate getSelectedDate() {
        return getDatePicker().getValue();
    }

}