package com.marco.gkp.model;

public class CariJemaatModel {

    private String id;
    private String name;
    private String no_regis;
    private String jenis_kelamin;
    private String alamat;
    private String status;

    public CariJemaatModel(String id, String name, String no_regis, String jenis_kelamin, String alamat, String status) {
        this.id = id;
        this.name = name;
        this.no_regis = no_regis;
        this.jenis_kelamin = jenis_kelamin;
        this.alamat = alamat;
        this.status = status;
    }

    public CariJemaatModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo_regis() {
        return no_regis;
    }

    public void setNo_regis(String no_regis) {
        this.no_regis = no_regis;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}