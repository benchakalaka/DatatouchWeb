package com.datascope.ui.utils.factories;

import com.datascope.ui.utils.common.ColorUtils;;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.ui.ColorPicker;
import com.vaadin.ui.ColorPickerArea;

public class ColorPickerFactory {
    public static ColorPickerArea buildColorPickerArea(String popupCaption, String hexColor, ColorPickerArea.ValueChangeListener<Color> listener) {
        Color color = new Color(ColorUtils.parseColor(hexColor));
        ColorPickerArea colorPickerArea = new ColorPickerArea(popupCaption, color);
        colorPickerArea.addValueChangeListener(listener);
        return colorPickerArea;
    }

    public static ColorPicker buildColorPicker(String popupCaption, String hexColor, ColorPickerArea.ValueChangeListener<Color> listener) {
        Color color = new Color(ColorUtils.parseColor(hexColor));
        ColorPicker colorPicker = new ColorPicker(popupCaption, color);
        colorPicker.setSwatchesVisibility(false);
        colorPicker.setHistoryVisibility(false);
        colorPicker.addValueChangeListener(listener);
        return colorPicker;
    }
}
