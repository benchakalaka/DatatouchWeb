package com.datascope.application.ui.area;

import com.datascope.application.ui.utils.common.DateUtils;
import com.vaadin.ui.Grid;
import com.vaadin.ui.renderers.DateRenderer;

import static com.datascope.application.ui.area.AreaGridItem.AREA_NAME_COLUMN;
import static com.datascope.application.ui.area.AreaGridItem.CREATED_COLUMN;
import static com.datascope.application.ui.area.AreaGridItem.DRAWING;

public class AreaViewUiHelper {
    public static void initAreasGrid(Grid<AreaGridItem> grid, String areaFilesUrl) {
        //        getAreasGrid().addColumn(Area::getProgress, new ProgressBarRenderer())
//                .setCaption("Progress")
//                .setExpandRatio(2);
//        getAreasGrid().addColumn(Area::getWeightHistory, new SparklineRenderer())
//                .setCaption("Weight")
//                .setExpandRatio(4);

        grid.removeAllColumns();
        grid
                .addColumn(AreaGridItem::getAreaName)
                .setCaption(AREA_NAME_COLUMN)
                .setExpandRatio(3);

        grid
                .addColumn(AreaGridItem::getCreatedAt)
                .setCaption(CREATED_COLUMN)
                .setExpandRatio(3)
                .setRenderer(DateUtils::parseDate,new DateRenderer());

        grid.addColumn(area -> area.buildUrl(areaFilesUrl))
                .setCaption(DRAWING)
                .setExpandRatio(1)
                .setRenderer(new com.vaadin.ui.renderers.HtmlRenderer());
    }
}
