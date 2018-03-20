package com.datascope.ui.company.helpers;

import com.datascope.bounded.contexts.company.domain.Company;
import com.datascope.ui.company.callbacks.CompanyColorChangedCallback;
import com.datascope.ui.company.callbacks.OnCompanySelectedCallback;
import com.datascope.ui.company.elements.CompanyGridItem;
import com.datascope.ui.utils.common.ColorUtils;
import com.datascope.ui.utils.helper.Labels;
import com.datascope.ui.utils.helper.SuperHelper;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.ui.ColorPickerArea;
import com.vaadin.ui.Grid;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CompanyViewUiHelper extends SuperHelper {

    public CompanyViewUiHelper(Labels labels) {
        super(labels);
    }

    public CompanyGridItem.List toGridItems(Company.List companies) {
        return companies.stream().map(CompanyGridItem::fromCompany).collect(Collectors.toCollection(CompanyGridItem.List::new));
    }

    public void initGrid(Grid<CompanyGridItem> grid, OnCompanySelectedCallback callback) {
        grid.removeAllColumns();

        grid.addColumn(CompanyGridItem::getName)
                .setCaption(getLabel("company.grid.company.name"))
                .setExpandRatio(1);

        grid.addSelectionListener(item -> item.getFirstSelectedItem().ifPresent(callback::companySelected));
    }

    public void setOnColorPicker(ColorPickerArea picker, CompanyColorChangedCallback callback) {
        picker.addValueChangeListener(item -> {
            Color color = item.getValue();
            callback.companyColorChanged(color.getAlpha(), color.getRed(), color.getBlue(), color.getBlue());

        });
    }

    public Color getColor(String colour) {
        return new Color(ColorUtils.getR(colour), ColorUtils.getG(colour), ColorUtils.getB(colour));
    }
}