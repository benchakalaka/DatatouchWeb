package com.datascope.bounded.contexts.area.domian;


import com.datascope.core.domain.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class AreaFile extends Model {

    @JsonProperty("Id")
    private int id;

    @JsonProperty("FileTypeId")
    private int fileTypeId;

    @JsonProperty("FileName")
    private String fileName;

    @JsonProperty("FileDirectory")
    private String fileDirectory;

    @JsonProperty("AreaId")
    private int areaId;

    public int getId() { return this.id; }

    public void setId(int Id) { this.id = Id; }

    public String getFileName() { return this.fileName; }

    public void setFileName(String FileName) { this.fileName = FileName; }

    public String getFileDirectory() { return this.fileDirectory; }

    public void setFileDirectory(String FileDirectory) { this.fileDirectory = FileDirectory; }

    public int getFileTypeId() { return this.fileTypeId; }

    public void setFileTypeId(int FileTypeId) { this.fileTypeId = FileTypeId; }

    public int getAreaId() { return this.areaId; }

    public void setAreaId(int AreaId) { this.areaId = AreaId; }

    public static class List extends ArrayList<AreaFile>{

    }
}
