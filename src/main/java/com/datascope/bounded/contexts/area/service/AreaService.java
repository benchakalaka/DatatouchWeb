package com.datascope.bounded.contexts.area.service;

import com.datascope.bounded.contexts.area.domian.Area;
import com.datascope.bounded.contexts.area.service.interfaces.GetAreasCallback;
import com.datascope.bounded.contexts.area.service.interfaces.GetAreasRequest;
import com.datascope.bounded.contexts.area.service.interfaces.IAreaService;
import com.datascope.core.services.IRestClient;
import com.datascope.core.services.concrete.SuperRestService;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class AreaService extends SuperRestService implements IAreaService {

    private static final String GET_AREAS = "Area/GetAllAreas";

    public AreaService(IRestClient restClient) {
        super(restClient);
    }

    @Override
    public void getAreas(GetAreasCallback callback) {
        GetAreasRequest request = new GetAreasRequest();
        Area.List areas = rest.post(Area.List.class, GET_AREAS, request);

        if (null == areas || areas.isEmpty())
            callback.areasNotFound();
        else
            callback.areasFound(areas);
    }
}
