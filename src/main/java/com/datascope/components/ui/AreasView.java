package com.datascope.components.ui;

import com.datascope.DatatouchUI;
import com.datascope.components.ui.notifications.DatatouchNotification;
import com.datascope.designs.generated.AreasDesign;
import com.datascope.domain.area.Area;
import com.datascope.services.area.interfaces.IAreaService;
import com.datascope.services.area.interfaces.callbacks.GetAreasCallback;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.renderers.ProgressBarRenderer;

import javax.annotation.PostConstruct;

@UIScope
@SpringView(name = AreasView.NAME, ui = {DatatouchUI.class})
public class AreasView extends AreasDesign implements View, GetAreasCallback {
    public static final String NAME = "AreaView";
    private IAreaService areaService;
    private DatatouchNotification notification;

    public AreasView(IAreaService areaService, DatatouchNotification notification) {
        this.areaService = areaService;
        this.notification = notification;
    }

    @PostConstruct
    public void init(){
        getAreas();
    }



    private void getAreas() {
        areaService.getAreas(this);
    }

    @Override
    public void areasNotFound() {
        notification.warn("areas.not.found");
    }

    @Override
    public void areasFound(Area.List areas) {
        getAreasGrid().setItems(areas);
        getAreasGrid().addColumn(Area::getProgress, new ProgressBarRenderer())
                .setCaption("Progress")
                .setExpandRatio(2);
//        getAreasGrid().addColumn(Area::getWeightHistory, new SparklineRenderer())
//                .setCaption("Weight")
//                .setExpandRatio(4);
    }


}
