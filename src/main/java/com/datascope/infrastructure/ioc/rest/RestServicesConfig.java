package com.datascope.infrastructure.ioc.rest;

import com.datascope.bounded.contexts.core.services.RequestInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Configuration
public class RestServicesConfig {

    private RequestInfo requestInfo;

    public RestServicesConfig(RequestInfo requestInfo) {
        this.requestInfo = requestInfo;
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public String areaFilesUrl() {
        return requestInfo.getRootUrl() + "QuiltFiles/" + requestInfo.getDatabase() + "/AreaFiles/";
    }
}
