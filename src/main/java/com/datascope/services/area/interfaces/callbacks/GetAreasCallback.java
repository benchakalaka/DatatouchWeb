package com.datascope.services.area.interfaces.callbacks;

import com.datascope.domain.area.Area;

public interface GetAreasCallback {
    void areasNotFound();
    void areasFound(Area.List areas);
}
