package com.datascope.bounded.contexts.core.services.concrete;


import com.datascope.bounded.contexts.core.services.RequestInfo;
import com.datascope.bounded.contexts.core.services.IRestClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;

import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class UnirestClient implements IRestClient {

    private static final String CONTENT_TYPE = "Content-Type";
    private static final String ACCEPT = "accept";
    private static final String TOKEN = "Token";
    private static final String DATABASE = "Database";
    private static final String JSON = "application/json";
    private static final String SITE_ID = "siteId";
    private static final String CLIENT_NAME = "DatatouchWeb";
    private static final String CLIENT = "Client";
    private static final String VERSION = "Version";

    @Value("${application.version}")
    private String version;


    private static boolean isInitialized = false;
    private RequestInfo requestInfoProvider;

    public UnirestClient(RequestInfo requestInfoProvider) {
        this.requestInfoProvider = requestInfoProvider;
    }

    public int getSiteId() {
        return requestInfoProvider.getSiteId();
    }

    @Override
    public <TResult> TResult post(Class<TResult> ofType, String path) {
        return post(ofType, path, null);
    }

    @Override
    public <TResult> TResult post(Class<TResult> ofType, String servicePath, Object params) {
        try {
            String url = requestInfoProvider.getRootUrl() + servicePath;

            System.out.println(String.format("Execute request %s", url));

            if (null != params)
                System.out.println(String.format("Params %s", params.toString()));

            System.out.println("--------------------------Headers");
            System.out.println(requestInfoProvider.toString());
            System.out.println("--------------------------Headers");

            HttpResponse<TResult> result = Unirest
                    .post(url)
                    .header(ACCEPT, JSON)
                    .header(CONTENT_TYPE, JSON)
                    .header(SITE_ID, String.valueOf(requestInfoProvider.getSiteId()))
                    .header(TOKEN, requestInfoProvider.getToken())
                    .header(CLIENT, CLIENT_NAME)
                    .header(VERSION, version)
                    .header(DATABASE, requestInfoProvider.getDatabase())
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
