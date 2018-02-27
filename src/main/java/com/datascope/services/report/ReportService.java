package com.datascope.services.report;

import com.datascope.application.ui.utils.common.DateUtils;
import com.datascope.domain.report.ReportGroup;
import com.datascope.services.core.Rest;
import com.datascope.services.report.interfaces.IReportService;
import com.datascope.services.report.interfaces.callbacks.GetReportGroupsCallback;
import com.datascope.services.report.requests.GetReportGroupsRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class ReportService  implements IReportService {

    private static final String GET_GROUPS = "Report/GetReportGroups";
    private Rest rest;

    public ReportService(Rest rest) {
        this.rest = rest;
    }

    @Override
    public void getReportGroups(int moduleId, LocalDate dateGeneratedAt, GetReportGroupsCallback callback) {

        GetReportGroupsRequest request = new GetReportGroupsRequest(moduleId,DateUtils.parseDate(dateGeneratedAt),rest.getSiteId());
        ReportGroup.List reports = rest.execute(ReportGroup.List.class, GET_GROUPS,request);

        if (null == reports || reports.isEmpty())
            callback.noReportGroupsFound();
        else
            callback.reportGroupsFound(reports);
    }
}
