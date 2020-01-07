package com.example.findme.model;

import android.widget.ImageView;

public class MainPageModel {
    String imageUrl,keterangan,namaBarang,nomorKontak,tanggalPenemuan,tempatPenemuan,userId,waktuPenemuan;


    public MainPageModel(String imageUrl, String keterangan, String namaBarang, String nomorKontak, String tanggalPenemuan, String tempatPenemuan, String userId, String waktuPenemuan) {
        this.imageUrl = imageUrl;
        this.keterangan = keterangan;
        this.namaBarang = namaBarang;
        this.nomorKontak = nomorKontak;
        this.tanggalPenemuan = tanggalPenemuan;
        this.tempatPenemuan = tempatPenemuan;
        this.userId = userId;
        this.waktuPenemuan = waktuPenemuan;
    }

    public MainPageModel() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getNomorKontak() {
        return nomorKontak;
    }

    public void setNomorKontak(String nomorKontak) {
        this.nomorKontak = nomorKontak;
    }

    public String getTanggalPenemuan() {
        return tanggalPenemuan;
    }

    public void setTanggalPenemuan(String tanggalPenemuan) {
        this.tanggalPenemuan = tanggalPenemuan;
    }

    public String getTempatPenemuan() {
        return tempatPenemuan;
    }

    public void setTempatPenemuan(String tempatPenemuan) {
        this.tempatPenemuan = tempatPenemuan;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWaktuPenemuan() {
        return waktuPenemuan;
    }

    public void setWaktuPenemuan(String waktuPenemuan) {
        this.waktuPenemuan = waktuPenemuan;
    }
}
