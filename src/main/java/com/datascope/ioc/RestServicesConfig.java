package com.datascope.ioc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RestServicesConfig {

    // TODO: do everything dynamically

    @Bean
    @Scope("prototype")
    public int siteId() {
        return 2;
    }

    @Bean
    @Scope("prototype")
    public String rootUrl() {
        return "http://www.datascopesystem.com/datatouch_dev/";
    }

    @Bean
    @Scope("prototype")
    public String areaFilesUrl(){
        return rootUrl() +"QuiltFiles/"+ database()+"/AreaFiles/";
    }

    @Bean
    @Scope("prototype")
    public String database() {
        return "quilt_development";
    }

    @Bean
    @Scope("prototype")
    public String token( ) {
        return "c48e9f37-ac90-427a-9101-119c096593de";
    }
}
