package com.ussoft.gw_wallpaper;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {


    TextView shareApp, rateApp, aboutUs, privacyApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        shareApp = findViewById(R.id.share_friend);
        rateApp = findViewById(R.id.rateapp);
        aboutUs = findViewById(R.id.aboutus);
        privacyApp = findViewById(R.id.privacyapp);
        shareApp.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName()))));

        rateApp.setOnClickListener(view -> startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName()))));

        aboutUs.setOnClickListener(view -> {
            Dialog dialog =new Dialog(SettingActivity.this);
            dialog.setContentView(R.layout.cardview_aboutus);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ImageView exitButton = dialog.findViewById(R.id.exit_about);
            exitButton.setOnClickListener(view1 -> dialog.dismiss());
            dialog.show();
        });

        privacyApp.setOnClickListener(view -> {
            Dialog dialog =new Dialog(SettingActivity.this);
            dialog.setContentView(R.layout.cardview_privacy);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ImageView exitButton = dialog.findViewById(R.id.exit_privacy);
            exitButton.setOnClickListener(view12 -> dialog.dismiss());
            dialog.show();
        });

    }
}