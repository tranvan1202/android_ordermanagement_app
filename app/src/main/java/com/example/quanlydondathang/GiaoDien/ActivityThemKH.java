package com.example.quanlydondathang.GiaoDien;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.quanlydondathang.Database.DBDonDatHang;
import com.example.quanlydondathang.Model.KhachHang;
import com.example.quanlydondathang.R;

public class ActivityThemKH extends AppCompatActivity {
    EditText edtMaKH, edtTenKH, edtDiaChi, edtSoDT;
    Button btnThem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_kh);
        setControl();
        setEvent();
        //Đổi tên actionbar theo ngôn ngữ đã đổi:
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.titleFormAddKH));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setEvent() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addKhachHangVaoMang();
            }
        });
    }

    private void addKhachHangVaoMang() {
        DBDonDatHang myDB = new DBDonDatHang(this);
        try {
            final String maKH = edtMaKH.getText().toString();
            final String tenKH = edtTenKH.getText().toString();
            final String diaChi = edtDiaChi.getText().toString();
            final String soDT = edtSoDT.getText().toString();

            if(TextUtils.isEmpty(maKH) || TextUtils.isEmpty(tenKH) || TextUtils.isEmpty(diaChi)|| TextUtils.isEmpty(soDT)){
                String toastNull = ActivityThemKH.this.getResources().getString(R.string.alertNull);
                Toast.makeText(ActivityThemKH.this, toastNull, Toast.LENGTH_LONG).show();
            }
            else{
                String toastSuccess = ActivityThemKH.this.getResources().getString(R.string.alertSuccess);
                KhachHang khachHang = new KhachHang(maKH,tenKH,diaChi,soDT);
                Toast.makeText(ActivityThemKH.this, toastSuccess, Toast.LENGTH_LONG).show();
                myDB.ThemKH(khachHang);
            }
            edtMaKH.setText("");
            edtTenKH.setText("");
            edtDiaChi.setText("");
            edtSoDT.setText("");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setControl() {
        edtMaKH = findViewById(R.id.nhapMaKH);
        edtTenKH = findViewById(R.id.nhapTenKH);
        edtDiaChi = findViewById(R.id.nhapDiaChi);
        edtSoDT = findViewById(R.id.nhapSoDT);
        btnThem = findViewById(R.id.add_button_kh);
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