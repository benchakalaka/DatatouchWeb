package com.datascope.bounded.contexts.sitesettings.service.requests;

import com.datascope.bounded.contexts.core.services.SuperRequestView;

public class GetSiteSettingsRequest extends SuperRequestView {
    public GetSiteSettingsRequest(int siteId) {
        setSiteId(siteId);
    }
}
