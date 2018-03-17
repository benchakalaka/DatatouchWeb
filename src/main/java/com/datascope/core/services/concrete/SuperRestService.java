package com.datascope.core.services.concrete;

import com.datascope.core.services.IRestClient;
import org.springframework.stereotype.Service;

@Service
public class SuperRestService {

    protected IRestClient rest;

    public SuperRestService(IRestClient rest) {
        this.rest = rest;
    }
}
