package com.datascope.domain.report;

import com.datascope.domain.core.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Report extends Model {
    @JsonProperty("Url")
    public String url;

    @JsonProperty("ReportGroupId")
    public int reportGroupId;

    @JsonProperty("FileName")
    public String fileName;

    @JsonProperty("Id")
    public int id;

    public Report() {
    }


    public static class List extends ArrayList<Report> {
    }
}
