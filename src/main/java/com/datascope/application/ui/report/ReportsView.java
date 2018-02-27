package com.datascope.application.ui.report;

import com.datascope.DatatouchUI;
import com.datascope.application.ui.report.callbacks.SelectReportGeneratedDateCallback;
import com.datascope.application.ui.utils.notifications.DatatouchNotification;
import com.datascope.application.ui.generated.ReportDesign;
import com.datascope.domain.report.ReportGroup;
import com.datascope.services.report.interfaces.IReportService;
import com.datascope.services.report.interfaces.callbacks.GetReportGroupsCallback;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.navigator.View;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TreeGrid;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@UIScope
@SpringView(name = ReportsView.NAME, ui = {DatatouchUI.class})
public class ReportsView extends ReportDesign implements View, GetReportGroupsCallback, SelectReportGeneratedDateCallback {

    public static final String NAME = "ReportsView";

    private IReportService service;
    private DatatouchNotification notification;

    public ReportsView(IReportService service, DatatouchNotification notification) {
        this.service = service;
        this.notification = notification;
    }

    @PostConstruct
    public void init() {
        ReportViewUiHelper.initGrid(getReportGroupsGrid());
        ReportViewUiHelper.initDatePicker(getDatePicker(), this);


        getReportGroupsGrid().addItemClickListener(click -> {
            ReportGroupGridItem item1 = click.getItem();

            ExternalResource externalResource = new ExternalResource(item1.getReports().get(0).url);//item1.getReports().get(0).url);
            getBrowserFrame().setSource(externalResource);

        });


        getGroups(getSelectedDate());
    }

    @Override
    public void reportGroupsFound(ReportGroup.List reports) {



        Grid<ReportGroupGridItem> tree = new TreeGrid<>();
        ReportGroupGridItem.List gridItems = reports.toGridItems();
        TreeData<ReportGroupGridItem> data = new TreeData<>();

        for (ReportGroupGridItem root : gridItems) {
            data.addItem(null, root);
            data.addItems(root, root.getReportsAdGridItems());
        }

        tree.setDataProvider(new TreeDataProvider<>(data));
        tree.addColumn(ReportGroupGridItem::getGeneratedAt).setCaption("Generated At");

        addComponent(tree);


        getReportGroupsGrid().setItems(reports.toGridItems());
    }

    private void getGroups(LocalDate selectedDate) {
        this.service.getReportGroups(71538, selectedDate, this);
    }

    //region notifications

    @Override
    public void noReportGroupsFound() {
        notification.warn("no.report.groups.found");
        getReportGroupsGrid().setItems(ReportGroupGridItem.List.empty());
    }

    @Override
    public void onReportGeneratedDateChanged(LocalDate selectedDate) {
        getGroups(selectedDate);
    }

    private LocalDate getSelectedDate() {
        return getDatePicker().getValue();
    }

    //endregion notifications
}