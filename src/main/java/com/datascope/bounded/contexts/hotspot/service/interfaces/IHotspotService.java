package com.datascope.bounded.contexts.hotspot.service.interfaces;

import java.time.LocalDate;

public interface IHotspotService {
    void getAreaHotspots(int areaId, LocalDate date, IGetAreaHotspotsCallback callback);
}
