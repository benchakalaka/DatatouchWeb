package com.datascope.services.core;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;

import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@Scope("prototype")
public class Rest {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String ACCEPT = "accept";
    private static final String TOKEN = "Token";
    private static final String DATABASE = "Database";
    private static final String JSON = "application/json";
    private static final String SITE_ID = "siteId";
    private static final String CLIENT_NAME = "DatatouchWeb";
    private static final String CLIENT = "Client";
    private static final String VERSION = "Version";
    private static final String VERSION_NAME = "0.0.1";

    private String rootUrl;
    private String database;
    private String token;

    // TODO: How to inject siteID??
    private static int siteId;

    private static boolean isInitialized = false;

    public Rest(String rootUrl, String database, String token) {
        this.rootUrl = rootUrl;
        this.database = database;
        this.token = token;
    }

    public static void setSiteId(int siteId){
        Rest.siteId = siteId;
    }

    public int getSiteId(){
        return siteId;
    }

    public <TResult> TResult execute(Class<TResult> ofType, String path, Object params) {
        try {
            String url = rootUrl + path;

            HttpResponse<TResult> result = Unirest
                    .post(url)
                    .header(ACCEPT, JSON)
                    .header(CONTENT_TYPE, JSON)
                    .header(SITE_ID, String.valueOf(siteId))
                    .header(TOKEN, token)
                    .header(CLIENT, CLIENT_NAME)
                    .header(VERSION, VERSION_NAME)
                    .header(DATABASE, database)
                    .body(params)
                    .asObject(ofType);

            return result.getBody();

        } catch (Exception e1) {
            e1.printStackTrace();
            //TODO: notification, request failed
            //TODO: exception filter
        }

        return null;
    }

    @PostConstruct
    public void postInit() {
        if (!isInitialized) {
            init();
            isInitialized = true;
        }
    }

    private static void init() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
