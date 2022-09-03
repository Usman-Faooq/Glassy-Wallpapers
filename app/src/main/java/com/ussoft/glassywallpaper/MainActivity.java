package com.ussoft.glassywallpaper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.ussoft.glassywallpaper.adapter.TabLayoutPageAdapter;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    TabLayoutPageAdapter tabLayoutPageAdapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    AdView mAdView;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        MobileAds.initialize(this, initializationStatus -> {
        });
        mAdView = findViewById(R.id.mainactivityadView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError adError) {
                super.onAdFailedToLoad(adError);
                mAdView.loadAd(adRequest);
            }

            @Override
            public void onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
            }
        });

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigation_view);
        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.main_activity_viewpager);


        tabLayoutPageAdapter = new TabLayoutPageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabLayoutPageAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0 || tab.getPosition() == 1 || tab.getPosition() == 2) {
                    tabLayoutPageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home_tab:
                    viewPager.setCurrentItem(0);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.cat_tab:
                    viewPager.setCurrentItem(1);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.fav_tab:
                    viewPager.setCurrentItem(2);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.share_tab:
                case R.id.rate_tab:
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.about_tab:
                    Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.cardview_aboutus);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    ImageView exitButton = dialog.findViewById(R.id.exit_about);
                    exitButton.setOnClickListener(view -> dialog.dismiss());
                    dialog.show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.privacy_tab:
                    Dialog privacyDialog = new Dialog(MainActivity.this);
                    privacyDialog.setContentView(R.layout.cardview_privacy);
                    privacyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    ImageView pexItButton = privacyDialog.findViewById(R.id.exit_privacy);
                    pexItButton.setOnClickListener(view -> privacyDialog.dismiss());
                    privacyDialog.show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.setting_tab:
                    Intent i = new Intent(MainActivity.this, SettingActivity.class);
                    startActivity(i);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
            }
            return true;
        });


        //Notification Work...
        NotificationChannel();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);

        if (Calendar.getInstance().after(calendar)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        Intent intent = new Intent(MainActivity.this, MemoBroadcast.class);
        @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }

    }


        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.search_menu, menu);
            MenuItem menuItem = menu.findItem(R.id.app_bar_search);
            SearchView searchView = (SearchView) menuItem.getActionView();
            searchView.setQueryHint("Search Category...");
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
                    intent.putExtra("search", query);
                    startActivity(intent);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
            return super.onCreateOptionsMenu(menu);
        }

    private void NotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Sheikh";
            String description = "Sheikh Channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Notification", name, importance);
            channel.setDescription(description);


            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }
}