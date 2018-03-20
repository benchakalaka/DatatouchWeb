package com.datascope.ui.utils.helper;

import org.springframework.stereotype.Component;

@Component
public class SuperHelper {

    private Labels labels;

    public SuperHelper(Labels labels) {
        this.labels = labels;
    }

    protected String getLabel(String labelName) {
        return labels.get(labelName);
    }
}
