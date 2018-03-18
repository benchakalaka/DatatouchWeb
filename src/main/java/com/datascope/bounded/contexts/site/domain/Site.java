package com.datascope.bounded.contexts.site.domain;


import com.datascope.bounded.contexts.core.domain.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Site extends Model {

    public Site() {
    }

    @JsonProperty("Id")
    private int id;

    @JsonProperty("Name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.valueOf(getName());
    }

    public static class List extends ArrayList<Site>

    {
    }
}
