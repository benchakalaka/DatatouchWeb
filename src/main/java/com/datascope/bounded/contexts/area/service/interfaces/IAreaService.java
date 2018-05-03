package com.datascope.bounded.contexts.area.service.interfaces;

import com.datascope.bounded.contexts.area.domian.Area;
import com.datascope.bounded.contexts.area.service.interfaces.callbacks.GetAreasCallback;

import java.util.function.Consumer;

public interface IAreaService {
    void getAreas(GetAreasCallback callback);
    void createAreaFromPdf(String areaName, String fileName, byte[] fileData, Consumer<Area> onSuccess);
    void createAreaFromImage(String areaName, String fileName, byte[] fileData, Consumer<Area> onSuccess);
}
