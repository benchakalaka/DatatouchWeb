package com.datascope.ui.area.callbacks;

public interface IAreaFileUploadCallback {
    void areaFileUploadFinished(String areaName, String areaFileName, byte[] fileData);
}
