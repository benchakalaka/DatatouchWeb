package com.datascope.services.report.interfaces.callbacks;

import com.datascope.domain.report.ReportGroup;

public interface GetReportGroupsCallback {
    void noReportGroupsFound();

    void reportGroupsFound(ReportGroup.List reports);
}
