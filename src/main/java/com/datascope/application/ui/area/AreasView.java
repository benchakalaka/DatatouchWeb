package com.datascope.application.ui.area;

import com.datascope.DatatouchUI;
import com.datascope.application.ui.utils.notifications.DatatouchNotification;
import com.datascope.application.ui.generated.AreasDesign;
import com.datascope.domain.area.Area;
import com.datascope.services.area.interfaces.IAreaService;
import com.datascope.services.area.interfaces.callbacks.GetAreasCallback;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = AreasView.NAME, ui = {DatatouchUI.class})
public class AreasView extends AreasDesign implements View, GetAreasCallback {

    public static final String NAME = "AreaView";

    private IAreaService areaService;
    private DatatouchNotification notification;
    private String areaFilesUrl;

    public AreasView(IAreaService areaService, DatatouchNotification notification, String areaFilesUrl) {
        this.areaService = areaService;
        this.notification = notification;
        this.areaFilesUrl = areaFilesUrl;
    }

    @PostConstruct
    public void init() {
        AreaViewUiHelper.initAreasGrid(getAreasGrid(), areaFilesUrl);
        getAreas();
    }

    private void getAreas() {
        areaService.getAreas(this);
    }

    @Override
    public void areasFound(Area.List areas) {
        getAreasGrid().setItems(areas.toGridItems());

    }

    //region notification

    @Override
    public void areasNotFound() {
        notification.warn("areas.not.found");
    }

    //endregion notification
}
