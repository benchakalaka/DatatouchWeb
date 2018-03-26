package com.datascope.ui.area.controller;

import com.datascope.bounded.contexts.area.domian.Area;
import com.datascope.ui.area.callbacks.IAreaSelectedCallback;
import com.datascope.ui.area.elements.AreaGridItem;
import com.datascope.ui.utils.helper.Labels;
import com.datascope.ui.utils.helper.UiHelper;
import com.vaadin.ui.Grid;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AreaViewController extends UiHelper {

    public AreaViewController(Labels labels) {
        super(labels);
    }

    public void initAreasGrid(Grid<AreaGridItem> grid, IAreaSelectedCallback callback) {
        grid.removeAllColumns();
        grid.addColumn(AreaGridItem::getAreaName).setCaption(getLabel("area.grid.area.name"));

        grid.addSelectionListener(selectionEvent ->
                selectionEvent.getFirstSelectedItem().ifPresent(
                        item -> callback.areaSelected(selectionEvent.getFirstSelectedItem().get())));
    }

    public void setAreasToGrid(Area.List areas, Grid<AreaGridItem> areasGrid) {
        AreaGridItem.List items = toGridItems(areas);
        areasGrid.setItems(items);

        if (CollectionUtils.isNotEmpty(items))
            areasGrid.select(items.get(0));
    }

    private AreaGridItem.List toGridItems(Area.List areas) {
        return areas.stream()
                .map(area -> new AreaGridItem(area.getAreaName(), area.getCreatedAt(), area.getId(), area.getAreaFiles()))
                .collect(Collectors.toCollection(AreaGridItem.List::new));
    }

//    public void initUploadNewAreaButton(Button btnAddArea, OnUploadAreaClickedCallback view) {
//        btnAddArea.addClickListener(clickEvent -> view.uploadAreaClicked());
//    }
}
