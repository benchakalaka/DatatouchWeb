package com.datascope.bounded.contexts.report.service.interfaces.callbacks;


import com.datascope.bounded.contexts.report.domain.ReportGroup;

public interface GetReportGroupsCallback {
    void noReportGroupsFound();

    void reportGroupsFound(ReportGroup.List reports);
}
