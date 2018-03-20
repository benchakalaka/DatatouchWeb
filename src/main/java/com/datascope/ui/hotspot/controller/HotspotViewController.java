package com.datascope.ui.hotspot.controller;

import com.datascope.bounded.contexts.area.domian.Area;
import com.datascope.ui.hotspot.callbacks.IAreaSelectedCallback;
import com.datascope.ui.hotspot.callbacks.IDateSelectedCallback;
import com.datascope.ui.hotspot.elements.AreaComboBoxItem;
import com.datascope.ui.hotspot.elements.HotspotGridItem;
import com.datascope.ui.utils.helper.Labels;
import com.datascope.ui.utils.helper.UiHelper;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.ProgressBarRenderer;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Component
public class HotspotViewController extends UiHelper {

    private static final int TABLE_DEFAULT_EXPAND_RATIO = 2;
    private static final int PROGRESS_EXPAND_RATIO = 3;

    public HotspotViewController(Labels labels) {
        super(labels);
    }

    public void initGrid(Grid<HotspotGridItem> grid) {
        grid.removeAllColumns();

        grid.addColumn(HotspotGridItem::getId)
                .setCaption(getLabel("hotspot.grid.id"))
                .setExpandRatio(TABLE_DEFAULT_EXPAND_RATIO);

        grid.addColumn(HotspotGridItem::getCompanyName)
                .setCaption(getLabel("hotspot.grid.company.name"))
                .setExpandRatio(TABLE_DEFAULT_EXPAND_RATIO);

        grid.addColumn(HotspotGridItem::getDescription)
                .setCaption(getLabel("hotspot.grid.description"))
                .setExpandRatio(TABLE_DEFAULT_EXPAND_RATIO);

        grid.addColumn(HotspotGridItem::getValidFromDate)
                .setCaption(getLabel("hotspot.grid.from"))
                .setExpandRatio(TABLE_DEFAULT_EXPAND_RATIO);

        grid.addColumn(HotspotGridItem::getValidToDate)
                .setCaption(getLabel("hotspot.grid.to"))
                .setExpandRatio(TABLE_DEFAULT_EXPAND_RATIO);

        grid.addColumn(HotspotGridItem::getCompleted, new ProgressBarRenderer())
                .setCaption(getLabel("hotspot.grid.progress"))
                .setExpandRatio(PROGRESS_EXPAND_RATIO);
    }

    public void initDatePicker(DateField datePicker, IDateSelectedCallback callback) {
        datePicker.setValue(LocalDate.now());
        datePicker.addValueChangeListener(e -> callback.onDateSelected(e.getValue()));
    }

    public void initAreaComboBox(ComboBox<AreaComboBoxItem> cbAreas, IAreaSelectedCallback areaSelected) {
        cbAreas.addValueChangeListener(e -> areaSelected.areaSelected(e.getValue()));
    }

    public void setAreas(ComboBox<AreaComboBoxItem> cbAreas, Area.List items) {
        AreaComboBoxItem.List areaComboBoxItems = toComboBoxItems(items);
        cbAreas.setItems(areaComboBoxItems);
        if (CollectionUtils.isNotEmpty(areaComboBoxItems))
            cbAreas.setSelectedItem(areaComboBoxItems.get(0));
    }


    private AreaComboBoxItem.List toComboBoxItems(Area.List areas) {
        return CollectionUtils.isEmpty(areas) ? new AreaComboBoxItem.List() : areas
                .stream()
                .map(AreaComboBoxItem::FromArea)
                .collect(Collectors.toCollection(AreaComboBoxItem.List::new));
    }

}
