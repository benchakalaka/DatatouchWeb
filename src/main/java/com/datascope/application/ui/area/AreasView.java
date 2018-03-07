package com.datascope.application.ui.area;

import com.datascope.DatatouchUI;
import com.datascope.application.ui.area.callbacks.IAreaSelectedCallback;
import com.datascope.application.ui.utils.notifications.DatatouchNotification;
import com.datascope.application.ui.generated.AreasDesign;
import com.datascope.domain.area.Area;
import com.datascope.services.area.interfaces.IAreaService;
import com.datascope.services.area.interfaces.callbacks.GetAreasCallback;
import com.vaadin.navigator.View;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = AreasView.NAME, ui = {DatatouchUI.class})
public class AreasView extends AreasDesign implements View, GetAreasCallback, IAreaSelectedCallback {

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
        AreaViewUiHelper.initAreasGrid(getAreasGrid(), areaFilesUrl, this);
        getAreas();
    }

    private void getAreas() {
        areaService.getAreas(this);
    }

    @Override
    public void areasFound(Area.List areas) {
        getAreasGrid().setItems(areas.toGridItems());

    }

    @Override
    public void areaSelected(AreaGridItem areaGridItem) {
        String fileUrl = areaGridItem.buildFileUrl(areaFilesUrl);
        getBrowser().setSource(new ExternalResource(fileUrl));
    }

    //region notification

    @Override
    public void areasNotFound() {
        notification.warn("areas.not.found");
    }



    //endregion notification
}
