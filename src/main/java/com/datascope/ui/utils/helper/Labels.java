package com.datascope.ui.utils.helper;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

@Component
public class Labels {

    private MessageSourceAccessor accessor;

    public Labels(MessageSource labelSource) {
        this.accessor = new MessageSourceAccessor(labelSource, LocaleContextHolder.getLocale());
    }

    public String get(String code) {
        return accessor.getMessage(code);
    }
}
