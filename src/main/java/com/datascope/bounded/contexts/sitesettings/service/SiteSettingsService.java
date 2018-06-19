package com.datascope.bounded.contexts.sitesettings.service;

import com.datascope.bounded.contexts.core.services.IRestClient;
import com.datascope.bounded.contexts.core.services.concrete.SuperRestService;
import com.datascope.bounded.contexts.core.services.concrete.UnirestClient;
import com.datascope.bounded.contexts.sitesettings.domain.SiteSettings;
import com.datascope.bounded.contexts.sitesettings.domain.UserSettings;
import com.datascope.bounded.contexts.sitesettings.service.interfaces.ISiteSettingsService;
import com.datascope.bounded.contexts.sitesettings.service.requests.GetSiteSettingsRequest;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.function.Consumer;

@Service
public class SiteSettingsService extends SuperRestService implements ISiteSettingsService {

    private static final String GET_SITE_SETTINGS = "SiteSettings/GetSiteSettings/";

    public SiteSettingsService(IRestClient client) {
        super(client);
    }

    private int getSiteId() {
        return ((UnirestClient)rest).getSiteId();
    }

    @Override
    public void getUserSettings(Consumer<UserSettings.List> onSuccess) {
        GetSiteSettingsRequest request = new GetSiteSettingsRequest(getSiteId());
        Observable.just(rest.post(SiteSettings.class, GET_SITE_SETTINGS, request))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .map(siteSettings -> {
                    if (siteSettings != null && CollectionUtils.isNotEmpty(siteSettings.getUserSettings())) {
                        return siteSettings.getUserSettings();
                    }
                    return UserSettings.List.empty();
                })
                .subscribe(onSuccess::accept);
    }
}
