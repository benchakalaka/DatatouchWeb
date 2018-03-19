package com.datascope.application.ui.area;

import com.datascope.application.ui.area.callbacks.IAreaSelectedCallback;
import com.datascope.application.ui.area.elements.AreaGridItem;
import com.datascope.application.ui.area.helper.AreaViewUiHelper;
import com.datascope.application.ui.generated.AreasDesign;
import com.datascope.application.ui.utils.notifications.DatatouchNotification;
import com.datascope.bounded.contexts.area.domian.Area;
import com.datascope.bounded.contexts.area.service.interfaces.IAreaService;
import com.datascope.bounded.contexts.area.service.interfaces.callbacks.GetAreasCallback;
import com.vaadin.navigator.View;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = AreasView.NAME)
public class AreasView extends AreasDesign implements View, GetAreasCallback, IAreaSelectedCallback {

    //TODO: Select first area
    public static final String NAME = "AreaView";

    private IAreaService areaService;
    private DatatouchNotification notification;
    private String areaFilesUrl;
    private AreaViewUiHelper helper;

    public AreasView(
            IAreaService areaService,
            DatatouchNotification notification,
            String areaFilesUrl,
            AreaViewUiHelper helper) {
        this.areaService = areaService;
        this.notification = notification;
        this.areaFilesUrl = areaFilesUrl;
        this.helper = helper;
    }

    @PostConstruct
    public void init() {
        helper.initAreasGrid(getAreasGrid(), this);
        getAreas();
    }

    private void getAreas() {
        areaService.getAreas(this);
    }

    @Override
    public void areasFound(Area.List areas) {
        getAreasGrid().setItems(helper.toGridItems(areas));
    }

    @Override
    public void areaSelected(AreaGridItem areaGridItem) {
        getBrowser().setSource(new ExternalResource(areaGridItem.buildFileUrl(areaFilesUrl)));
    }

    @Override
    public void areasNotFound() {
        notification.warn("areas.not.found");
    }

}
