package com.datascope.ui.area;


import com.datascope.bounded.contexts.area.domian.Area;
import com.datascope.bounded.contexts.area.service.interfaces.IAreaService;
import com.datascope.bounded.contexts.area.service.interfaces.callbacks.GetAreasCallback;
import com.datascope.ui.area.callbacks.IAreaSelectedCallback;
import com.datascope.ui.area.elements.AreaGridItem;
import com.datascope.ui.area.helper.AreaViewUiHelper;
import com.datascope.ui.generated.AreasDesign;
import com.datascope.ui.utils.notifications.DatatouchNotification;
import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.github.appreciated.app.layout.annotations.NavigatorViewName;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

import javax.annotation.PostConstruct;

@UIScope
@MenuCaption("Areas")
@MenuIcon(VaadinIcons.ADJUST)
@NavigatorViewName(AreasView.NAME)
@SpringView(name = AreasView.NAME)
public class AreasView extends AreasDesign implements View, GetAreasCallback, IAreaSelectedCallback {

    //TODO: Select first area
    static final String NAME = "AreaView";

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