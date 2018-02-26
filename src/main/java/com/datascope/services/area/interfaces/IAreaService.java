package com.datascope.services.area.interfaces;

import com.datascope.services.area.interfaces.callbacks.GetAreasCallback;

public interface IAreaService {
    void getAreas(GetAreasCallback callback);
}
