package com.datascope.services.hotspot.interfaces;

import java.time.LocalDate;

public interface IHotspotService {
    void getAreaHotspots(int areaId, LocalDate date, IGetAreaHotspotsCallback callback);
}
