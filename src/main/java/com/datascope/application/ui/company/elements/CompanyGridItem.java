package com.datascope.application.ui.company.elements;

import com.datascope.bounded.contexts.company.domain.Company;

import java.util.ArrayList;

public class CompanyGridItem {

    public static final String NAME = "Name";
    public static final String COLOUR = "Colour";
    private final int id;
    private final String name;
    private final String colour;
    private final int sortOrder;

    public CompanyGridItem(int id, String name, String colour, int sortOrder) {
        this.id = id;
        this.name = name;
        this.colour = colour;
        this.sortOrder = sortOrder;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColour() {
        return colour;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public static CompanyGridItem fromCompany(Company company) {
        return new CompanyGridItem(company.getId(),company.getName(), company.getColour(), company.getSortOrder());
    }

    public  static class List extends ArrayList<CompanyGridItem> {
    }
}
