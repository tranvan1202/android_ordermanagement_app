package com.example.quanlydondathang.Model;

public class DonHang {
    Integer sttDH, soLuongDat;
    String maDH;
    String maKH;
    String maSP;
    String ngayTaoDH;
    String tinhTrangDH;

    public DonHang() {
    }

    public DonHang(String maDH, String maKH, String maSP, Integer soLuongDat, String ngayTaoDH, String tinhTrangDH) {
        this.maDH = maDH;
        this.maKH = maKH;
        this.maSP = maSP;
        this.soLuongDat = soLuongDat;
        this.ngayTaoDH = ngayTaoDH;
        this.tinhTrangDH = tinhTrangDH;
    }

    public DonHang(Integer sttDH, String maDH, String maKH, String maSP, Integer soLuongDat, String ngayTaoDH, String tinhTrangDH) {
        this.sttDH = sttDH;
        this.maDH = maDH;
        this.maKH = maKH;
        this.maSP = maSP;
        this.soLuongDat = soLuongDat;
        this.ngayTaoDH = ngayTaoDH;
        this.tinhTrangDH = tinhTrangDH;
    }

    public Integer getSttDH() { return sttDH; }

    public void setSttDH(Integer sttDH) { this.sttDH = sttDH; }

    public String getMaDH() { return maDH; }

    public void setMaDH(String maDH) { this.maDH = maDH; }

    public String getMaKH() { return maKH; }

    public void setMaKH(String maKH) { this.maKH = maKH; }

    public String getMaSP() { return maSP; }

    public void setMaSP(String maSP) { this.maSP = maSP; }

    public Integer getSoLuongDat() { return soLuongDat; }

    public void setSoLuongDat(Integer soLuongDat) { this.soLuongDat = soLuongDat; }

    public String getNgayTaoDH() { return ngayTaoDH; }

    public void setNgayTaoDH(String ngayTaoDH) { this.ngayTaoDH = ngayTaoDH; }

    public String getTinhTrangDH() { return tinhTrangDH; }

    public void setTinhTrangDH(String tinhTrangDH) { this.tinhTrangDH = tinhTrangDH; }
}
