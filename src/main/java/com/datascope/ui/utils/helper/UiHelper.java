package com.datascope.ui.utils.helper;

import org.springframework.stereotype.Component;

@Component
public class UiHelper {

    private Labels labels;

    public UiHelper(Labels labels) {
        this.labels = labels;
    }

    protected String getLabel(String labelName) {
        return labels.get(labelName);
    }
}
