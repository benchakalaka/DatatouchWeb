package com.datascope.application.ui.report.elements;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ReportGroupGridItem {

    private String url;

    private ReportGroupGridItem.List reports;
    private String caption;


    public ReportGroupGridItem(String name, String url) {
        this.caption = name;
        this.url = url;
    }

    public ReportGroupGridItem(ReportGroupGridItem.List reports, String caption) {
        this.reports = reports;
        this.caption = caption;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public ReportGroupGridItem.List getReportsAdGridItems() {
        return reports
                .stream()
                .map(item -> new ReportGroupGridItem(item.getCaption(), item.getUrl()))
                .collect(Collectors.toCollection(ReportGroupGridItem.List::new));
    }

    public boolean hasUrls() {
        return null != url && !"".equals(url);
    }

    public static class List extends ArrayList<ReportGroupGridItem> {

        public static ReportGroupGridItem.List empty() {
            return new ReportGroupGridItem.List();
        }
    }
}
