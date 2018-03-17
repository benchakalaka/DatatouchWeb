package com.datascope.application.ui.company;

import com.datascope.application.ui.company.callbacks.CompanyColorChangedCallback;
import com.datascope.application.ui.company.callbacks.OnCompanySelectedCallback;
import com.datascope.application.ui.company.elements.CompanyGridItem;
import com.datascope.bounded.contexts.company.domain.Company;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.ui.ColorPicker;
import com.vaadin.ui.ColorPickerArea;
import com.vaadin.ui.Grid;

import java.util.stream.Collectors;

import static com.datascope.application.ui.utils.common.ColorUtils.getB;
import static com.datascope.application.ui.utils.common.ColorUtils.getG;
import static com.datascope.application.ui.utils.common.ColorUtils.getR;

public class CompanyViewUiHelper {

    public CompanyGridItem.List toGridItems(Company.List companies) {
        return companies.stream().map(CompanyGridItem::fromCompany).collect(Collectors.toCollection(CompanyGridItem.List::new));
    }

    public void initGrid(Grid<CompanyGridItem> grid, OnCompanySelectedCallback callback) {
        grid.removeAllColumns();

        grid.addColumn(CompanyGridItem::getName)
                .setCaption(CompanyGridItem.NAME)
                .setExpandRatio(1);

        grid.addSelectionListener(item -> item.getFirstSelectedItem().ifPresent(callback::companySelected));
    }

    public void setOnColorPicker(ColorPickerArea picker, CompanyColorChangedCallback callback) {
        picker.addValueChangeListener(item -> {
            Color color = item.getValue();

            String col = colorToHex(color);

            callback.companyColorChanged(color.getAlpha(), color.getRed(), color.getBlue(), color.getBlue());

        });
    }

    public String colorToHex(Color color) {
        String hex1;
        String hex2;

        hex1 = Integer.toHexString(color.hashCode()).toUpperCase();

        switch (hex1.length()) {
            case 2:
                hex2 = "000000";
                break;
            case 3:
                hex2 = String.format("00000%s", hex1.substring(0,1));
                break;
            case 4:
                hex2 = String.format("0000%s", hex1.substring(0,2));
                break;
            case 5:
                hex2 = String.format("000%s", hex1.substring(0,3));
                break;
            case 6:
                hex2 = String.format("00%s", hex1.substring(0,4));
                break;
            case 7:
                hex2 = String.format("0%s", hex1.substring(0,5));
                break;
            default:
                hex2 = hex1.substring(0, 6);
        }
        return hex2;
    }

    public Color getColor(String colour) {
        return new Color(getR(colour), getG(colour), getB(colour));
    }
}