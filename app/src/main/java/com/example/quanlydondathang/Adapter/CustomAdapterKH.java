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
import com.example.quanlydondathang.GiaoDien.ActivitySuaKH;
import com.example.quanlydondathang.Model.KhachHang;
import com.example.quanlydondathang.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapterKH extends RecyclerView.Adapter<CustomAdapterKH.MyViewHolder> implements Filterable {
    private Context context;
    private Activity activity;
    private ArrayList<KhachHang> data;
    private ArrayList<KhachHang> dataArrayList;

    private DBDonDatHang myDB;

    public CustomAdapterKH(Activity activity, Context context, ArrayList<KhachHang> data){
        this.activity = activity;
        this.context = context;
        this.data = data;
        this.dataArrayList=data;
        myDB = new DBDonDatHang(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recyclerview_kh, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final KhachHang kh = data.get(position);
        holder.tvID.setText(String.valueOf(kh.getSttKH()));
        holder.tvMaKH.setText(String.valueOf(kh.getMaKH()));
        holder.tvTenKH.setText(String.valueOf(kh.getTenKH()));
        holder.tvDiaChi.setText(String.valueOf(kh.getDiaChi()));
        holder.tvSoDT.setText(String.valueOf(kh.getSoDT()));
        holder.recyclerViewItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivitySuaKH.class);
                intent.putExtra("id",Integer.valueOf(kh.getSttKH()));
                intent.putExtra("maKH", String.valueOf(kh.getMaKH()));
                intent.putExtra("tenKH", String.valueOf(kh.getTenKH()));
                intent.putExtra("diaChi", String.valueOf(kh.getDiaChi()));
                intent.putExtra("soDT", String.valueOf(kh.getSoDT()));
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
                    ArrayList<KhachHang> filteredList = new ArrayList<>();
                    for (KhachHang kh : dataArrayList) {

                        if (kh.getMaKH().toLowerCase().contains(charString)) {

                            filteredList.add(kh);
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
                data = (ArrayList<KhachHang>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvID, tvMaKH, tvTenKH, tvDiaChi, tvSoDT;
        LinearLayout recyclerViewItemLayout;
        ImageView imgUser;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.imgRecyclerViewIDKH);
            tvID = itemView.findViewById(R.id.tvRecyclerViewIDKH);
            tvMaKH = itemView.findViewById(R.id.tvMaKH);
            tvTenKH = itemView.findViewById(R.id.tvTenKH);
            tvDiaChi = itemView.findViewById(R.id.tvDiaChi);
            tvSoDT = itemView.findViewById(R.id.tvSoDT);
            recyclerViewItemLayout = itemView.findViewById(R.id.recyclerViewItemLayoutKH);
            //Animate Recyclerview
            //Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            //recyclerViewItemLayoutKH.setAnimation(translate_anim);
        }
    }
}
