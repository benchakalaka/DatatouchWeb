package com.datascope.application.ui.company;

import com.datascope.application.ui.company.callbacks.CompanyColorChangedCallback;
import com.datascope.application.ui.company.callbacks.OnCompanySelectedCallback;
import com.datascope.application.ui.company.elements.CompanyGridItem;
import com.datascope.bounded.contexts.company.domain.Company;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.ui.ColorPicker;
import com.vaadin.ui.Grid;

import java.util.stream.Collectors;

public class CompanyViewUiHelper {

    public CompanyGridItem.List toGridItems(Company.List companies) {
        return companies.stream().map(CompanyGridItem::fromCompany).collect(Collectors.toCollection(CompanyGridItem.List::new));
    }

    public void initGrid(Grid<CompanyGridItem> grid, OnCompanySelectedCallback callback) {
        grid.removeAllColumns();

        grid.addColumn(CompanyGridItem::getName)
                .setCaption(CompanyGridItem.NAME)
                .setExpandRatio(1);

        grid.addSelectionListener(item -> callback.companySelected(item.getFirstSelectedItem().get()));
    }

    public void setOnColorPicker(ColorPicker picker, CompanyColorChangedCallback callback) {
        picker.addValueChangeListener(item -> {
            Color color = item.getValue();
            callback.companyColorChanged(color.getAlpha(), color.getRed(), color.getBlue(), color.getBlue());

        });
    }
}