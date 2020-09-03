package com.example.quanlydondathang.GiaoDien;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Spinner;
import android.widget.TextView;

import com.example.quanlydondathang.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityDoiNgonNgu extends AppCompatActivity {
    TextView mTextView;
    Spinner mLanguage;
    ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_language);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.trangDoiNgonNgu));
        actionBar.setDisplayHomeAsUpEnabled(true);

        mLanguage = (Spinner) findViewById(R.id.spLanguage);
        mTextView = (TextView) findViewById(R.id.textView);
        mAdapter = new ArrayAdapter<String>(ActivityDoiNgonNgu.this,
                android.R.layout.simple_spinner_dropdown_item,
                getResources().getStringArray(R.array.language_option));
        mLanguage.setAdapter(mAdapter);

        if (com.example.quanlydondathang.Adapter.LocaleHelper.getLanguage(ActivityDoiNgonNgu.this).equalsIgnoreCase("en")) {
            mLanguage.setSelection(mAdapter.getPosition("English"));
        } else if (com.example.quanlydondathang.Adapter.LocaleHelper.getLanguage(ActivityDoiNgonNgu.this).equalsIgnoreCase("vi-rVN")) {
            mLanguage.setSelection(mAdapter.getPosition("Vietnamese"));
        }

        mLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Context context;
                Resources resources;
                switch (i) {
                    case 0:
                        context = com.example.quanlydondathang.Adapter.LocaleHelper.setLocale(ActivityDoiNgonNgu.this, "en");
                        resources = context.getResources();
                        mTextView.setText(resources.getString(R.string.app_name));
                        break;
                    case 1:
                        context = com.example.quanlydondathang.Adapter.LocaleHelper.setLocale(ActivityDoiNgonNgu.this, "vi-rVN");
                        resources = context.getResources();
                        mTextView.setText(resources.getString(R.string.app_name));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(com.example.quanlydondathang.Adapter.LocaleHelper.onAttach(newBase));
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    //Bấm nút quay về trang chủ (ko dùng AndroidManifest được vì là từ Fragment về Activity)
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
