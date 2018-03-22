package com.datascope.bounded.contexts.settingslab.service;

import com.datascope.bounded.contexts.core.services.IRestClient;
import com.datascope.bounded.contexts.settingslab.domain.SettingsLab;
import com.datascope.bounded.contexts.settingslab.service.callbacks.ISettingsLabService;
import com.google.gwt.core.client.Scheduler;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.function.Consumer;

public class SettingsLabService implements ISettingsLabService {

    private static final String GET_DEFAULT_SETTINGS_LAB = "http://[ip:port]/SettingsLab/GetDefaultSettingsLab/";

    private IRestClient rest;

    public SettingsLabService(IRestClient client) {
        this.rest = client;
    }

    public void gg(){
        getSettingsLab(this::kk);
    }

    private void kk(SettingsLab settingsLab) {

    }

    @Override
    public void getSettingsLab(Consumer<SettingsLab> onSuccess) {
        Observable.just(rest.post(SettingsLab.class,GET_DEFAULT_SETTINGS_LAB,))
            .subscribeOn(Schedulers.newThread())
            .observeOn(Schedulers.io())
            .subscribe(onSuccess::accept);
    }
}
