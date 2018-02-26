package com.datascope.components.ui;

import com.datascope.DatatouchUI;
import com.datascope.components.ui.grid.items.AreaGridItem;
import com.datascope.components.ui.notifications.DatatouchNotification;
import com.datascope.designs.generated.AreasDesign;
import com.datascope.domain.area.Area;
import com.datascope.services.area.interfaces.IAreaService;
import com.datascope.services.area.interfaces.callbacks.GetAreasCallback;
import com.vaadin.client.renderers.HtmlRenderer;
import com.vaadin.client.widget.grid.RendererCellReference;
import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.renderers.ButtonRenderer;
import com.vaadin.ui.renderers.Renderer;


import javax.annotation.PostConstruct;
import java.util.Locale;

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


//    private ButtonRenderer r = new ButtonRenderer<AreaGridItem>(a -> {
//        a.getColumn().
//    });

    @Override
    public void areasFound(Area.List areas) {
        getAreasGrid().removeAllColumns();
       // Renderer<? super P> c;
      //  getAreasGrid().addColumn(AreaGridItem::hasFiles).setCaption("PDF").setRenderer(r);
//        getAreasGrid().addColumn(AreaGridItem::getAreaName).setCaption("PDF").setRenderer(new HtmlRenderer(), new Converter<String,String>() {
//            @Override
//            public Result<String> convertToModel(String s, ValueContext valueContext) {
//                return Res"";
//            }
//
//            @Override
//            public String convertToPresentation(String s, ValueContext valueContext) {
//                return "";
//            }
//
//
//        });
        getAreasGrid().addColumn(AreaGridItem::getAreaName).setCaption("Area Name");
        getAreasGrid().addColumn(AreaGridItem::getCreatedAt).setCaption("Created");
        getAreasGrid().setItems(areas.toGridItems());
//        getAreasGrid().addColumn(Area::getProgress, new ProgressBarRenderer())
//                .setCaption("Progress")
//                .setExpandRatio(2);
//        getAreasGrid().addColumn(Area::getWeightHistory, new SparklineRenderer())
//                .setCaption("Weight")
//                .setExpandRatio(4);
    }


}
