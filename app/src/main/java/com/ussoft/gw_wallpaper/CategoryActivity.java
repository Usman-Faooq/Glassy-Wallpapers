package com.ussoft.gw_wallpaper;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.ussoft.gw_wallpaper.adapter.WallpaperAdapter;
import com.ussoft.gw_wallpaper.api.RetrofitClient;
import com.ussoft.gw_wallpaper.dataclasses.WallpaperData;
import com.ussoft.gw_wallpaper.dataclasses.Wallpapers;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {

    final String API_KEY = "563492ad6f91700001000001d2e9665eb933495b98e9c5da74c4e609";
    int pageNumber = 1;
    static int perPageItemCount = 80;
    ArrayList<String> wallpaperSize = new ArrayList<>();
    ArrayList<Wallpapers> wallpapersList = new ArrayList<>();
    Toolbar toolbar;
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    NestedScrollView nestedScrollView;
    ShimmerFrameLayout shimmerFrameLayout;
    ProgressBar progressBar;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.CategoryBanner);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        toolbar = findViewById(R.id.categorytoolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String searchText = getIntent().getStringExtra("search").toLowerCase();
        toolbar.setTitle(searchText);
        shimmerFrameLayout = findViewById(R.id.category_shimmer_loading_layout);
        progressBar = findViewById(R.id.category_loadingbar);
        nestedScrollView = findViewById(R.id.category_nestedscrollview);
        recyclerView = findViewById(R.id.category_recyclerview);
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        fetchWallpapers(pageNumber, searchText);
        setPagination(searchText);

    }

    private void fetchWallpapers(int pageNumber, String queryText) {
        Call<WallpaperData> call = RetrofitClient
                .getInstance()
                .getApi()
                .getCategory(API_KEY, pageNumber, perPageItemCount, queryText);

        call.enqueue(new Callback<WallpaperData>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<WallpaperData> call, @NonNull Response<WallpaperData> response) {
                WallpaperData data = response.body();
                if (response.isSuccessful() && null!=data){
                    wallpapersList.addAll(data.getPhotosList());
                    for (int i = 0 ;  i < wallpapersList.size(); i++){
                        wallpaperSize.add(wallpapersList.get(i).getSrc().getPortraitimage());
                    }
                    WallpaperAdapter wallpaperAdapter = new WallpaperAdapter(CategoryActivity.this, wallpapersList, wallpaperSize);
                    recyclerView.setAdapter(wallpaperAdapter);
                    shimmerFrameLayout.startShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    wallpaperAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(@NonNull Call<WallpaperData> call, @NonNull Throwable t) {

            }
        });
    }

    private void setPagination(String searchText) {
        progressBar.setVisibility(View.GONE);
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight()){
                progressBar.setVisibility(View.VISIBLE);
                fetchWallpapers(++pageNumber, searchText);
            }else{
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onResume() {
        Log.d("UGS LifeCycle: ", "On Resume");
        shimmerFrameLayout.startShimmer();
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("UGS LifeCycle: ", "On Pause");
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }
}