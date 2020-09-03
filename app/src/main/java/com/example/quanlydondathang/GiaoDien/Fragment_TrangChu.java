package com.example.quanlydondathang.GiaoDien;

import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.quanlydondathang.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Fragment_TrangChu extends Fragment implements View.OnClickListener {
    CardView cvKH,cvSP,cvDH,cvThongKe;
    private Context mContext;
    //sensors
    private SensorManager mSensorManager;
    private float mAccel; // acceleration apart from gravity
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast; // last acceleration including gravity
    //hop thoai hien trong sensor shake
    static AlertDialog.Builder alertbox;
    static AlertDialog alertDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trangchu,container,false);
        cvKH = view.findViewById(R.id.homeKH);
        cvSP = view.findViewById(R.id.homeSP);
        cvDH = view.findViewById(R.id.homeDH);
        cvThongKe = view.findViewById(R.id.homeThongKe);
        cvKH.setOnClickListener(this);
        cvSP.setOnClickListener(this);
        cvDH.setOnClickListener(this);
        cvThongKe.setOnClickListener(this);
        setEvent();
        //Đổi tên toolbar
        String toolbBarTitle = mContext.getResources().getString(R.string.app_name);
        ((MainActivity)getActivity()).setActionBarTitle(toolbBarTitle);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onClick(View view) {
        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.homeKH:
                fragment = new Fragment_DSKH();
                replaceFragment(fragment);
                break;

            case R.id.homeSP:
                fragment = new Fragment_DSSP();
                replaceFragment(fragment);
                break;

            case R.id.homeDH:
                fragment = new Fragment_DSDH();
                replaceFragment(fragment);
                break;
            case R.id.homeThongKe:
                fragment = new Fragment_ThongKe();
                replaceFragment(fragment);
                break;
        }
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_fragment, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setEvent() {
        //Sensor Shake
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent se) {
            float x = se.values[0];
            float y = se.values[1];
            float z = se.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x*x + y*y + z*z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter

            if (mAccel > 12) {
                String toastTittle = mContext.getResources().getString(R.string.titleDialogShakeDH);
                String toastMessage = mContext.getResources().getString(R.string.messageDialogShakeDH);
                showAlertDialogDH(toastTittle, toastMessage, mContext,true);
            }
        }
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    public static void showAlertDialogDH(final String title, String message,
                                         final Context context, final boolean redirectToPreviousScreen) {
        if (alertDialog != null && alertDialog.isShowing()) {
            // A dialog is already open, wait for it to be dismissed, do nothing
        } else {
            alertbox = new AlertDialog.Builder(context);
            alertbox.setMessage(message);
            alertbox.setTitle(title);
            alertbox.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    alertDialog.dismiss();
                }
            });
            alertDialog = alertbox.create();
            alertDialog.show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_FASTEST);
    }
}
