package com.datascope.bounded.contexts.site.service.interfaces.callbacks;


import com.datascope.bounded.contexts.site.domain.Site;

public interface GetSitesCallback {
    void sitesNotFound();
    void sitesFound(Site.List sites);
}
