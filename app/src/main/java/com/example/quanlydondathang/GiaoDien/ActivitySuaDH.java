package com.example.quanlydondathang.GiaoDien;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

public class ActivitySuaDH extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText edtMaDH, edtSoLuongSP, edtNgayTaoDH;
    Spinner spinnerMaKH, spinnerMaSP, spinnerTinhTrangDH;
    Button btnLuuDH, btnXoaDH;
    DBDonDatHang myDB;
    private String maKH = "";
    private String clickedMaSP = "";
    private String tinhTrangDH = "";
    DonHang donHang = new DonHang();
    private ArrayList<String> arrKH = new ArrayList<>();
    private ArrayList<String> arrSP = new ArrayList<>();
    private ArrayList<String> arrTinhTrangDH = new ArrayList<>();
    private SpinnerAdapterSP spinnerAdapterSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_dh);
        setControl();
        setEvent();
        layDuLieuDH();

        //Lấy tên cho trang details
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            String titleFormEditDH = ActivitySuaDH.this.getResources().getString(R.string.titleFormEditDH);
            ab.setTitle(titleFormEditDH + " " +  donHang.getMaDH());
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setEvent() {
        spinnerMaKH.setOnItemSelectedListener(this);
        spinnerMaSP.setOnItemSelectedListener(this);
        spinnerTinhTrangDH.setOnItemSelectedListener(this);
        loadSpinnerData();
        btnLuuDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suaDonHangVaoMang();
            }
        });

        btnXoaDH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmXoa();
            }
        });
    }
    private void loadSpinnerData() {
        myDB = new DBDonDatHang(getApplicationContext());
        //Set giá trị cho spinner Mã KH
        arrKH = myDB.getAllKhachHangs();
        ArrayAdapter<String> adapterMaKH = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arrKH);
        adapterMaKH.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMaKH.setAdapter(adapterMaKH);

        //Set giá trị cho spinner Mã SP
        ArrayList<SanPham> listMaSP = myDB.listSanPhams();
        spinnerAdapterSP = new SpinnerAdapterSP(this, listMaSP);
        spinnerMaSP.setAdapter(spinnerAdapterSP);

        //Set giá trị cho spinner tình trạng đơn hàng
        arrTinhTrangDH = myDB.getAllsTinhTrangDH();
        ArrayAdapter<String> adapterTinhTrangDH = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,arrTinhTrangDH);
        adapterTinhTrangDH.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTinhTrangDH.setAdapter(adapterTinhTrangDH);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinner = (Spinner) parent;
        if(spinner.getId() == R.id.suaSpinnerMaKH) {
            maKH = parent.getItemAtPosition(position).toString();
//            Toast.makeText(parent.getContext(), "You selected: " + maKH, Toast.LENGTH_LONG).show();
        } else if (spinner.getId() == R.id.suaSpinnerMaSP) {
            SanPham maSP = (SanPham) parent.getItemAtPosition(position);
            clickedMaSP = maSP.getMaSP();
//            Toast.makeText(parent.getContext(), "You selected: " + maSP, Toast.LENGTH_LONG).show();
        } else if (spinner.getId() == R.id.suaSpinnerTinhTrangDH) {
            tinhTrangDH = parent.getSelectedItem().toString();
            Toast.makeText(parent.getContext(), "You selected: " + tinhTrangDH,
                    Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    private void suaDonHangVaoMang() {
        myDB =  new DBDonDatHang(this);
        try {
            final String maDH = edtMaDH.getText().toString();
            final Integer soLuongSP = Integer.parseInt(edtSoLuongSP.getText().toString());
            final String ngayTaoDH = edtNgayTaoDH.getText().toString();

            if(TextUtils.isEmpty(maDH) || TextUtils.isEmpty(maKH) || TextUtils.isEmpty(clickedMaSP)|| TextUtils.isEmpty(ngayTaoDH)){
                String toastNull = ActivitySuaDH.this.getResources().getString(R.string.alertNull);
                Toast.makeText(ActivitySuaDH.this, toastNull, Toast.LENGTH_LONG).show();
            }
            else{
                String toastSuccess = ActivitySuaDH.this.getResources().getString(R.string.alertSuccess);
                myDB.SuaDH(new DonHang(donHang.getSttDH(),maDH,maKH,clickedMaSP,soLuongSP,ngayTaoDH, tinhTrangDH));
                Toast.makeText(ActivitySuaDH.this, toastSuccess, Toast.LENGTH_LONG).show();
            }

        }catch (NumberFormatException nfe) {
            String toastNull = ActivitySuaDH.this.getResources().getString(R.string.alertNull);
            Toast.makeText(ActivitySuaDH.this, toastNull, Toast.LENGTH_LONG).show();
        }
    }

    //Lấy dữ liệu đổ vào trang sửa khi click vào item
    void layDuLieuDH(){
        //Gọi array từ DB để lấy ID của Khách hàng và Sản Phẩm
        arrKH = myDB.getAllKhachHangs();
        arrSP = myDB.getAllSanPhams();
        arrTinhTrangDH = myDB.getAllsTinhTrangDH();

        if(getIntent().hasExtra("id") && getIntent().hasExtra("maDH")
                && getIntent().hasExtra("maKH") && getIntent().hasExtra("maSP")
                && getIntent().hasExtra("soLuongSPDat") && getIntent().hasExtra("ngayTaoDH")){
            //Getting Data from Intent
            int id = -1;
            int soLuongSPDat = -1;
            donHang.setSttDH(getIntent().getIntExtra("id",id));
            donHang.setMaDH(getIntent().getStringExtra("maDH"));
            donHang.setMaKH(getIntent().getStringExtra("maKH"));
            donHang.setMaSP(getIntent().getStringExtra("maSP"));
            donHang.setSoLuongDat(getIntent().getIntExtra("soLuongSPDat",soLuongSPDat));
            donHang.setNgayTaoDH(getIntent().getStringExtra("ngayTaoDH"));
            donHang.setTinhTrangDH(getIntent().getStringExtra("tinhTrangDH"));
            //Setting Intent Data
            edtMaDH.setText(donHang.getMaDH());
            spinnerMaKH.setSelection(arrKH.indexOf(donHang.getMaKH()));
            spinnerMaSP.setSelection(arrSP.indexOf(donHang.getMaSP()));
            edtSoLuongSP.setText(String.valueOf(donHang.getSoLuongDat()));
            edtNgayTaoDH.setText(donHang.getNgayTaoDH());
            spinnerTinhTrangDH.setSelection(arrTinhTrangDH.indexOf(donHang.getTinhTrangDH()));
        } else {
            String toastMessage = ActivitySuaDH.this.getResources().getString(R.string.noData);
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
        }
    }

    //Method nút xóa
    void confirmXoa() {
        String toastTittle = ActivitySuaDH.this.getResources().getString(R.string.confirmDeleteTitle);
        String toastMessage = ActivitySuaDH.this.getResources().getString(R.string.confirmDeleteMessage);
        String toastYes = ActivitySuaDH.this.getResources().getString(R.string.confirmYes);
        String toastNo = ActivitySuaDH.this.getResources().getString(R.string.confirmNo);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(toastTittle + donHang.getMaDH());
        builder.setMessage(toastMessage + donHang.getMaDH());
        builder.setPositiveButton(toastYes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBDonDatHang myDB = new DBDonDatHang(ActivitySuaDH.this);
                myDB.XoaMotItemDH(donHang);
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
        edtMaDH = findViewById(R.id.suaMaDH);
        spinnerMaKH = findViewById(R.id.suaSpinnerMaKH);
        spinnerMaSP = findViewById(R.id.suaSpinnerMaSP);
        spinnerTinhTrangDH = findViewById(R.id.suaSpinnerTinhTrangDH);
        edtSoLuongSP = findViewById(R.id.suaSoLuongSP);
        edtNgayTaoDH = findViewById(R.id.suaNgayTaoDH);
        btnLuuDH = findViewById(R.id.save_button_dh);
        btnXoaDH = findViewById(R.id.delete_button_dh);
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