package com.datascope.bounded.contexts.site.service;

import com.datascope.bounded.contexts.site.domain.Site;
import com.datascope.bounded.contexts.site.service.interfaces.ISiteService;
import com.datascope.core.services.IRestClient;
import com.datascope.core.services.concrete.SuperRestService;
import com.datascope.bounded.contexts.site.service.interfaces.callbacks.GetSitesCallback;
import org.springframework.stereotype.Service;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

@Service
public class SiteService extends SuperRestService implements ISiteService {

    private static String GET_SITES = "ServerInfo/GetSites";

    public SiteService(IRestClient rest) {
        super(rest);
    }

    @Override
    public void getSites(GetSitesCallback callback) {
        Site.List sites = rest.post(Site.List.class,GET_SITES);

        if (isNotEmpty(sites))
            callback.sitesFound(sites);
        else
            callback.sitesNotFound();
    }
}