package com.datascope.services.hotspot;

import com.datascope.domain.hotspot.Hotspot;
import com.datascope.services.core.Rest;
import com.datascope.services.hotspot.interfaces.IGetAreaHotspotsCallback;
import com.datascope.services.hotspot.interfaces.IHotspotService;
import com.datascope.services.hotspot.requests.GetAreaHotspotsRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class HotspotService implements IHotspotService {

    private static final String GET_HOTSPOTS = "AreaHotspot/GetAllAreaHotspots";
    private Rest rest;

    public HotspotService(Rest rest) {
        this.rest = rest;
    }


    @Override
    public void getAreaHotspots(int areaId, LocalDate date, IGetAreaHotspotsCallback callback) {
        GetAreaHotspotsRequest request = new GetAreaHotspotsRequest(rest.getSiteId(),areaId, date);
        Hotspot.List hotspots = rest.execute(Hotspot.List.class, GET_HOTSPOTS,request);

        if (null == hotspots || hotspots.isEmpty())
            callback.noHotspotsFound();
        else
            callback.hotspotsFound(hotspots);
    }
}
