package com.datascope.ui.company.elements;

import com.datascope.bounded.contexts.company.domain.Company;
import com.datascope.ui.utils.common.ColorUtils;

import java.util.ArrayList;

public class CompanyGridItem {
    private final int id;
    private final String name;
    private String colour;
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

    public void setColour(int colour) {
        this.colour = ColorUtils.toHexColor(colour);
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
