package com.datascope.bounded.contexts.report.service.interfaces;

import com.datascope.bounded.contexts.report.service.interfaces.callbacks.GetReportGroupsCallback;

import java.time.LocalDate;

public interface IReportService {
    void getReportGroups(int moduleId, LocalDate dateGeneratedAt, GetReportGroupsCallback callback);
}
