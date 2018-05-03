package com.datascope.ui.area;

import com.datascope.bounded.contexts.area.domian.Area;
import com.datascope.bounded.contexts.area.service.interfaces.IAreaService;
import com.datascope.bounded.contexts.area.service.interfaces.callbacks.GetAreasCallback;
import com.datascope.ui.area.callbacks.IAreaFileUploadCallback;
import com.datascope.ui.area.callbacks.IAreaSelectedCallback;
import com.datascope.ui.area.callbacks.OnUploadAreaClickedCallback;
import com.datascope.ui.area.controller.AreaViewController;
import com.datascope.ui.area.elements.AreaGridItem;
import com.datascope.ui.generated.AreasDesign;
import com.datascope.ui.utils.common.FileUtilsWrapper;
import com.datascope.ui.utils.notifications.Notifications;
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
@MenuIcon(VaadinIcons.SITEMAP)
@NavigatorViewName(AreasView.NAME)
@SpringView(name = AreasView.NAME)
public class AreasView extends AreasDesign implements View, GetAreasCallback, IAreaSelectedCallback, OnUploadAreaClickedCallback,
        IAreaFileUploadCallback {
    static final String NAME = "AreaView";

    private IAreaService areaService;
    private Notifications notifications;
    private String areaFilesUrl;
    private AreaViewController controller;


    public AreasView(IAreaService areaService, Notifications notifications, String areaFilesUrl, AreaViewController controller) {
        this.areaService = areaService;
        this.notifications = notifications;
        this.areaFilesUrl = areaFilesUrl;
        this.controller = controller;
    }

    @PostConstruct
    public void init() {
        controller.initAreasGrid(getAreasGrid(), this);
        controller.initAreaFileUploadGridHeader(getAreasGrid(), this);
        areaService.getAreas(this);
    }

    @Override
    public void areasFound(Area.List areas) {
        controller.setAreasToGrid(areas, getAreasGrid());
    }

    @Override
    public void areaSelected(AreaGridItem areaGridItem) {
        getBrowser().setSource(new ExternalResource(areaGridItem.buildFileUrl(areaFilesUrl)));
    }

    @Override
    public void areasNotFound() {
        notifications.warn("areas.not.found");
    }

    @Override
    public void uploadAreaClicked() {

    }

    @Override
    public void areaFileUploadFinished(String areaName, String areaFileName, byte[] fileData) {
        if (FileUtilsWrapper.isPdfFile(areaFileName)) {
            areaService.createAreaFromPdf(areaName, areaFileName, fileData, this::areaCreated);
        } else {
            areaService.createAreaFromImage(areaName, areaFileName, fileData, this::areaCreated);
        }
    }

    private void areaCreated(Area area) {
        getUI().access(() -> {
            areaService.getAreas(this);
            notifications.success("areas.creation.success");
        });
    }
}
