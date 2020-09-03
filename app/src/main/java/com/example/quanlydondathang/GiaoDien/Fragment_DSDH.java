package com.example.quanlydondathang.GiaoDien;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlydondathang.Adapter.CustomAdapterDH;
import com.example.quanlydondathang.Database.DBDonDatHang;
import com.example.quanlydondathang.Model.DonHang;
import com.example.quanlydondathang.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class Fragment_DSDH extends Fragment {
    RecyclerView recyclerViewDH;
    FloatingActionButton add_button_dh;
    ImageView img_empty;
    TextView khongDuLieu;

    ArrayList<DonHang> data = new ArrayList<>();
    DBDonDatHang myDB;
    CustomAdapterDH customAdapterDH;
    SwipeRefreshLayout swipeRefreshLayoutDH;

    CoordinatorLayout coLayoutDH;
    NavigationView navigationView;
    Toolbar toolbar;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);//Hiện option menu (Delete all + Search)
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dsdh,container,false);
        recyclerViewDH = view.findViewById(R.id.recyclerViewDH);
        add_button_dh = view.findViewById(R.id.add_button_dh);
        img_empty = view.findViewById(R.id.image_empty);
        khongDuLieu = view.findViewById(R.id.no_data);
        coLayoutDH = view.findViewById(R.id.coordinatorLayoutDH);
        swipeRefreshLayoutDH = view.findViewById(R.id.swipeToRefreshDH);
        navigationView = view.findViewById(R.id.nav_view);
        toolbar = view.findViewById(R.id.toolBar);
        setEvent();
        hienThiDatabase();

        //Đổi tên toolbar
        String toolbBarTitle = mContext.getResources().getString(R.string.trangDH);
        ((MainActivity)getActivity()).setActionBarTitle(toolbBarTitle);

        return view;

    }

    private void setEvent() {
        add_button_dh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ActivityThemDH.class);
                startActivity(intent);
            }
        });

        //Kéo trái, phải item để xóa + snackbar undo
        new ItemTouchHelper((new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                final DonHang recoverItem = data.get(position);
                //Lưu giữ id position
                //final int checkingId = data.get(position).getSttKH();
                data.remove(position);
                customAdapterDH.notifyItemRemoved(position);
                //long result = myDB.XoaItemTheoIDKH(checkingId);
                final Snackbar snackbar = Snackbar.make(coLayoutDH, "Đã xóa đơn hàng", Snackbar.LENGTH_LONG)
                        .setAction("Hoàn Tác", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                data.add(position, recoverItem);
                                customAdapterDH.notifyItemInserted(position);
                                customAdapterDH.notifyDataSetChanged();
                                myDB.ThemDH(recoverItem);
                            }

                        }).addCallback(new Snackbar.Callback() {
                            @Override
                            public void onDismissed(Snackbar snackbar, int dismissType) {
                                super.onDismissed(snackbar, dismissType);
                                if(dismissType == DISMISS_EVENT_TIMEOUT || dismissType == DISMISS_EVENT_SWIPE
                                        || dismissType == DISMISS_EVENT_CONSECUTIVE || dismissType == DISMISS_EVENT_MANUAL
                                        || dismissType == DISMISS_EVENT_ACTION) {
                                    myDB.XoaMotItemDH(recoverItem);
                                }
                            }
                        });
                snackbar.show();
            }
        })) .attachToRecyclerView(recyclerViewDH);

        //Kéo refresh list
        swipeRefreshLayoutDH.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                hienThiDatabase();
                swipeRefreshLayoutDH.setRefreshing(false);
            }
        });
    }

    public void hienThiDatabase(){
        myDB = new DBDonDatHang(mContext);
        data = myDB.listDonHangs();
        customAdapterDH = new CustomAdapterDH(getActivity(),mContext, data);
        recyclerViewDH.setAdapter(customAdapterDH);
        recyclerViewDH.setLayoutManager(new LinearLayoutManager(mContext));
        if(data.size() > 0){
            recyclerViewDH.setVisibility(View.VISIBLE);
            img_empty.setVisibility(View.GONE);
            khongDuLieu.setVisibility(View.GONE);
        }else {
            recyclerViewDH.setVisibility(View.GONE);
            img_empty.setVisibility(View.VISIBLE);
            khongDuLieu.setVisibility(View.VISIBLE);
//            String toastMessage = mContext.getResources().getString(R.string.noData);
//            Toast.makeText(mContext,toastMessage, Toast.LENGTH_SHORT).show();
        }
        customAdapterDH.notifyDataSetChanged();
    }

    //Tạo menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.chucnang_menu, menu) ;

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        search(searchView);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmXoaTatCa();
        }

        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (customAdapterDH!=null)
                    customAdapterDH.getFilter().filter(newText);
                return true;
            }
        });
    }

    void confirmXoaTatCa () {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        String toastTittle = mContext.getResources().getString(R.string.confirmDeleteAllTittle);
        String toastMessage = mContext.getResources().getString(R.string.confirmDeleteAllMessages);
        String toastYes = mContext.getResources().getString(R.string.confirmYes);
        String toastNo =mContext.getResources().getString(R.string.confirmNo);
        builder.setTitle(toastTittle);
        builder.setMessage(toastMessage);
        builder.setPositiveButton(toastYes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DBDonDatHang myDB = new DBDonDatHang(mContext);
                myDB.XoaTatCaDH();
                hienThiDatabase();
            }
        });
        builder.setNegativeButton(toastNo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }


    @Override
    public void onResume() {
        super.onResume();
        hienThiDatabase();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    public void onStart(){ super.onStart(); }
}
