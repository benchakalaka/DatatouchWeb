package com.datascope.bounded.contexts.core.services;

public interface RequestInfo {
    int getSiteId();
    String getDatabase();
    String getToken();
    String getRootUrl();
}
