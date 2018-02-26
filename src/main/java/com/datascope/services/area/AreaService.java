package com.datascope.services.area;

import com.datascope.domain.area.Area;
import com.datascope.services.area.interfaces.IAreaService;
import com.datascope.services.area.interfaces.callbacks.GetAreasCallback;
import com.datascope.services.area.requests.GetAreasRequest;
import com.datascope.services.core.Rest;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class AreaService implements IAreaService {

    private static final String GET_AREAS = "Area/GetAllAreas";
    private Rest rest;

    public AreaService(Rest rest) {
        this.rest = rest;
    }

    @Override
    public void getAreas(GetAreasCallback callback) {
        GetAreasRequest request = new GetAreasRequest();
        request.setSiteId(rest.getSiteId());
        Area.List areas = rest.execute(Area.List.class, GET_AREAS, request);

        if (null == areas || areas.isEmpty())
            callback.areasNotFound();
        else
            callback.areasFound(areas);
    }
}
