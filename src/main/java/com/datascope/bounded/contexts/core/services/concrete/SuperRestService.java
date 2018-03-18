package com.datascope.bounded.contexts.core.services.concrete;

import com.datascope.bounded.contexts.core.services.IRestClient;
import org.springframework.stereotype.Service;

@Service
public class SuperRestService {

    protected IRestClient rest;

    public SuperRestService(IRestClient rest) {
        this.rest = rest;
    }
}
