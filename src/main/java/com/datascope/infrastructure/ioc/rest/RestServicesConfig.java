package com.datascope.infrastructure.ioc.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Configuration
public class RestServicesConfig {

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public int siteId() {
        return 2;
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public String rootUrl() {
        return "http://www.datascopesystem.com/datatouch_dev/";
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public String areaFilesUrl() {
        return rootUrl() + "QuiltFiles/" + database() + "/AreaFiles/";
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public String database() {
        return "quilt_development";
    }

    @Bean
    @Scope(SCOPE_PROTOTYPE)
    public String token() {
        return "c48e9f37-ac90-427a-9101-119c096593de";
    }
}
