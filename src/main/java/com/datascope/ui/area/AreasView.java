package com.datascope.ui.area;


import com.datascope.bounded.contexts.area.domian.Area;
import com.datascope.bounded.contexts.area.service.interfaces.IAreaService;
import com.datascope.bounded.contexts.area.service.interfaces.callbacks.GetAreasCallback;
import com.datascope.ui.area.callbacks.IAreaSelectedCallback;
import com.datascope.ui.area.callbacks.OnUploadAreaClickedCallback;
import com.datascope.ui.area.controller.AreaViewController;
import com.datascope.ui.area.elements.AreaGridItem;
import com.datascope.ui.generated.AreasDesign;
import com.datascope.ui.utils.notifications.Notifications;
import com.github.appreciated.app.layout.annotations.MenuCaption;
import com.github.appreciated.app.layout.annotations.MenuIcon;
import com.github.appreciated.app.layout.annotations.NavigatorViewName;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.StreamVariable;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.wcs.wcslib.vaadin.widget.multifileupload.component.FileDetail;
import com.wcs.wcslib.vaadin.widget.multifileupload.component.MultiUploadHandler;
import com.wcs.wcslib.vaadin.widget.multifileupload.ui.UploadStateWindow;

import javax.annotation.PostConstruct;
import java.io.OutputStream;
import java.util.Collection;

@UIScope
@MenuCaption("Areas")
@MenuIcon(VaadinIcons.BOOK)
@NavigatorViewName(AreasView.NAME)
@SpringView(name = AreasView.NAME)
public class AreasView extends AreasDesign implements View, GetAreasCallback, IAreaSelectedCallback, OnUploadAreaClickedCallback {
    static final String NAME = "AreaView";

    private IAreaService areaService;
    private Notifications notifications;
    private String areaFilesUrl;
    private AreaViewController controller;
    private MultiUploadHandler handler = new MultiUploadHandler() {
        @Override
        public void streamingStarted(StreamVariable.StreamingStartEvent streamingStartEvent) {

        }

        @Override
        public void streamingFinished(StreamVariable.StreamingEndEvent streamingEndEvent) {

        }

        @Override
        public OutputStream getOutputStream() {
            return null;
        }

        @Override
        public void streamingFailed(StreamVariable.StreamingErrorEvent streamingErrorEvent) {

        }

        @Override
        public void onProgress(StreamVariable.StreamingProgressEvent streamingProgressEvent) {

        }

        @Override
        public void filesQueued(Collection<FileDetail> collection) {

        }
    };

    public AreasView(IAreaService areaService, Notifications notifications, String areaFilesUrl, AreaViewController controller) {
        this.areaService = areaService;
        this.notifications = notifications;
        this.areaFilesUrl = areaFilesUrl;
        this.controller = controller;
    }



    @PostConstruct
    public void init() {
        controller.initAreasGrid(getAreasGrid(), this);
      //  controller.initUploadNewAreaButton(getBtnAddArea(), this);
        areaService.getAreas(this);




        UploadStateWindow window = new UploadStateWindow();

        getMultiFileUpload().setHandler(handler);




        getMultiFileUpload().setAcceptFilter("file/pdf");
    }

    @Override
    public void areasFound(Area.List areas) {
        controller.setAreasToGrid(areas, getAreasGrid());
    }

    @Override
    public void areaSelected(AreaGridItem areaGridItem) {
        getBrowser().setSource(new ExternalResource(areaGridItem.buildFileUrl(areaFilesUrl)));
    }

    @Override
    public void areasNotFound() {
        notifications.warn("areas.not.found");
    }

    @Override
    public void uploadAreaClicked() {

    }
}
