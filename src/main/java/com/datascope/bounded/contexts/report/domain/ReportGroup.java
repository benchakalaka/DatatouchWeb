package com.datascope.bounded.contexts.report.domain;

import com.datascope.application.ui.report.elements.ReportGroupGridItem;
import com.datascope.bounded.contexts.core.domain.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ReportGroup  extends Model {
    @JsonProperty("Reports")
    private Report.List reports;

    @JsonProperty("GeneratedAt")
    public String generatedAt;


    public ReportGroup() {
    }

    private boolean hasReports(){
        return reports != null && !reports.isEmpty();
    }

    private ReportGroupGridItem.List reportsToGridItems() {
        if (!hasReports())
            return ReportGroupGridItem.List.empty();

        return reports
                .stream()
                .map(report -> new ReportGroupGridItem(report.getFileName(), report.getUrl()))
                .collect(Collectors.toCollection(ReportGroupGridItem.List::new));
    }

    public static class List extends ArrayList<ReportGroup> {
        public List() {
        }

        public ReportGroupGridItem.List toGridItems() {
            return stream()
                    .map(reportGroup ->  new ReportGroupGridItem(reportGroup.reportsToGridItems(), reportGroup.generatedAt))
                    .collect(Collectors.toCollection(ReportGroupGridItem.List::new));
        }
    }
}
