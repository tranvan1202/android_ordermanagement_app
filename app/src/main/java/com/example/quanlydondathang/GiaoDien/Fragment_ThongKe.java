package com.example.quanlydondathang.GiaoDien;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.quanlydondathang.Database.DBDonDatHang;
import com.example.quanlydondathang.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class Fragment_ThongKe extends Fragment {
    private BarChart bar;
    private BarData data;
    private BarDataSet dataSet;

    private Context mContext;
    DBDonDatHang myDB;
    SwipeRefreshLayout swipeRefreshLayoutThongKe;

    public static Fragment_ThongKe newInstance() {
        Fragment_ThongKe fragment = new Fragment_ThongKe();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public Fragment_ThongKe() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //Đổi tên toolbar
        String toolbBarTitle = mContext.getResources().getString(R.string.trangThongKe);
        ((MainActivity)getActivity()).setActionBarTitle(toolbBarTitle);

        View view =inflater.inflate(R.layout.fragment_thongke, container, false);

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "antic_regular.ttf");


        swipeRefreshLayoutThongKe = view.findViewById(R.id.swipeToRefreshThongKe);
        bar = (BarChart)view.findViewById(R.id.barChart);

        myDB = new DBDonDatHang(mContext);
        int donHangHoanThanh = myDB.getSLDonHangHoanThanh();
        int donHangDangGiao = myDB.getSLDonHangDangGiao();
        int donHangDaHuy = myDB.getSLDonHangDaHuy();
        int donHangTong = myDB.getSLDonHangTong();

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, donHangTong,"Tổng đơn"));
        entries.add(new BarEntry(1f, donHangHoanThanh,"Hoàn thành"));
        entries.add(new BarEntry(2f, donHangDangGiao,"Đang giao"));
        entries.add(new BarEntry(3f, donHangDaHuy,"Đã hủy"));

        dataSet = new BarDataSet(entries, "Marks");
        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);

        ArrayList<String> barFactors = new ArrayList<>();
        barFactors.add("Tổng đơn");
        barFactors.add("Hoàn thành");
        barFactors.add("Đang giao");
        barFactors.add("Đã hủy");

        XAxis xAxis = bar.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        data = new BarData(dataSet);
        data.setBarWidth(0.9f); // set custom bar width
        data.setValueTextSize(12);
        data.notifyDataChanged();

        Description description = new Description();
        description.setTextColor(R.color.colorPrimary);
        description.setText("Swipe to refresh data");

        bar.setDescription(description);
        bar.setData(data);
        bar.setFitBars(true); // make the x-axis fit exactly all bars
        bar.notifyDataSetChanged();
        bar.invalidate(); // refresh
        bar.getXAxis().setValueFormatter(new IndexAxisValueFormatter(barFactors));

        Legend l = bar.getLegend();
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        l.setTypeface(font);
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);
        List<LegendEntry> lentries = new ArrayList<>();
        for (int i = 0; i < barFactors.size(); i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = ColorTemplate.VORDIPLOM_COLORS[i];
            entry.label = barFactors.get(i);
            lentries.add(entry);
        }
        l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        l.setYEntrySpace(5f);
        l.setCustom(lentries);

        setEvent();
        return view;
    }

    private void setEvent() {
        //Kéo refresh list
        swipeRefreshLayoutThongKe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayoutThongKe.setRefreshing(false);
                updateData();
            }
        });
    }

    public void updateData() {
        myDB = new DBDonDatHang(mContext);
        int donHangHoanThanh = myDB.getSLDonHangHoanThanh();
        int donHangDangGiao = myDB.getSLDonHangDangGiao();
        int donHangDaHuy = myDB.getSLDonHangDaHuy();
        int donHangTong = myDB.getSLDonHangTong();

        dataSet.removeEntry(0);
        data.addEntry(new BarEntry(0f, donHangTong,"Tổng đơn"),0);
        data.addEntry(new BarEntry(1f, donHangHoanThanh,"Hoàn thành"),0);
        data.addEntry(new BarEntry(2f, donHangDangGiao,"Đang giao"),0);
        data.addEntry(new BarEntry(3f, donHangDaHuy,"Đã hủy"),0);

        data.notifyDataChanged(); // NOTIFIES THE DATA OBJECT

        bar.notifyDataSetChanged(); // let the chart know it's data changed
        bar.invalidate(); // refresh
    }
}
