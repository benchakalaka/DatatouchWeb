package com.datascope.ui.area;


import com.datascope.bounded.contexts.area.domian.Area;
import com.datascope.bounded.contexts.area.service.interfaces.IAreaService;
import com.datascope.bounded.contexts.area.service.interfaces.callbacks.GetAreasCallback;
import com.datascope.ui.area.callbacks.IAreaSelectedCallback;
import com.datascope.ui.area.elements.AreaGridItem;
import com.datascope.ui.area.controller.AreaViewController;
import com.datascope.ui.generated.AreasDesign;
import com.datascope.ui.utils.notifications.Messages;
import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.github.appreciated.app.layout.annotations.NavigatorViewName;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

import javax.annotation.PostConstruct;

@UIScope
@MenuCaption("Areas")
@MenuIcon(VaadinIcons.BOOK)
@NavigatorViewName(AreasView.NAME)
@SpringView(name = AreasView.NAME)
public class AreasView extends AreasDesign implements View, GetAreasCallback, IAreaSelectedCallback {
    static final String NAME = "AreaView";

    private IAreaService areaService;
    private Messages messages;
    private String areaFilesUrl;
    private AreaViewController controller;

    public AreasView(
            IAreaService areaService,
            Messages messages,
            String areaFilesUrl,
            AreaViewController controller) {
        this.areaService = areaService;
        this.messages = messages;
        this.areaFilesUrl = areaFilesUrl;
        this.controller = controller;
    }

    @PostConstruct
    public void init() {
        controller.initAreasGrid(getAreasGrid(), this);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
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
        messages.warn("areas.not.found");
    }

}
