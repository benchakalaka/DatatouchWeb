package com.datascope.application.ui.report;

import com.datascope.application.ui.report.callbacks.SelectReportGeneratedDateCallback;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;

import java.time.LocalDate;

public class ReportViewUiHelper {
    public static void initGrid(Grid<ReportGroupGridItem> grid) {
        grid.removeAllColumns();

        grid.addColumn(ReportGroupGridItem::getGeneratedAt)
                .setCaption(ReportGroupGridItem.GENERATED_AT_COLUMN)
                .setExpandRatio(3);
    }

    public static void initDatePicker(DateField datePicker, SelectReportGeneratedDateCallback callback) {
        datePicker.setValue(LocalDate.now());
        datePicker.addValueChangeListener(valueChangeEvent -> callback.onReportGeneratedDateChanged(valueChangeEvent.getValue()));
    }
}
