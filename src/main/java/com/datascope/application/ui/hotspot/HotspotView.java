package com.datascope.application.ui.hotspot;

import com.datascope.application.ui.DatatouchUI;
import com.datascope.application.ui.generated.HotspotDesign;
import com.datascope.application.ui.hotspot.callbacks.IAreaSelectedCallback;
import com.datascope.application.ui.hotspot.callbacks.IDateSelectedCallback;
import com.datascope.application.ui.hotspot.elements.AreaComboBoxItem;
import com.datascope.application.ui.hotspot.elements.HotspotGridItem;
import com.datascope.application.ui.utils.notifications.DatatouchNotification;
import com.datascope.bounded.contexts.area.domian.Area;
import com.datascope.bounded.contexts.area.service.interfaces.IAreaService;
import com.datascope.bounded.contexts.hotspot.domain.Hotspot;
import com.datascope.bounded.contexts.area.service.interfaces.GetAreasCallback;
import com.datascope.bounded.contexts.hotspot.service.interfaces.IGetAreaHotspotsCallback;
import com.datascope.bounded.contexts.hotspot.service.interfaces.IHotspotService;
import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

import static java.util.Optional.ofNullable;

@UIScope
@SpringView(name = HotspotView.NAME, ui = {DatatouchUI.class})
public class HotspotView extends HotspotDesign implements
        View,
        IGetAreaHotspotsCallback,
        IDateSelectedCallback,
        GetAreasCallback,
        IAreaSelectedCallback {

    public static final String NAME = "HotspotView";
    private IHotspotService service;
    private IAreaService areaService;
    private DatatouchNotification notifications;
    private HotspotViewUiHelper helper = new HotspotViewUiHelper();

    public HotspotView(IHotspotService service, IAreaService areaService, DatatouchNotification notifications) {
        this.service = service;
        this.areaService = areaService;
        this.notifications = notifications;
    }

    @PostConstruct
    public void init() {
        helper.initAreaComboBox(getCbAreas(), this);
        helper.initGrid(getHotspotGrid());
        helper.initDatePicker(getDatePicker(), this);
        getAreas();
    }

    private void getAreas() {
        areaService.getAreas(this);
    }

    private void getHotspots(int areaId) {
        service.getAreaHotspots(areaId, getSelectedDate(), this);
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
        getHotspots(getSelectedAreaId());
    }

    private LocalDate getSelectedDate() {
        return getDatePicker().getValue();
    }

    @Override
    public void areasNotFound() {
        notifications.warn("areas.not.found");
    }

    @Override
    public void areasFound(Area.List areas) {
        // TODO: to messages
        ofNullable(areas)
                .ifPresent(item -> notifications.success(String.format("%s areas found", areas.size())));

        helper.setAreas(getCbAreas(), areas);
    }

    @Override
    public void areaSelected(AreaComboBoxItem area) {
        getHotspots(area.getAreaId());
    }

    private int getSelectedAreaId() {
        return getCbAreas().getSelectedItem().isPresent() ? getCbAreas().getSelectedItem().get().getAreaId() : 0;
    }
}
