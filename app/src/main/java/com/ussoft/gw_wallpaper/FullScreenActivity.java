package com.ussoft.gw_wallpaper;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.ussoft.gw_wallpaper.adapter.FullScreenAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class FullScreenActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    FullScreenAdapter fullScreenAdapter;
    ArrayList<String> wallpapers;
    TextView setWallpaper;
    ImageView downloadBtn;
    ImageView backButton;
    String imageURL;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-3304369012094568/6133569718", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });



        viewPager2 = findViewById(R.id.fullscreen_viewpager);
        int currentItemPosition =getIntent().getIntExtra("CurrentPosition", 0);
        wallpapers = getIntent().getStringArrayListExtra("WallpaperList");
        fullScreenAdapter = new FullScreenAdapter(this, wallpapers);
        viewPager2.setAdapter(fullScreenAdapter);
        viewPager2.setCurrentItem(currentItemPosition);

        backButton = findViewById(R.id.fullscreen_backbutton);
        backButton.setOnClickListener(view -> finish());

        setWallpaper = findViewById(R.id.fullscreen_setwallpaper);
        downloadBtn = findViewById(R.id.fullscreen_savebtn);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                imageURL = wallpapers.get(position);
            }
        });

        setWallpaper.setOnClickListener(view -> {
            if (mInterstitialAd != null) {
                mInterstitialAd.show(FullScreenActivity.this);
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.");
            }
            WallpaperManager manager = WallpaperManager.getInstance(getApplicationContext());
            Glide.with(getApplicationContext())
                    .asBitmap().load(wallpapers.get(viewPager2.getCurrentItem()))
                    .into(new CustomTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            try {
                                manager.setBitmap(resource);
                                Toast.makeText(getApplicationContext(), "Set Wallpaper", Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder) {
                        }
                    });
        });

        downloadBtn.setOnClickListener(view ->
                Glide.with(getApplicationContext())
                .asBitmap().load(imageURL)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        if (mInterstitialAd != null) {
                            mInterstitialAd.show(FullScreenActivity.this);
                        } else {
                            Log.d("TAG", "The interstitial ad wasn't ready yet.");
                        }
                        try {
                            File storage = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                            File dir = new File(storage.getAbsolutePath() + "/Glassy Wallpapers");
                            if (!dir.exists()){
                                dir.mkdirs();
                            }
                            String filename = String.format("%d.jpg", System.currentTimeMillis());
                            File out = new File(dir, filename);
                            FileOutputStream stream = new FileOutputStream(out);
                            resource.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                            Toast.makeText(FullScreenActivity.this, "Image Saved", Toast.LENGTH_SHORT).show();
                            stream.flush();
                            stream.close();
                        }catch (Exception e){
                            Toast.makeText(FullScreenActivity.this, "Not Saved: " + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                }));
    }
}