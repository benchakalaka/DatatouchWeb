package com.datascope.infrastructure.ioc.lang;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
@PropertySource("classpath:labels.properties")
@PropertySource("classpath:notifications.properties")
public class LocalizationConfig {

    private static final String NOTIFICATIONS_PROPERTIES_FILE_NAME = "notifications";
    private static final String LABELS_PROPERTIES_FILE_NAME = "labels";

    @Bean
    public MessageSource messageSource() {
        return getSource(NOTIFICATIONS_PROPERTIES_FILE_NAME);
    }

    @Bean
    public MessageSource labelSource() {
        return getSource(LABELS_PROPERTIES_FILE_NAME);
    }

    private MessageSource getSource(String name) {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(String.format("classpath:%s", name));
        messageSource.setCacheSeconds(36000);
        return messageSource;
    }
}
