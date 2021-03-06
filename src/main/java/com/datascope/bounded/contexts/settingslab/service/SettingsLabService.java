package com.datascope.bounded.contexts.settingslab.service;

import com.datascope.bounded.contexts.core.services.IRestClient;
import com.datascope.bounded.contexts.core.services.concrete.UnirestClient;
import com.datascope.bounded.contexts.settingslab.domain.SettingsLab;
import com.datascope.bounded.contexts.settingslab.service.callbacks.ISettingsLabService;
import com.datascope.bounded.contexts.settingslab.service.requests.GetSettingsLabRequest;
import com.mashape.unirest.http.Unirest;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.function.Consumer;

@Service
public class SettingsLabService implements ISettingsLabService {

    private static final String GET_DEFAULT_SETTINGS_LAB = "SettingsLab/GetDefaultSettingsLab/";

    private IRestClient rest;

    public SettingsLabService(IRestClient client) {
        this.rest = client;
    }

    @Override
    public void getSettingsLab(Consumer<SettingsLab> onSuccess) {
        // TODO: Change service on server to take siteId from Header
        GetSettingsLabRequest view = new GetSettingsLabRequest(((UnirestClient) rest).getSiteId());

        Observable.just(rest.post(SettingsLab.class, GET_DEFAULT_SETTINGS_LAB, view))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .subscribe(onSuccess::accept);
    }
}
