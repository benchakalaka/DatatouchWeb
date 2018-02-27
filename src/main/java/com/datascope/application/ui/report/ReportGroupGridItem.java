package com.datascope.application.ui.report;

import com.datascope.domain.report.Report;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ReportGroupGridItem {
    public static final String GENERATED_AT_COLUMN = "Generated";
    private Report.List reports;
    private String generatedAt;
    private int reportGroupTypeId;
    private int moduleId;
    private int reportGroupId;

    public ReportGroupGridItem(Report.List reports, String generatedAt, int reportGroupId) {
        this.reports = reports;
        this.generatedAt = generatedAt;
        this.reportGroupId = reportGroupId;
    }

    public Report.List getReports() {
        return reports;
    }

    public void setReports(Report.List reports) {
        this.reports = reports;
    }

    public String getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(String generatedAt) {
        this.generatedAt = generatedAt;
    }

    public int getReportGroupTypeId() {
        return reportGroupTypeId;
    }

    public void setReportGroupTypeId(int reportGroupTypeId) {
        this.reportGroupTypeId = reportGroupTypeId;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public int getReportGroupId() {
        return reportGroupId;
    }

    public void setReportGroupId(int reportGroupId) {
        this.reportGroupId = reportGroupId;
    }

    public ReportGroupGridItem.List getReportsAdGridItems() {
        return reports
                .stream()
                .map(item -> new ReportGroupGridItem(null,item.url,item.reportGroupId))
                .collect(Collectors.toCollection(ReportGroupGridItem.List::new));
    }

    public static class List extends ArrayList<ReportGroupGridItem>{

        public static ReportGroupGridItem.List empty() {
            return new ReportGroupGridItem.List();
        }
    }
}
