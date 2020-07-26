package com.marco.gkp.model;

public class EwartaModel {
    private String id, file, datetime, ketfile;

    public EwartaModel() {
    }

    public EwartaModel(String id, String file, String datetime, String ketfile) {
        this.id = id;
        this.file = file;
        this.datetime = datetime;
        this.ketfile = ketfile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getKetfile() {
        return ketfile;
    }

    public void setKetfile(String ketfile) {
        this.ketfile = ketfile;
    }
}