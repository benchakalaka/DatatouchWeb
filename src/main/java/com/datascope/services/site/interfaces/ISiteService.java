package com.datascope.services.site.interfaces;

import com.datascope.services.site.interfaces.callbacks.GetSitesCallback;

public interface ISiteService {
    void getSites(GetSitesCallback callback);
}
