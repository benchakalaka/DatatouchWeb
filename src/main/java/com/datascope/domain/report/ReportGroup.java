package com.datascope.domain.report;

import com.datascope.application.ui.report.ReportGroupGridItem;
import com.datascope.domain.core.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ReportGroup  extends Model {
    @JsonProperty("Reports")
    public Report.List reports;

    @JsonProperty("GeneratedAt")
    public String generatedAt;

    @JsonProperty("ReportGroupTypeId")
    public int reportGroupTypeId;

    @JsonProperty("ModuleId")
    public int moduleId;

    @JsonProperty("Id")
    private int reportGroupId;

    public ReportGroup() {
    }


    public Report.List getReports() {
        return reports;
    }

    public void setReports(Report.List reports) {
        this.reports = reports;
    }

    public String getGeneratrdAt() {
        return generatedAt;
    }

    public void setGeneratrdAt(String generatrdAt) {
        this.generatedAt = generatrdAt;
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

    public boolean hasReportFiles(){
        return reports != null && !reports.isEmpty();
    }

    public static class List extends ArrayList<ReportGroup> {
        public List() {
        }

        public ReportGroupGridItem.List toGridItems() {
            return stream()
                    .map(reportGroup -> new ReportGroupGridItem(reportGroup.reports, reportGroup.generatedAt, reportGroup.getReportGroupId()))
                    .collect(Collectors.toCollection(ReportGroupGridItem.List::new));

        }
    }
}
