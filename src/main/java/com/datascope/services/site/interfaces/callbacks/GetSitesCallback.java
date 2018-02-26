package com.datascope.services.site.interfaces.callbacks;

import com.datascope.domain.site.Site;

public interface GetSitesCallback {
    void sitesNotFound();
    void sitesFound(Site.List sites);
}
