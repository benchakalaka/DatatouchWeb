package com.datascope.application.ui.hotspot;

import com.datascope.application.ui.hotspot.callbacks.IAreaSelectedCallback;
import com.datascope.application.ui.hotspot.callbacks.IDateSelectedCallback;
import com.datascope.domain.area.Area;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.ProgressBarRenderer;

import java.time.LocalDate;

public class HotspotViewUiHelper {

    public void initGrid(Grid<HotspotGridItem> grid) {
        grid.removeAllColumns();

        grid.addColumn(HotspotGridItem::getId)
                .setCaption(HotspotGridItem.ID_COLUMN)
                .setExpandRatio(3);

        grid.addColumn(HotspotGridItem::getCompanyName)
                .setCaption(HotspotGridItem.COMPANY_NAME_COLUMN)
                .setExpandRatio(2);

        grid.addColumn(HotspotGridItem::getDescription)
                .setCaption(HotspotGridItem.DESCRIPTION_COLUMN)
                .setExpandRatio(2);

        grid.addColumn(HotspotGridItem::getValidFromDate)
                .setCaption(HotspotGridItem.FROM_COLUMN_NAME)
                .setExpandRatio(2);

        grid.addColumn(HotspotGridItem::getValidToDate)
                .setCaption(HotspotGridItem.TO_COLUMN_NAME)
                .setExpandRatio(2);

        grid.addColumn(HotspotGridItem::getCompleted, new ProgressBarRenderer())
                .setCaption(HotspotGridItem.PROGRESS_COLUMN)
                .setExpandRatio(3);
    }

    public void initDatePicker(DateField datePicker, IDateSelectedCallback callback) {
        datePicker.setValue(LocalDate.now());
        datePicker.addValueChangeListener(e -> callback.onDateSelected(e.getValue()));
    }

    public void initAreaComboBox(ComboBox<Area> cbAreas,IAreaSelectedCallback areaSelected ) {
        cbAreas.addValueChangeListener(e -> areaSelected.areaSelected(e.getValue()));
    }
}
