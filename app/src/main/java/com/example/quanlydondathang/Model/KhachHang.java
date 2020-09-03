package com.example.quanlydondathang.Model;

public class KhachHang {
    String maKH,tenKH,diaChi,soDT;
    Integer sttKH;
    public KhachHang() {
    }

    public KhachHang(String maKH, String tenKH, String diaChi, String soDT) {
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.diaChi = diaChi;
        this.soDT = soDT;
    }

    public KhachHang(Integer sttKH, String maKH, String tenKH, String diaChi, String soDT) {
        this.sttKH = sttKH;
        this.maKH = maKH;
        this.tenKH = tenKH;
        this.diaChi = diaChi;
        this.soDT = soDT;
    }

    public Integer getSttKH() {
        return sttKH;
    }

    public void setSttKH(Integer sttKH) {
        this.sttKH = sttKH;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    @Override
    public String toString() {
        return "KhachHang{" +
                "sttKH='" + sttKH + '\'' +
                ", maKH='" + maKH + '\'' +
                ", tenKH='" + tenKH + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", soDT='" + soDT + '\'' +
                '}';
    }
}
