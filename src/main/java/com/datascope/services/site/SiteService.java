package com.datascope.services.site;

import com.datascope.domain.site.Site;
import com.datascope.services.core.Rest;
import com.datascope.services.site.interfaces.ISiteService;
import com.datascope.services.site.interfaces.callbacks.GetSitesCallback;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class SiteService implements ISiteService {

    private static String GET_SITES = "ServerInfo/GetSites";
    private Rest rest;

    public SiteService(Rest rest) {
        this.rest = rest;
    }

    @Override
    public void getSites(GetSitesCallback callback) {
        Site.List sites = rest.execute(Site.List.class,GET_SITES, null);

        if (null != sites && !sites.isEmpty())
            callback.sitesFound(sites);
        else
            callback.sitesNotFound();
    }
}
