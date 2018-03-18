package com.datascope.application.ui.utils.factories;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;

public class ButtonFactory {
    public static Button buildButton(VaadinIcons icon, String theme, Button.ClickListener listener) {
        Button button = new Button(icon);
        button.addStyleName(theme);
        button.addClickListener(listener);
        return button;
    }
}
