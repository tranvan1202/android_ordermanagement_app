package com.example.quanlydondathang.Model;

public class SanPham {
    String maSP,tenSP,xuatXu, donGia;
    Integer sttSP;

    byte[] imageSP;

    public SanPham() {
    }

    public SanPham(String maSP, String tenSP, String xuatXu, String donGia, byte[] imageSP) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.xuatXu = xuatXu;
        this.donGia = donGia;
        this.imageSP = imageSP;
    }

    public SanPham(Integer sttSP, String maSP, String tenSP, String xuatXu, String donGia, byte[] imageSP) {
        this.sttSP = sttSP;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.xuatXu = xuatXu;
        this.donGia = donGia;
        this.imageSP = imageSP;
    }

    public SanPham(String maSP, byte[] imageSP) {
        this.maSP = maSP;
        this.imageSP = imageSP;
    }

    public Integer getSttSP() { return sttSP; }

    public void setSttSP(Integer sttSP) { this.sttSP = sttSP; }
    public String getMaSP() { return maSP; }

    public void setMaSP(String maSP) { this.maSP = maSP; }

    public String getTenSP() { return tenSP; }

    public void setTenSP(String tenSP) { this.tenSP = tenSP; }

    public String getXuatXu() { return xuatXu; }

    public void setXuatXu(String xuatXu) { this.xuatXu = xuatXu; }

    public String getDonGia() { return donGia; }

    public void setDonGia(String donGia) { this.donGia = donGia; }

    public byte[] getImageSP() { return imageSP; }

    public void setImageSP(byte[] imageSP) { this.imageSP = imageSP; }

}
