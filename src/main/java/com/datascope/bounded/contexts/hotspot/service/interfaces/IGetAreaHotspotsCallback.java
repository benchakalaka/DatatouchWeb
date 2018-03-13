package com.datascope.bounded.contexts.hotspot.service.interfaces;

import com.datascope.bounded.contexts.hotspot.domain.Hotspot;

public interface IGetAreaHotspotsCallback {

    void noHotspotsFound();
    void hotspotsFound(Hotspot.List hotspots);
}
