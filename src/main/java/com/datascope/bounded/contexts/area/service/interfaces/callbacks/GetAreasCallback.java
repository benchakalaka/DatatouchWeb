package com.datascope.bounded.contexts.area.service.interfaces.callbacks;

import com.datascope.bounded.contexts.area.domian.Area;

public interface GetAreasCallback {
    void areasNotFound();
    void areasFound(Area.List areas);
}
