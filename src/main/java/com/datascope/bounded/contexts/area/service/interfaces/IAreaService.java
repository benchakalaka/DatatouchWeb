package com.datascope.bounded.contexts.area.service.interfaces;

import com.datascope.bounded.contexts.area.service.interfaces.callbacks.GetAreasCallback;

public interface IAreaService {
    void getAreas(GetAreasCallback callback);
}
