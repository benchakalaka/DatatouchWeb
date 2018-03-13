package com.datascope.application.ui.area;

import com.datascope.application.ui.area.callbacks.IAreaSelectedCallback;
import com.datascope.application.ui.area.elements.AreaGridItem;
import com.datascope.bounded.contexts.area.domian.Area;
import com.vaadin.ui.Grid;

import java.util.stream.Collectors;

import static com.datascope.application.ui.area.elements.AreaGridItem.AREA_NAME_COLUMN;

public class AreaViewUiHelper {

    public AreaGridItem.List toGridItems(Area.List areas) {
        return areas.stream()
                .map(area -> new AreaGridItem(area.getAreaName(), area.getCreatedAt(), area.getId(), area.getAreaFiles()))
                .collect(Collectors.toCollection(AreaGridItem.List::new));
    }

    public void initAreasGrid(Grid<AreaGridItem> grid, IAreaSelectedCallback callback) {

//        getAreasGrid().addColumn(Area::getWeightHistory, new SparklineRenderer())
//                .setCaption("Weight")
////                .setExpandRatio(4);

        grid.removeAllColumns();
        grid.addColumn(AreaGridItem::getAreaName).setCaption(AREA_NAME_COLUMN);


        grid.addSelectionListener((selectionEvent -> selectionEvent.getFirstSelectedItem()
        .ifPresent(item ->  callback.areaSelected(selectionEvent.getFirstSelectedItem().get()))));

//        grid
//                .addColumn(AreaGridItem::getCreatedAt)
//                .setCaption(CREATED_COLUMN)
//                .setExpandRatio(3)
//                .setRenderer(DateUtils::parseDate,new DateRenderer());
//
//        grid.addColumn(area -> area.buildFileUrl(areaFilesUrl))
//                .setCaption(DRAWING)
//                .setExpandRatio(1)
//                .setRenderer(new com.vaadin.ui.renderers.HtmlRenderer());
    }
}
