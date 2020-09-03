package com.example.quanlydondathang.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quanlydondathang.Database.DBDonDatHang;
import com.example.quanlydondathang.GiaoDien.ActivitySuaDH;
import com.example.quanlydondathang.Model.DonHang;
import com.example.quanlydondathang.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapterDH extends RecyclerView.Adapter<CustomAdapterDH.MyViewHolder> implements Filterable {
    private Context context;
    private Activity activity;
    private ArrayList<DonHang> data;
    private ArrayList<DonHang> dataArrayList;

    private DBDonDatHang myDB;

    public CustomAdapterDH(Activity activity, Context context, ArrayList<DonHang> data){
        this.activity = activity;
        this.context = context;
        this.data = data;
        this.dataArrayList=data;
        myDB = new DBDonDatHang(context);
    }

    @NonNull
    @Override
    public CustomAdapterDH.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recyclerview_dh, parent, false);
        return new CustomAdapterDH.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapterDH.MyViewHolder holder, final int position) {
        final DonHang dh = data.get(position);
        holder.tvIDDH.setText(String.valueOf(dh.getSttDH()));
        holder.tvMaDH.setText(String.valueOf(dh.getMaDH()));
        holder.tvMaKH.setText(String.valueOf(dh.getMaKH()));
        holder.tvMaSP.setText(String.valueOf(dh.getMaSP()));
        holder.tvSoLuongSPDat.setText(String.valueOf(dh.getSoLuongDat()));
        holder.tvNgayTao.setText(String.valueOf(dh.getNgayTaoDH()));
        holder.tvTinhTrangDH.setText(String.valueOf(dh.getTinhTrangDH()));

        holder.recyclerViewItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivitySuaDH.class);
                intent.putExtra("id",Integer.valueOf(dh.getSttDH()));
                intent.putExtra("maDH", String.valueOf(dh.getMaDH()));
                intent.putExtra("maKH", String.valueOf(dh.getMaKH()));
                intent.putExtra("maSP", String.valueOf(dh.getMaSP()));
                intent.putExtra("soLuongSPDat", Integer.valueOf(dh.getSoLuongDat()));
                intent.putExtra("ngayTaoDH", String.valueOf(dh.getNgayTaoDH()));
                intent.putExtra("tinhTrangDH", String.valueOf(dh.getTinhTrangDH()));
                activity.startActivityForResult(intent,1);
            }
        });
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
                    ArrayList<DonHang> filteredList = new ArrayList<>();
                    for (DonHang dh : dataArrayList) {

                        if (dh.getMaDH().toLowerCase().contains(charString)) {

                            filteredList.add(dh);
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
                data = (ArrayList<DonHang>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvIDDH, tvMaDH, tvMaKH, tvMaSP, tvSoLuongSPDat, tvNgayTao, tvTinhTrangDH;
        LinearLayout recyclerViewItemLayout;
        ImageView imgDH;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgDH = itemView.findViewById(R.id.imgRecyclerViewIDDH);
            tvIDDH = itemView.findViewById(R.id.tvRecyclerViewIDDH);
            tvMaDH = itemView.findViewById(R.id.tvMaDH);
            tvMaKH = itemView.findViewById(R.id.tvMaKHDH);
            tvMaSP = itemView.findViewById(R.id.tvMaSPDH);
            tvSoLuongSPDat = itemView.findViewById(R.id.tvSoLuong);
            tvNgayTao = itemView.findViewById(R.id.tvNgayTaoDH);
            tvTinhTrangDH = itemView.findViewById(R.id.tvTinhTrangDH);

            recyclerViewItemLayout = itemView.findViewById(R.id.recyclerViewItemLayoutDH);
            //Animate Recyclerview
            //Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            //recyclerViewItemLayoutKH.setAnimation(translate_anim);
        }
    }
}
