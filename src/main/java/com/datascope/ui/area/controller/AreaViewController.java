package com.datascope.ui.area.controller;

import com.datascope.bounded.contexts.area.domian.Area;
import com.datascope.ui.area.callbacks.IAreaFileUploadCallback;
import com.datascope.ui.area.callbacks.IAreaSelectedCallback;
import com.datascope.ui.area.dialog.UploadAreaFileDialog;
import com.datascope.ui.area.elements.AreaGridItem;
import com.datascope.ui.utils.helper.Labels;
import com.datascope.ui.utils.helper.UiHelper;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AreaViewController extends UiHelper {

    private final UploadAreaFileDialog uploadAreaFileDialog;

    public AreaViewController(Labels labels, UploadAreaFileDialog uploadAreaFileDialog) {
        super(labels);
        this.uploadAreaFileDialog = uploadAreaFileDialog;
    }

    public void initAreasGrid(Grid<AreaGridItem> grid, IAreaSelectedCallback callback) {
        grid.removeAllColumns();
        grid.addColumn(AreaGridItem::getAreaName)
                .setCaption(getLabel("area.grid.area.name"))
                .setId(getLabel("area.grid.area.name"));

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

    public void initAreaFileUploadGridHeader(Grid<AreaGridItem> grid, IAreaFileUploadCallback callback) {
        Label label = new Label(getLabel("area.grid.area.name"));

        Button btnShowUploadDialog = new Button("", VaadinIcons.PLUS);
        btnShowUploadDialog.addStyleNames(ValoTheme.BUTTON_FRIENDLY, ValoTheme.LABEL_TINY, ValoTheme.BUTTON_SMALL);
        btnShowUploadDialog.addClickListener(event -> {
            uploadAreaFileDialog.setAreaFileUploadCallback(callback);
            UI.getCurrent().addWindow(uploadAreaFileDialog);
        });

        HorizontalLayout layoutHeader = new HorizontalLayout();
        layoutHeader.setSizeFull();

        layoutHeader.addComponent(label);
        layoutHeader.addComponent(btnShowUploadDialog);

        layoutHeader.setComponentAlignment(label, Alignment.MIDDLE_LEFT);
        layoutHeader.setComponentAlignment(btnShowUploadDialog, Alignment.MIDDLE_RIGHT);

        grid.getDefaultHeaderRow()
                .getCell(getLabel("area.grid.area.name"))
                .setComponent(layoutHeader);
    }
}
