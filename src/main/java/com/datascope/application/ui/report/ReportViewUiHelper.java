package com.datascope.application.ui.report;

import com.datascope.application.ui.report.callbacks.ReportSelectedCallback;
import com.datascope.application.ui.report.callbacks.SelectReportGeneratedDateCallback;
import com.vaadin.data.TreeData;
import com.vaadin.data.provider.TreeDataProvider;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TreeGrid;

import java.time.LocalDate;
import java.util.function.Consumer;

public class ReportViewUiHelper {
    private TreeData<ReportGroupGridItem> data = new TreeData<>();
    private TreeDataProvider<ReportGroupGridItem> reportGroupGridItemTreeDataProvider = new TreeDataProvider<>(data);

    public void initTree(TreeGrid<ReportGroupGridItem> treeGrid, ReportSelectedCallback callback) {
        treeGrid.setSizeFull();
        treeGrid.addColumn(ReportGroupGridItem::getCaption)
                .setCaption(ReportGroupGridItem.REPORTS_COLUMN_NAME)
                .setExpandRatio(3);

        treeGrid.addItemClickListener(click -> {
            if (click.getItem().hasUrls())
                callback.onReportSelected(click.getItem().getUrl());
        });

        treeGrid.setDataProvider(reportGroupGridItemTreeDataProvider);
    }

    public void setReports(ReportGroupGridItem.List reports) {
        data.clear();
        refreshTree.accept(reports);
    }

    public void initDatePicker(DateField datePicker, SelectReportGeneratedDateCallback callback) {
        datePicker.setValue(LocalDate.now());
        datePicker.addValueChangeListener(valueChangeEvent -> callback.onReportGeneratedDateChanged(valueChangeEvent.getValue()));
    }

    public void clearReportsTree() {
        setReports(ReportGroupGridItem.List.empty());
    }

    private Consumer<ReportGroupGridItem.List> refreshTree = reports -> {
        for (ReportGroupGridItem root : reports) {
            data.addItem(null, root);
            data.addItems(root, root.getReportsAdGridItems());
        }

        reportGroupGridItemTreeDataProvider.refreshAll();
    };
}
