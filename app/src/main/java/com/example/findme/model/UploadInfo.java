package com.example.findme.model;

public class UploadInfo {
    public String userId;
    public String namaBarang;
    public String imageURL;
    public String waktuPenemuan;
    public String tanggalPenemuan;
    public String tempatPenemuan;
    public String nomorKontak;
    public String keterangan;
    public UploadInfo(){}

    public UploadInfo(String userId, String name, String url, String waktuPenemuan, String tanggalPenemuan, String tempatPenemuan, String nomorKontak, String keterangan) {
        this.userId = userId;
        this.namaBarang = name;
        this.imageURL = url;
        this.waktuPenemuan = waktuPenemuan;
        this.tanggalPenemuan = tanggalPenemuan;
        this.tempatPenemuan = tempatPenemuan;
        this.nomorKontak = nomorKontak;
        this.keterangan = keterangan;
    }

    public String getUserId() {
        return userId;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getWaktuPenemuan() {
        return waktuPenemuan;
    }

    public String getTanggalPenemuan() {
        return tanggalPenemuan;
    }

    public String getTempatPenemuan() {
        return tempatPenemuan;
    }

    public String getNomorKontak() {
        return nomorKontak;
    }

    public String getKeterangan() {
        return keterangan;
    }
}
