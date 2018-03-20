package com.datascope.bounded.contexts.report.service;

import com.datascope.bounded.contexts.report.domain.ReportGroup;
import com.datascope.bounded.contexts.report.service.interfaces.IReportService;
import com.datascope.bounded.contexts.report.service.interfaces.callbacks.GetReportGroupsCallback;
import com.datascope.bounded.contexts.report.service.requests.GetReportGroupsRequest;
import com.datascope.bounded.contexts.core.services.IRestClient;
import com.datascope.bounded.contexts.core.services.concrete.SuperRestService;
import com.datascope.ui.utils.common.DateUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReportService extends SuperRestService implements IReportService {

    private static final String GET_GROUPS = "Report/GetReportGroups";

    public ReportService(IRestClient rest) {
       super(rest);
    }

    @Override
    public void getReportGroups(int moduleId, LocalDate dateGeneratedAt, GetReportGroupsCallback callback) {

        GetReportGroupsRequest request = new GetReportGroupsRequest(moduleId, DateUtils.parseDate(dateGeneratedAt));
        ReportGroup.List reports = rest.post(ReportGroup.List.class, GET_GROUPS,request);

        if (null == reports || reports.isEmpty())
            callback.noReportGroupsFound();
        else
            callback.reportGroupsFound(reports);
    }
}
