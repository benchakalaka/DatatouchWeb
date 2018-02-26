package com.datascope.ioc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class RestServicesConfig {

//    @Bean
//    @Scope("prototype")
//    public int siteId() {
//        // TODO: some dynamic way of resolving siteid
//        return GlobalShit.SITE_ID;
//    }

    @Bean
    @Scope("prototype")
    public String rootUrl(@Value("${rootUrl:http://www.datascopesystem.com/datatouch_dev/}")String rootUrl) {
        return rootUrl;
    }

    @Bean
    @Scope("prototype")
    public String database(@Value("${database:quilt_development}")String database) {
        return database;
    }

    @Bean
    @Scope("prototype")
    public String token(@Value("${token:c48e9f37-ac90-427a-9101-119c096593de}")String token) {
        return token;
    }
}
