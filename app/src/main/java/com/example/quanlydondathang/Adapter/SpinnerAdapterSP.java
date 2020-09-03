package com.example.quanlydondathang.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlydondathang.Model.SanPham;
import com.example.quanlydondathang.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SpinnerAdapterSP extends ArrayAdapter<SanPham> {

    public SpinnerAdapterSP(Context context, ArrayList<SanPham> sanPhamList) {
        super(context, 0, sanPhamList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }
    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_value_layout_sp, parent, false
            );
        }
        ImageView imageViewSanPham = convertView.findViewById(R.id.spinnerImageViewSP);
        TextView textViewName = convertView.findViewById(R.id.spinnerTextViewSP);
        SanPham currentItem = getItem(position);
        if (currentItem != null) {
            byte[] anhSP = currentItem.getImageSP();
            Bitmap bitmap = BitmapFactory.decodeByteArray(anhSP, 0, anhSP.length);
            //Scale ảnh theo tỉ lệ
            Bitmap.createScaledBitmap(bitmap, 150, 100, false);
            imageViewSanPham.setImageBitmap(bitmap);
            textViewName.setText(currentItem.getMaSP());
        }
        return convertView;
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
