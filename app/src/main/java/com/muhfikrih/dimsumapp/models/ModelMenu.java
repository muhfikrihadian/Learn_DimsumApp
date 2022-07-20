package com.muhfikrih.dimsumapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelMenu{
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;
    @SerializedName("tipe")
    @Expose
    private String tipe;
    @SerializedName("harga")
    @Expose
    private Integer harga;
    @SerializedName("foto")
    @Expose
    private String foto;

    public ModelMenu(){

    }

    public ModelMenu(String id, String nama, String deskripsi, String tipe, Integer harga, String foto){
        this.id = id;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.tipe = tipe;
        this.harga = harga;
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
