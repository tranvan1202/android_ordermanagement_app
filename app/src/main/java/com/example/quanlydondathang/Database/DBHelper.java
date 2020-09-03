package com.example.quanlydondathang.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper( Context context) {
        super(context, "QLDDH.db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlKH = "CREATE TABLE " + "QLKH" +
                " (" + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "makh" + " TEXT, " +
                "tenkh" + " TEXT, " +
                "diachi" + " TEXT, " +
                "sodt" + " INTEGER);";

        String sqlSP = "CREATE TABLE " + "QLSP" +
                " (" + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "masp" + " TEXT, " +
                "tensp" + " TEXT, " +
                "xuatxu" + " TEXT, " +
                "dongia" + " INTEGER, " +
                "imagesp" + " BLOB NOT NULL);";

        String sqlDH = "CREATE TABLE " + "QLDH" +
                " (" + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "madh" + " TEXT, " +
                "makh" + " TEXT, " +
                "masp" + " TEXT, " +
                "soluongdat" + " INTEGER, " +
                "ngaytaodh" + " TEXT," +
                "tinhtrangdh" + " TEXT);";

        String sqlTinhTrangDH = "CREATE TABLE " + "QLTINHTRANGDH" +
                " (" + "_id" + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tinhtrangdh" + " TEXT);";

        String sqlThemTinhTrangDH1 = "INSERT INTO " + "QLTINHTRANGDH(tinhtrangdh)" + "VALUES" + "('Đang giao')";
        String sqlThemTinhTrangDH2 = "INSERT INTO " + "QLTINHTRANGDH(tinhtrangdh)" + "VALUES" + "('Đã hoàn thành')";
        String sqlThemTinhTrangDH3 = "INSERT INTO " + "QLTINHTRANGDH(tinhtrangdh)" + "VALUES" + "('Đã hủy')";

        db.execSQL(sqlKH);
        db.execSQL(sqlSP);
        db.execSQL(sqlDH);
        db.execSQL(sqlTinhTrangDH);
        db.execSQL(sqlThemTinhTrangDH1);
        db.execSQL(sqlThemTinhTrangDH2);
        db.execSQL(sqlThemTinhTrangDH3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "QLKH");
        db.execSQL("DROP TABLE IF EXISTS " + "QLSP");
        db.execSQL("DROP TABLE IF EXISTS " + "QLDH");
        onCreate(db);
    }
}
