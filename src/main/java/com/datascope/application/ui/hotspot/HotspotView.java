package com.datascope.application.ui.hotspot;

import com.datascope.DatatouchUI;
import com.datascope.application.ui.generated.HotspotDesign;
import com.datascope.application.ui.hotspot.callbacks.IAreaSelectedCallback;
import com.datascope.application.ui.hotspot.callbacks.IDateSelectedCallback;
import com.datascope.domain.area.Area;
import com.datascope.domain.hotspot.Hotspot;
import com.datascope.services.area.interfaces.IAreaService;
import com.datascope.services.area.interfaces.callbacks.GetAreasCallback;
import com.datascope.services.hotspot.interfaces.IGetAreaHotspotsCallback;
import com.datascope.services.hotspot.interfaces.IHotspotService;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@UIScope
@SpringView(name = HotspotView.NAME, ui = {DatatouchUI.class})
public class HotspotView extends HotspotDesign implements View, IGetAreaHotspotsCallback, IDateSelectedCallback, GetAreasCallback, IAreaSelectedCallback {
    public static final String NAME = "HotspotView";
    private IHotspotService service;
    private IAreaService areaService;
    private HotspotViewUiHelper helper = new HotspotViewUiHelper();

    public HotspotView(IHotspotService service,IAreaService areaService) {
        this.service = service;
        this.areaService = areaService;
    }

    @PostConstruct
    public void init(){
        helper.initAreaComboBox(getCbAreas(), this);
        helper.initGrid(getHotspotGrid());
        helper.initDatePicker(getDatePicker(), this);
        getAreas();
        getHotspots( );
    }

    private void getAreas() {
        areaService.getAreas(this);
    }

    private void getHotspots() {
        service.getAreaHotspots(163242, getSelectedDate() ,this);
    }

    @Override
    public void noHotspotsFound() {
        getHotspotGrid().setItems(HotspotGridItem.List.empty());
    }

    @Override
    public void hotspotsFound(Hotspot.List hotspots) {
        getHotspotGrid().setItems(hotspots.toGridItems());
    }

    @Override
    public void onDateSelected(LocalDate value) {
        getHotspots();
    }

    private LocalDate getSelectedDate() {
        return getDatePicker().getValue();
    }

    @Override
    public void areasNotFound() {

    }

    @Override
    public void areasFound(Area.List areas) {
        getCbAreas().setItems(areas);
    }

    @Override
    public void areaSelected(Area area) {

    }
}
