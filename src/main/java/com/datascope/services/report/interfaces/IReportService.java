package com.datascope.services.report.interfaces;

import com.datascope.services.report.interfaces.callbacks.GetReportGroupsCallback;

import java.time.LocalDate;

public interface IReportService {
    void getReportGroups(int moduleId, LocalDate dateGeneratedAt, GetReportGroupsCallback callback);
}
