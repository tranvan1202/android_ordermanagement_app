package com.example.quanlydondathang.GiaoDien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quanlydondathang.R;

public class ActivitySplashScreen extends AppCompatActivity {
    private  static int SPLASH_SCREEN = 5000;
    Animation topAnimSplashScreen, bottomAnimSplashScreen, waterWaveAnimSplashScreen, rotateAnimSplashScreen;
    ImageView imageSplashScreen, imageSplashScreen2;
    TextView tvAppNameSplashScreen, tvSloganSplashScreen;
    LinearLayout waterWaveLayout;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_splash_screen);

        //Khai báo cho các file animation
        topAnimSplashScreen = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimSplashScreen = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        waterWaveAnimSplashScreen = AnimationUtils.loadAnimation(this, R.anim.water_wave_animation);
        rotateAnimSplashScreen = AnimationUtils.loadAnimation(this,R.anim.rotate_animation);

        //Logo và chữ từ XML
        imageSplashScreen = findViewById(R.id.imgSplashScreen);
        imageSplashScreen2 = findViewById(R.id.imgSplashScreen2);
        tvAppNameSplashScreen = findViewById(R.id.tvAppNameSplashScreen);
        tvSloganSplashScreen = findViewById(R.id.tvInfoSplashScreen);
        //Background ngoài từ XML
        coordinatorLayout = findViewById(R.id.splashScreenLayout);
        //Hiệu ứng sóng bên trong từ XML
        waterWaveLayout = findViewById(R.id.splashScreenWaveBackground);

        //Set font chữ (từ API 16 trở lên dùng font family)
        Typeface fontAppName = Typeface.createFromAsset(getAssets(),"banger_regular.ttf");
        tvAppNameSplashScreen.setTypeface(fontAppName);
        Typeface fontSlogan = Typeface.createFromAsset(getAssets(),"antic_regular.ttf");
        tvSloganSplashScreen.setTypeface(fontSlogan);

        //Set Animation từ trên xuống cho splash screen
        imageSplashScreen2.setAnimation(topAnimSplashScreen);
        imageSplashScreen.setAnimation(rotateAnimSplashScreen);
        tvAppNameSplashScreen.setAnimation(bottomAnimSplashScreen);
        tvSloganSplashScreen.setAnimation(bottomAnimSplashScreen);
        waterWaveLayout.setAnimation(waterWaveAnimSplashScreen);

        //Set animation background của Splash Screen
        AnimationDrawable animationDrawable = (AnimationDrawable) coordinatorLayout.getBackground();
        animationDrawable.setEnterFadeDuration(10);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();

        //Chuyển sang trang homepage
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ActivitySplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}