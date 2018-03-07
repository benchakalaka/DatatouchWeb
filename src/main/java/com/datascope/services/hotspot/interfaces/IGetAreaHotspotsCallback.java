package com.datascope.services.hotspot.interfaces;

import com.datascope.domain.hotspot.Hotspot;

public interface IGetAreaHotspotsCallback {
    void noHotspotsFound();

    void hotspotsFound(Hotspot.List hotspots);
}
