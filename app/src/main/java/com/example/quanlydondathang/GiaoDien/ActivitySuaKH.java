package com.example.quanlydondathang.GiaoDien;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quanlydondathang.Database.DBDonDatHang;
import com.example.quanlydondathang.Model.KhachHang;
import com.example.quanlydondathang.R;

public class ActivitySuaKH extends AppCompatActivity {

    EditText edtMaKH, edtTenKH, edtDiaChi, edtSoDT;
    Button btnLuu, btnXoa;

    KhachHang khachHang = new KhachHang();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_kh);
        setControl();
        setEvent();
        layDuLieuKH();

        //Lấy tên cho trang details
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            String titleFormEditKH = ActivitySuaKH.this.getResources().getString(R.string.titleFormEditKH);
            ab.setTitle(titleFormEditKH + " " +  khachHang.getMaKH());
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setEvent() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suaKhachHangVaoMang();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmXoa();
            }
        });
    }

    private void suaKhachHangVaoMang() {
        DBDonDatHang myDB =  new DBDonDatHang(this);
        final String maKH = edtMaKH.getText().toString();
        final String tenKH = edtTenKH.getText().toString();
        final String diaChi = edtDiaChi.getText().toString();
        final String soDT = edtSoDT.getText().toString();

        if(TextUtils.isEmpty(maKH) || TextUtils.isEmpty(tenKH) || TextUtils.isEmpty(diaChi)|| TextUtils.isEmpty(soDT)){
            String toastNull = ActivitySuaKH.this.getResources().getString(R.string.alertNull);
            Toast.makeText(ActivitySuaKH.this, toastNull, Toast.LENGTH_LONG).show();
        }
        else{
            String toastSuccess = ActivitySuaKH.this.getResources().getString(R.string.alertSuccess);
            myDB.SuaKH(new KhachHang(khachHang.getSttKH(),maKH,tenKH,diaChi,soDT));
            Toast.makeText(ActivitySuaKH.this, toastSuccess, Toast.LENGTH_LONG).show();
        }
    }

    //Lấy dữ liệu đổ vào trang sửa khi click vào item
    void layDuLieuKH(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("maKH") &&
                getIntent().hasExtra("tenKH") &&
                getIntent().hasExtra("diaChi") && getIntent().hasExtra("soDT")){
            //Getting Data from Intent
            int id = -1;
            khachHang.setSttKH(getIntent().getIntExtra("id",id));
            khachHang.setMaKH(getIntent().getStringExtra("maKH"));
            khachHang.setTenKH(getIntent().getStringExtra("tenKH"));
            khachHang.setDiaChi(getIntent().getStringExtra("diaChi"));
            khachHang.setSoDT(getIntent().getStringExtra("soDT"));

            //Setting Intent Data
            edtMaKH.setText(khachHang.getMaKH());
            edtTenKH.setText(khachHang.getTenKH());
            edtDiaChi.setText(khachHang.getDiaChi());
            edtSoDT.setText(khachHang.getSoDT());
            Log.d("TranVan", khachHang.getMaKH()+" "+khachHang.getTenKH()+" "+khachHang.getDiaChi()+ " " +khachHang.getSoDT());
        } else {
            String toastMessage = ActivitySuaKH.this.getResources().getString(R.string.noData);
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
        }
    }

    void confirmXoa() {
        String toastTittle = ActivitySuaKH.this.getResources().getString(R.string.confirmDeleteTitle);
        String toastMessage = ActivitySuaKH.this.getResources().getString(R.string.confirmDeleteMessage);
        String toastYes = ActivitySuaKH.this.getResources().getString(R.string.confirmYes);
        String toastNo = ActivitySuaKH.this.getResources().getString(R.string.confirmNo);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(toastTittle + khachHang.getTenKH());
        builder.setMessage(toastMessage + khachHang.getTenKH());
        builder.setPositiveButton(toastYes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBDonDatHang myDB = new DBDonDatHang(ActivitySuaKH.this);
                myDB.XoaMotItemKH(khachHang);
                finish();
            }
        });
        builder.setNegativeButton(toastNo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.create().show();
    }

    private void setControl() {
        edtMaKH = findViewById(R.id.suaMaKH);
        edtTenKH = findViewById(R.id.suaTenKH);
        edtDiaChi = findViewById(R.id.suaDiaChi);
        edtSoDT = findViewById(R.id.suaSoDT);
        btnLuu = findViewById(R.id.save_button_kh);
        btnXoa = findViewById(R.id.delete_button_kh);
    }
    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    //Bấm nút quay về trang danh sách (ko dùng AndroidManifest được vì là từ Fragment về Activity)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}