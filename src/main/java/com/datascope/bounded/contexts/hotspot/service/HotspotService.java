package com.datascope.bounded.contexts.hotspot.service;

import com.datascope.bounded.contexts.hotspot.domain.Hotspot;
import com.datascope.bounded.contexts.hotspot.service.interfaces.IGetAreaHotspotsCallback;
import com.datascope.bounded.contexts.hotspot.service.interfaces.IHotspotService;
import com.datascope.bounded.contexts.hotspot.service.requests.GetAreaHotspotsRequest;
import com.datascope.bounded.contexts.core.services.IRestClient;
import com.datascope.bounded.contexts.core.services.concrete.SuperRestService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

@Service
public class HotspotService extends SuperRestService implements IHotspotService {

    private static final String GET_HOTSPOTS = "AreaHotspot/GetAllAreaHotspots";

    public HotspotService(IRestClient restClient) {
        super(restClient);
    }

    @Override
    public void getAreaHotspots(int areaId, LocalDate date, IGetAreaHotspotsCallback callback) {
        GetAreaHotspotsRequest request = new GetAreaHotspotsRequest(areaId, date);
        Hotspot.List hotspots = rest.post(Hotspot.List.class, GET_HOTSPOTS,request);

        if (isNotEmpty(hotspots))
            callback.noHotspotsFound();
        else
            callback.hotspotsFound(hotspots);
    }
}
