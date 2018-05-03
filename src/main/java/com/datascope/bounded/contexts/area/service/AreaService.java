package com.datascope.bounded.contexts.area.service;

import com.datascope.bounded.contexts.area.domian.Area;
import com.datascope.bounded.contexts.area.service.interfaces.callbacks.GetAreasCallback;
import com.datascope.bounded.contexts.area.service.interfaces.GetAreasRequest;
import com.datascope.bounded.contexts.area.service.interfaces.IAreaService;
import com.datascope.bounded.contexts.area.service.requests.CreateAreaFromImageRequest;
import com.datascope.bounded.contexts.area.service.requests.CreateAreaFromPdfRequest;
import com.datascope.bounded.contexts.core.services.IRestClient;
import com.datascope.bounded.contexts.core.services.concrete.SuperRestService;
import com.datascope.bounded.contexts.core.services.concrete.UnirestClient;
import com.vaadin.spring.annotation.SpringComponent;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.function.Consumer;

@SpringComponent
public class AreaService extends SuperRestService implements IAreaService {

    private static final String GET_AREAS = "Area/GetAllAreas";
    private static final String CREATE_AREA_FROM_PDF = "Area/CreateFromPdf";
    private static final String CREATE_AREA_FROM_IMAGE = "Area/CreateFromImage";

    public AreaService(IRestClient restClient) {
        super(restClient);
    }

    private int getSiteId() {
        return ((UnirestClient)rest).getSiteId();
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

    @Override
    public void createAreaFromPdf(String areaName, String fileName, byte[] fileData, Consumer<Area> onSuccess) {
        CreateAreaFromPdfRequest request = new CreateAreaFromPdfRequest(getSiteId(), fileName, areaName, fileData);
        Observable.just(rest.post(Area.class, CREATE_AREA_FROM_PDF, request))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .subscribe(onSuccess::accept);
    }

    @Override
    public void createAreaFromImage(String areaName, String fileName, byte[] fileData, Consumer<Area> onSuccess) {
        CreateAreaFromImageRequest request = new CreateAreaFromImageRequest(getSiteId(), fileName, areaName, fileData);
        Observable.just(rest.post(Area.class, CREATE_AREA_FROM_IMAGE, request))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .subscribe(onSuccess::accept);
    }
}
