package com.datascope.bounded.contexts.report.domain;

import com.datascope.bounded.contexts.core.domain.Model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Report extends Model {
    @JsonProperty("Url")
    private String url;

    @JsonProperty("ReportGroupId")
    private int reportGroupId;

    @JsonProperty("FileName")
    private String fileName;

    @JsonProperty("Id")
    private int id;

    public Report() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getReportGroupId() {
        return reportGroupId;
    }

    public void setReportGroupId(int reportGroupId) {
        this.reportGroupId = reportGroupId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class List extends ArrayList<Report> {

    }
}
