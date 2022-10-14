package com.ussoft.gw_wallpaper.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ussoft.gw_wallpaper.R;
import com.ussoft.gw_wallpaper.adapter.WallpaperAdapter;
import com.ussoft.gw_wallpaper.api.RetrofitClient;
import com.ussoft.gw_wallpaper.dataclasses.WallpaperData;
import com.ussoft.gw_wallpaper.dataclasses.Wallpapers;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentHome extends Fragment {

    public FragmentHome() {
        // Required empty public constructor
    }

    final String API_KEY = "563492ad6f91700001000001d2e9665eb933495b98e9c5da74c4e609";
    int pageNumber = 1;
    static int perPageItemCount = 80;
    ArrayList<String> wallpaperSize = new ArrayList<>();
    ArrayList<Wallpapers> wallpapersList = new ArrayList<>();
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    NestedScrollView nestedScrollView;
    ShimmerFrameLayout shimmerFrameLayout;
    ProgressBar progressBar;
    LinearLayout netLayout;
    RelativeLayout dataLayout;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        shimmerFrameLayout = view.findViewById(R.id.mainfrag_shimmer_loading_layout);
        progressBar = view.findViewById(R.id.main_loadingbar);
        nestedScrollView = view.findViewById(R.id.nestedscrollview);
        recyclerView = view.findViewById(R.id.main_recyclerview);
        netLayout = view.findViewById(R.id.internet_connection_layout);
        dataLayout = view.findViewById(R.id.data_rlayout);

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            netLayout.setVisibility(View.GONE);
        }else{
            dataLayout.setVisibility(View.GONE);
        }

        layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        fetchWallpapers(pageNumber);
        setPagination();
        //progressBar.setVisibility(View.GONE);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setPagination() {
        progressBar.setVisibility(View.GONE);
        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY == v.getChildAt(0).getMeasuredHeight()-v.getMeasuredHeight()){
                progressBar.setVisibility(View.VISIBLE);
                fetchWallpapers(++pageNumber);
            }else{
                progressBar.setVisibility(View.GONE);
            }

        });
    }

    private void fetchWallpapers(int pageCount) {
        Call<WallpaperData> call = RetrofitClient
                .getInstance()
                .getApi()
                .getWallpaper(API_KEY, pageCount, perPageItemCount);

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
                    WallpaperAdapter wallpaperAdapter = new WallpaperAdapter(getActivity(), wallpapersList, wallpaperSize);
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