package com.example.quanlydondathang.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.quanlydondathang.Model.DonHang;
import com.example.quanlydondathang.Model.KhachHang;
import com.example.quanlydondathang.Model.SanPham;

import java.util.ArrayList;

public class DBDonDatHang {
    DBHelper dbHelper;
    Context context;
    public DBDonDatHang(Context context) {
        dbHelper= new DBHelper(context);
        this.context = context;
    }

    //--------------------------------------Table Khách hàng---------------------------------------------------
    public ArrayList<KhachHang> listKhachHangs(){
        String sql = "select * from " + "QLKH";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<KhachHang> khachHangs = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                Integer _id = (cursor.getInt(0));
                String maKH = cursor.getString(1);
                String tenKH = cursor.getString(2);
                String diaChi = cursor.getString(3);
                String soDT = cursor.getString(4);
                khachHangs.add(new KhachHang(_id,maKH,tenKH,diaChi,soDT));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return khachHangs;
    }

    public void ThemKH(KhachHang khachHang){
        ContentValues values = new ContentValues();
        values.put("makh", khachHang.getMaKH());
        values.put("tenkh", khachHang.getTenKH());
        values.put("diachi", khachHang.getDiaChi());
        values.put("sodt", khachHang.getSoDT());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert("QLKH", null, values);
    }

    public void SuaKH(KhachHang khachHang){
        ContentValues values = new ContentValues();
        values.put("makh", khachHang.getMaKH());
        values.put("tenkh", khachHang.getTenKH());
        values.put("diachi", khachHang.getDiaChi());
        values.put("sodt", khachHang.getSoDT());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update("QLKH", values, "_id"	+ "	= ?", new String[]{String.valueOf(khachHang.getSttKH())});
    }

    public void XoaMotItemKH(KhachHang khachHang){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = db.delete("QLKH", "_id=?", new String[]{String.valueOf(khachHang.getSttKH())});
        if(result == -1){
            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Đã xóa.", Toast.LENGTH_SHORT).show();
        }
    }

    public void XoaTatCaKH(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + "QLKH");
        //RESET STT ID TỰ TĂNG
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + "QLKH" + "'");
    }

    public long XoaItemTheoIDKH(int id)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete("QLKH", "_id=?", new String[]{String.valueOf(id)});
    }

    public ArrayList<String> getAllKhachHangs(){
        ArrayList<String> listMaSoKH = new ArrayList<String>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.beginTransaction();

        try {
            String selectQuery = "SELECT * FROM " + "QLKH";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String khID = cursor.getString(cursor.getColumnIndex("makh"));
                    listMaSoKH.add(khID);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e)  {
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return listMaSoKH;
    }

    //--------------------------------------Table Sản Phẩm---------------------------------------------------
    public ArrayList<SanPham> listSanPhams(){
        String sql = "select * from " + "QLSP";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<SanPham> sanPhams = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                Integer _id = (cursor.getInt(0));
                String maSP = cursor.getString(1);
                String tenSP = cursor.getString(2);
                String xuatXu = cursor.getString(3);
                String donGia = cursor.getString(4);
                byte[] anhSanPham = cursor.getBlob(5);
                sanPhams.add(new SanPham(_id,maSP,tenSP,xuatXu,donGia, anhSanPham));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return sanPhams;
    }

    public void ThemSP(SanPham sanPham){
        ContentValues values = new ContentValues();
        values.put("masp", sanPham.getMaSP());
        values.put("tensp", sanPham.getTenSP());
        values.put("xuatxu", sanPham.getXuatXu());
        values.put("dongia", sanPham.getDonGia());
        values.put("imagesp", sanPham.getImageSP());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert("QLSP", null, values);
    }

    public void SuaSP(SanPham sanPham){
        ContentValues values = new ContentValues();
        values.put("masp", sanPham.getMaSP());
        values.put("tensp", sanPham.getTenSP());
        values.put("xuatxu", sanPham.getXuatXu());
        values.put("dongia", sanPham.getDonGia());
        values.put("imagesp", sanPham.getImageSP());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update("QLSP", values, "_id"	+ "	= ?", new String[]{String.valueOf(sanPham.getSttSP())});
    }

    public void XoaMotItemSP(SanPham sanPham){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = db.delete("QLSP", "_id=?", new String[]{String.valueOf(sanPham.getSttSP())});
        if(result == -1){
            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Đã xóa.", Toast.LENGTH_SHORT).show();
        }
    }

    public void XoaTatCaSP(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + "QLSP");
        //RESET STT ID TỰ TĂNG
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + "QLSP" + "'");
    }

    public ArrayList<String> getAllSanPhams(){
        ArrayList<String> listMaSoSP = new ArrayList<String>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.beginTransaction();

        try {
            String selectQuery = "SELECT * FROM " + "QLSP";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String spID = cursor.getString(cursor.getColumnIndex("masp"));
                    listMaSoSP.add(spID);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e)  {
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return listMaSoSP;
    }

    //--------------------------------------Table Đơn hàng---------------------------------------------------
    public ArrayList<DonHang> listDonHangs(){
        String sql = "select * from " + "QLDH";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<DonHang> donHangs = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.moveToFirst()){
            do{
                Integer _id = (cursor.getInt(0));
                String maDH = cursor.getString(1);
                String maKHDH = cursor.getString(2);
                String maSPDH = cursor.getString(3);
                Integer soLuongDat = cursor.getInt(4);
                String ngayTaoDH = cursor.getString(5);
                String tinhTrangDH = cursor.getString(6);
                donHangs.add(new DonHang(_id,maDH,maKHDH,maSPDH,soLuongDat, ngayTaoDH, tinhTrangDH));
            }while (cursor.moveToNext());
        }
        cursor.close();
        return donHangs;
    }

    public void ThemDH(DonHang donHang){
        ContentValues values = new ContentValues();
        values.put("madh", donHang.getMaDH());
        values.put("makh", donHang.getMaKH());
        values.put("masp", donHang.getMaSP());
        values.put("soluongdat", donHang.getSoLuongDat());
        values.put("ngaytaodh", donHang.getNgayTaoDH());
        values.put("tinhtrangdh", donHang.getTinhTrangDH());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.insert("QLDH", null, values);
    }

    public void SuaDH(DonHang donHang){
        ContentValues values = new ContentValues();
        values.put("madh", donHang.getMaDH());
        values.put("makh", donHang.getMaKH());
        values.put("masp", donHang.getMaSP());
        values.put("soluongdat", donHang.getSoLuongDat());
        values.put("ngaytaodh", donHang.getNgayTaoDH());
        values.put("tinhtrangdh", donHang.getTinhTrangDH());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update("QLDH", values, "_id"	+ "	= ?", new String[]{String.valueOf(donHang.getSttDH())});
    }

    public void XoaMotItemDH(DonHang donHang){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = db.delete("QLDH", "_id=?", new String[]{String.valueOf(donHang.getSttDH())});
        if(result == -1){
            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Đã xóa.", Toast.LENGTH_SHORT).show();
        }
    }

    public void XoaTatCaDH(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + "QLDH");
        //RESET STT ID TỰ TĂNG
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" + "QLDH" + "'");
    }

    public ArrayList<String> getAllsTinhTrangDH(){
        ArrayList<String> listTinhTrangDH = new ArrayList<String>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.beginTransaction();

        try {
            String selectQuery = "SELECT * FROM " + "QLTINHTRANGDH";
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String dhTinhTrang = cursor.getString(cursor.getColumnIndex("tinhtrangdh"));
                    listTinhTrangDH.add(dhTinhTrang);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e)  {
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }
        return listTinhTrangDH;
    }

    public int getSLDonHangHoanThanh() {
        String countQuery = "SELECT * " + "FROM " + "QLDH " + "WHERE " + "tinhtrangdh = " + "'Đã hoàn thành'";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int getSLDonHangDangGiao() {
        String countQuery = "SELECT * " + "FROM " + "QLDH " + "WHERE " + "tinhtrangdh = " + "'Đang giao'";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int getSLDonHangDaHuy() {
        String countQuery = "SELECT * " + "FROM " + "QLDH " + "WHERE " + "tinhtrangdh = " + "'Đã hủy'";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public int getSLDonHangTong() {
        String countQuery = "SELECT * " + "FROM " + "QLDH ";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }


}
