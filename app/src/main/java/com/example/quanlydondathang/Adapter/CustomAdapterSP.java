package com.example.quanlydondathang.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quanlydondathang.Database.DBDonDatHang;
import com.example.quanlydondathang.GiaoDien.ActivitySuaSP;
import com.example.quanlydondathang.Model.SanPham;
import com.example.quanlydondathang.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapterSP extends RecyclerView.Adapter<CustomAdapterSP.MyViewHolder> implements Filterable {
    private Context context;
    private Activity activity;
    private ArrayList<SanPham> data;
    private ArrayList<SanPham> dataArrayList;

    private DBDonDatHang myDB;

    public CustomAdapterSP(Activity activity, Context context, ArrayList<SanPham> data){
        this.activity = activity;
        this.context = context;
        this.data = data;
        this.dataArrayList=data;
        myDB = new DBDonDatHang(context);
    }

    @NonNull
    @Override
    public CustomAdapterSP.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recyclerview_sp, parent, false);
        return new CustomAdapterSP.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterSP.MyViewHolder holder, final int position) {
        final SanPham sp = data.get(position);

        byte[] imgSP = sp.getImageSP();
        final Bitmap bitmap = BitmapFactory.decodeByteArray(imgSP, 0, imgSP.length);
        holder.imgSP.setImageBitmap(Bitmap.createScaledBitmap(bitmap,200,200,false));

        holder.tvIDSP.setText(String.valueOf(sp.getSttSP()));
        holder.tvMaSP.setText(String.valueOf(sp.getMaSP()));
        holder.tvTenSP.setText(String.valueOf(sp.getTenSP()));
        holder.tvXuatXu.setText(String.valueOf(sp.getXuatXu()));
        holder.tvDonGia.setText(String.valueOf(sp.getDonGia()));
        holder.recyclerViewItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivitySuaSP.class);
                intent.putExtra("id",Integer.valueOf(sp.getSttSP()));
                intent.putExtra("maSP", String.valueOf(sp.getMaSP()));
                intent.putExtra("tenSP", String.valueOf(sp.getTenSP()));
                intent.putExtra("xuatXu", String.valueOf(sp.getXuatXu()));
                intent.putExtra("donGia", String.valueOf(sp.getDonGia()));
                intent.putExtra("anhSP", convertBitmapToByteArray(bitmap));
                activity.startActivityForResult(intent,1);
            }
        });
    }

    byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos;
        baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, baos);
        return baos.toByteArray();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    data = dataArrayList;
                } else {
                    ArrayList<SanPham> filteredList = new ArrayList<>();
                    for (SanPham sp : dataArrayList) {

                        if (sp.getMaSP().toLowerCase().contains(charString)) {

                            filteredList.add(sp);
                        }
                    }
                    data = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = data;
                return filterResults;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                data = (ArrayList<SanPham>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvIDSP, tvMaSP, tvTenSP, tvXuatXu, tvDonGia;
        LinearLayout recyclerViewItemLayout;
        ImageView imgSP;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSP = itemView.findViewById(R.id.imgRecyclerViewIDSP);
            tvIDSP = itemView.findViewById(R.id.tvRecyclerViewIDSP);
            tvMaSP = itemView.findViewById(R.id.tvMaSP);
            tvTenSP = itemView.findViewById(R.id.tvTenSP);
            tvXuatXu = itemView.findViewById(R.id.tvXuatXu);
            tvDonGia = itemView.findViewById(R.id.tvDonGia);
            recyclerViewItemLayout = itemView.findViewById(R.id.recyclerViewItemLayoutSP);
            //Animate Recyclerview
            //Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            //recyclerViewItemLayoutKH.setAnimation(translate_anim);
        }
    }
}
