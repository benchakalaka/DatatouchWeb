package com.datascope.bounded.contexts.area.service.requests;

import com.datascope.bounded.contexts.core.services.SuperRequestView;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateAreaFromImageRequest extends SuperRequestView {

    @JsonProperty("AreaFileName")
    public String fileName;

    @JsonProperty("NewAreaName")
    public String areaName;

    @JsonProperty("AreaFile")
    public byte[] file;

    @JsonProperty("RotationAngle")
    public int rotationAngle;

    public CreateAreaFromImageRequest(int siteId, String fileName, String areaName, byte[] fileData) {
        this.setSiteId(siteId);
        this.fileName = fileName;
        this.areaName = areaName;
        this.rotationAngle = 0;
        this.file = fileData;
    }

}
