package com.datascope.bounded.contexts.site.service.interfaces;


import com.datascope.bounded.contexts.site.service.interfaces.callbacks.GetSitesCallback;

public interface ISiteService {
    void getSites(GetSitesCallback callback);
}
