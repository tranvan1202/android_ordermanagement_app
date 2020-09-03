package com.example.quanlydondathang.GiaoDien;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quanlydondathang.Database.DBDonDatHang;
import com.example.quanlydondathang.Model.SanPham;
import com.example.quanlydondathang.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class ActivitySuaSP extends AppCompatActivity {

    EditText edtMaSP, edtTenSP, edtXuatXu, edtDonGia;
    Button btnLuu, btnXoa, btnChooseImg;
    ImageView imgPreviewSP;

    final int REQUEST_CODE_GALLERY = 999;

    SanPham sanPham = new SanPham();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_sp);
        setControl();
        setEvent();
        layDuLieuSP();

        //Lấy tên cho trang details
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            String titleFormEditSP = ActivitySuaSP.this.getResources().getString(R.string.titleFormEditSP);
            ab.setTitle(titleFormEditSP + " " +  sanPham.getMaSP());
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setEvent() {
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suaSanPhamVaoMang();
            }
        });

        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmXoa();
            }
        });

        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        ActivitySuaSP.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });
    }

    private void suaSanPhamVaoMang() {
        DBDonDatHang myDB =  new DBDonDatHang(this);
        final String maSP = edtMaSP.getText().toString();
        final String tenSP = edtTenSP.getText().toString();
        final String xuatXu = edtXuatXu.getText().toString();
        final String donGia = edtDonGia.getText().toString();
        final byte[] anhSP = imageViewToByte(imgPreviewSP);

        if(TextUtils.isEmpty(maSP) || TextUtils.isEmpty(tenSP) || TextUtils.isEmpty(xuatXu)|| TextUtils.isEmpty(donGia)){
            String toastNull = ActivitySuaSP.this.getResources().getString(R.string.alertNull);
            Toast.makeText(ActivitySuaSP.this, toastNull, Toast.LENGTH_LONG).show();
        }
        else{
            String toastSuccess = ActivitySuaSP.this.getResources().getString(R.string.alertSuccess);
            myDB.SuaSP(new SanPham(sanPham.getSttSP(),maSP,tenSP,xuatXu,donGia, anhSP));
            Toast.makeText(ActivitySuaSP.this, toastSuccess, Toast.LENGTH_LONG).show();
        }
    }


    //Lấy dữ liệu đổ vào trang sửa khi click vào item
    void layDuLieuSP(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("maSP") &&
                getIntent().hasExtra("tenSP") &&
                getIntent().hasExtra("xuatXu") && getIntent().hasExtra("donGia") && getIntent().hasExtra("anhSP")){
            //Getting Data from Intent
            int id = -1;
            sanPham.setSttSP(getIntent().getIntExtra("id",id));
            sanPham.setMaSP(getIntent().getStringExtra("maSP"));
            sanPham.setTenSP(getIntent().getStringExtra("tenSP"));
            sanPham.setXuatXu(getIntent().getStringExtra("xuatXu"));
            sanPham.setDonGia(getIntent().getStringExtra("donGia"));
            sanPham.setImageSP(getIntent().getByteArrayExtra("anhSP"));

            //Setting Intent Data
            edtMaSP.setText(sanPham.getMaSP());
            edtTenSP.setText(sanPham.getTenSP());
            edtXuatXu.setText(sanPham.getXuatXu());
            edtDonGia.setText(sanPham.getDonGia());

            byte[] anhSP = sanPham.getImageSP();
            Bitmap myImage = BitmapFactory.decodeByteArray(anhSP, 0, anhSP.length);
            imgPreviewSP.setImageBitmap(myImage);
        } else {
            String toastMessage = ActivitySuaSP.this.getResources().getString(R.string.noData);
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
        }
    }

    void confirmXoa() {
        String toastTittle = ActivitySuaSP.this.getResources().getString(R.string.confirmDeleteTitle);
        String toastMessage = ActivitySuaSP.this.getResources().getString(R.string.confirmDeleteMessage);
        String toastYes = ActivitySuaSP.this.getResources().getString(R.string.confirmYes);
        String toastNo = ActivitySuaSP.this.getResources().getString(R.string.confirmNo);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(toastTittle + sanPham.getTenSP());
        builder.setMessage(toastMessage + sanPham.getTenSP());
        builder.setPositiveButton(toastYes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBDonDatHang myDB = new DBDonDatHang(ActivitySuaSP.this);
                myDB.XoaMotItemSP(sanPham);
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

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgPreviewSP.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setControl() {
        edtMaSP = findViewById(R.id.suaMaSP);
        edtTenSP = findViewById(R.id.suaTenSP);
        edtXuatXu = findViewById(R.id.suaXuatXu);
        edtDonGia = findViewById(R.id.suaDonGia);
        btnLuu = findViewById(R.id.save_button_sp);
        btnXoa = findViewById(R.id.delete_button_sp);

        imgPreviewSP = findViewById(R.id.imgPreviewEditSP);
        btnChooseImg = findViewById(R.id.button_choose_img_editSP);
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
