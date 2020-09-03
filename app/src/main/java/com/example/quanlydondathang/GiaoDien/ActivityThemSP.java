package com.example.quanlydondathang.GiaoDien;

import android.Manifest;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class ActivityThemSP extends AppCompatActivity  {
    EditText edtMaSP, edtTenSP, edtXuatXu, edtDonGia;
    Button btnThem, btnChooseImg;
    ImageView imgPreviewSP;

    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sp);
        setControl();
        setEvent();
        //Đổi tên actionbar theo ngôn ngữ đã đổi:
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.titleFormAddSP));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void setEvent() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSanPhamVaoMang();
            }
        });

        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(
                        ActivityThemSP.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });
    }

    private void addSanPhamVaoMang() {
        DBDonDatHang myDB = new DBDonDatHang(this);
        try {
            final String maSP = edtMaSP.getText().toString();
            final String tenSP = edtTenSP.getText().toString();
            final String xuatXu = edtXuatXu.getText().toString();
            final String donGia = edtDonGia.getText().toString();
            final byte[] anhSP = imageViewToByte(imgPreviewSP);
//            imgPreviewSP.setImageResource(R.mipmap.ic_launcher);

            if(TextUtils.isEmpty(maSP) || TextUtils.isEmpty(tenSP) || TextUtils.isEmpty(xuatXu)|| TextUtils.isEmpty(donGia)){
                String toastNull = ActivityThemSP.this.getResources().getString(R.string.alertNull);
                Toast.makeText(ActivityThemSP.this, toastNull, Toast.LENGTH_LONG).show();
            }
            else{
                String toastSuccess = ActivityThemSP.this.getResources().getString(R.string.alertSuccess);
                SanPham sanPham = new SanPham(maSP,tenSP,xuatXu,donGia, anhSP);
                Toast.makeText(ActivityThemSP.this, toastSuccess, Toast.LENGTH_LONG).show();
                myDB.ThemSP(sanPham);
            }
            imgPreviewSP.setImageResource(R.drawable.no_img);
            edtMaSP.setText("");
            edtTenSP.setText("");
            edtXuatXu.setText("");
            edtDonGia.setText("");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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
                Toast.makeText(getApplicationContext(), "Bạn không có quyền truy cập vào file", Toast.LENGTH_SHORT).show();
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
        edtMaSP = findViewById(R.id.nhapMaSP);
        edtTenSP = findViewById(R.id.nhapTenSP);
        edtXuatXu = findViewById(R.id.nhapXuatXu);
        edtDonGia = findViewById(R.id.nhapDonGia);
        btnThem = findViewById(R.id.add_button_sp);

        btnChooseImg = findViewById(R.id.button_choose_img_addSP);
        imgPreviewSP = findViewById(R.id.imgPreviewAddSP);
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
