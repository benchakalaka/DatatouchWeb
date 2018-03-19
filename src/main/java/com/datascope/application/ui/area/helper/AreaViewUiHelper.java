package com.datascope.application.ui.area.helper;

import com.datascope.application.ui.area.callbacks.IAreaSelectedCallback;
import com.datascope.application.ui.area.elements.AreaGridItem;
import com.datascope.application.ui.utils.helper.Labels;
import com.datascope.application.ui.utils.helper.SuperHelper;
import com.datascope.bounded.contexts.area.domian.Area;
import com.vaadin.ui.Grid;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AreaViewUiHelper extends SuperHelper {

    public AreaViewUiHelper(Labels labels) {
        super(labels);
    }

    public AreaGridItem.List toGridItems(Area.List areas) {
        return areas.stream()
                .map(area -> new AreaGridItem(area.getAreaName(), area.getCreatedAt(), area.getId(), area.getAreaFiles()))
                .collect(Collectors.toCollection(AreaGridItem.List::new));
    }

    public void initAreasGrid(Grid<AreaGridItem> grid, IAreaSelectedCallback callback) {
        grid.removeAllColumns();
        grid.addColumn(AreaGridItem::getAreaName).setCaption(getLabel("area.grid.area.name"));

        grid.addSelectionListener(selectionEvent ->
                selectionEvent.getFirstSelectedItem().ifPresent(
                        item -> callback.areaSelected(selectionEvent.getFirstSelectedItem().get())));
    }
}
