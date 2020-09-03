package com.example.quanlydondathang.GiaoDien;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.quanlydondathang.R;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;
    TextView toolbarTitle;
    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Đổi tên toolbar
        toolbar = findViewById(R.id.toolBar);
        toolbarTitle = toolbar.findViewById(R.id.toolBar_title);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayoutKH);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openBar,R.string.closeBar);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        //Load default fragment
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment,new Fragment_TrangChu());
        fragmentTransaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //Click menuItem của Navigation thì đóng Nav lại
        drawerLayout.closeDrawer(GravityCompat.START);
        if(menuItem.getItemId() == R.id.nav_home) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_TrangChu());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.nav_kh) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_DSKH());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.nav_sp) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_DSSP());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.nav_dh) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_DSDH());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.nav_thongke) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new Fragment_ThongKe());
            fragmentTransaction.commit();
        }
        if(menuItem.getItemId() == R.id.nav_doingonngu) {
            Intent myIntent = new Intent(MainActivity.this, ActivityDoiNgonNgu.class);
            MainActivity.this.startActivity(myIntent);
        }
//        if(menuItem.getItemId() == R.id.nav_aboutus) {
//            Intent myIntent = new Intent(MainActivity.this, ActivityAboutUs.class);
//            MainActivity.this.startActivity(myIntent);
//        }
        return true;
    }

    //Đổi tên toolbar
    public void setActionBarTitle(String title) {
        toolbarTitle.setText(title);
    }
}