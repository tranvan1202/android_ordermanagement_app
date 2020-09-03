package com.example.quanlydondathang.GiaoDien;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.quanlydondathang.Adapter.SpinnerAdapterSP;
import com.example.quanlydondathang.Database.DBDonDatHang;
import com.example.quanlydondathang.Model.DonHang;
import com.example.quanlydondathang.Model.SanPham;
import com.example.quanlydondathang.R;

import java.util.ArrayList;

public class ActivityThemDH extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText edtMaDH, edtSoLuongSP, edtNgayTaoDH;
    Spinner spinnerMaKH, spinnerMaSP, spinnerTinhTrangDH;
    Button btnThemDH;
    public DBDonDatHang myDB;
    private String maKH = "";
    private String clickedMaSP = "";
    private String tinhTrangDH = "";
    private SpinnerAdapterSP spinnerAdapterSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dh);
        setControl();
        setEvent();
        //Đổi tên actionbar theo ngôn ngữ đã đổi:
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.titleFormAddDH));
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    private void setEvent() {
        spinnerMaKH.setOnItemSelectedListener(this);
        spinnerMaSP.setOnItemSelectedListener(this);
        spinnerTinhTrangDH.setOnItemSelectedListener(this);
        loadSpinnerData();
        btnThemDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDonHangVaoMang();
            }
        });
    }

    private void loadSpinnerData() {
        myDB = new DBDonDatHang(getApplicationContext());
        //Set giá trị cho spinner Mã KH
        ArrayList<String> listMaKH = myDB.getAllKhachHangs();
        ArrayAdapter<String> adapterMaKH = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listMaKH);
        adapterMaKH.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaKH.setAdapter(adapterMaKH);

        //Set giá trị cho spinner Mã SP
        ArrayList<SanPham> listMaSP = myDB.listSanPhams();
        spinnerAdapterSP = new SpinnerAdapterSP(this, listMaSP);
        spinnerMaSP.setAdapter(spinnerAdapterSP);

        //Set giá trị cho spinner tình trạng đơn hàng
        ArrayList<String> listTinhTrangDH = myDB.getAllsTinhTrangDH();
        ArrayAdapter<String> adapterTinhTrangDH = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,listTinhTrangDH);
        adapterTinhTrangDH.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTinhTrangDH.setAdapter(adapterTinhTrangDH);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.nhapSpinnerMaKH) {
            maKH = parent.getSelectedItem().toString();
            Toast.makeText(parent.getContext(), "You selected: " + maKH,
                    Toast.LENGTH_LONG).show();
        } else if (spinner.getId() == R.id.nhapSpinnerMaSP) {
            SanPham maSP = (SanPham) parent.getItemAtPosition(position);
            clickedMaSP = maSP.getMaSP();
            Toast.makeText(parent.getContext(), "You selected: " + clickedMaSP,
                    Toast.LENGTH_LONG).show();
        } else if (spinner.getId() == R.id.nhapSpinnerTinhTrangDH) {
            tinhTrangDH = parent.getSelectedItem().toString();
            Toast.makeText(parent.getContext(), "You selected: " + tinhTrangDH,
                    Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    private void addDonHangVaoMang() {
        myDB = new DBDonDatHang(this);
        try {
            final String maDH = edtMaDH.getText().toString();
            final String ngayTaoDH = edtNgayTaoDH.getText().toString();
            final Integer soLuongSP = Integer.parseInt(edtSoLuongSP.getText().toString());

            if(TextUtils.isEmpty(maDH) || TextUtils.isEmpty(maKH) || TextUtils.isEmpty(clickedMaSP) || TextUtils.isEmpty(ngayTaoDH)){
                String toastNull = ActivityThemDH.this.getResources().getString(R.string.alertNull);
                Toast.makeText(ActivityThemDH.this, toastNull, Toast.LENGTH_LONG).show();
            }
            else{
                String toastSuccess = ActivityThemDH.this.getResources().getString(R.string.alertSuccess);
                DonHang donHang = new DonHang(maDH,maKH, clickedMaSP, soLuongSP, ngayTaoDH, tinhTrangDH);
                Toast.makeText(ActivityThemDH.this, toastSuccess, Toast.LENGTH_LONG).show();
                myDB.ThemDH(donHang);
            }
            edtMaDH.setText("");
            edtNgayTaoDH.setText("");
            edtSoLuongSP.setText("");
        }
        catch (NumberFormatException nfe) {
            String toastNull = ActivityThemDH.this.getResources().getString(R.string.alertNull);
            Toast.makeText(ActivityThemDH.this, toastNull, Toast.LENGTH_LONG).show();
        }
    }
    private void setControl() {
        edtMaDH = findViewById(R.id.nhapMaDH);
        edtSoLuongSP = findViewById(R.id.nhapSoLuongSP);
        edtNgayTaoDH = findViewById(R.id.nhapNgayTaoDH);
        spinnerMaKH = findViewById(R.id.nhapSpinnerMaKH);
        spinnerMaSP = findViewById(R.id.nhapSpinnerMaSP);
        btnThemDH = findViewById(R.id.add_button_dh);
        spinnerTinhTrangDH = findViewById(R.id.nhapSpinnerTinhTrangDH);
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