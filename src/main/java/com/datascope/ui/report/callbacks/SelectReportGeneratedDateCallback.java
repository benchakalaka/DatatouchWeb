package com.datascope.ui.report.callbacks;

import java.time.LocalDate;

public interface SelectReportGeneratedDateCallback {
    void onReportGeneratedDateChanged(LocalDate date);
}
