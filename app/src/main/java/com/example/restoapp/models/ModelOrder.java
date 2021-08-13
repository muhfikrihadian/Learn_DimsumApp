package com.example.restoapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelOrder {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("nama")
    @Expose
    private String nama;
    @SerializedName("harga")
    @Expose
    private Integer harga;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("foto")
    @Expose
    private String foto;

    public ModelOrder(String name, String nama, Integer harga, Integer qty, Integer total, String foto) {
        this.name = name;
        this.nama = nama;
        this.harga = harga;
        this.qty = qty;
        this.total = total;
        this.foto = foto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}
