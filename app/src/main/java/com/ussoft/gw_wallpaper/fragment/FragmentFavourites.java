package com.ussoft.gw_wallpaper.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ussoft.gw_wallpaper.R;
import com.ussoft.gw_wallpaper.adapter.FavouritesWallpaperAdapter;
import com.ussoft.gw_wallpaper.database.AppDatabase;
import com.ussoft.gw_wallpaper.database.FavImages;
import com.ussoft.gw_wallpaper.database.UserDao;

import java.util.ArrayList;
import java.util.List;

public class FragmentFavourites extends Fragment {

    public FragmentFavourites() {
        // Required empty public constructor
    }

    ArrayList<String> wallpaperSize = new ArrayList<>();
    List<FavImages> allWallpapers = new ArrayList<>();
    RecyclerView recyclerView;
    GridLayoutManager layoutManager;
    ShimmerFrameLayout shimmerFrameLayout;
    FavouritesWallpaperAdapter wallpaperAdapter;
    ImageView checkEmpty;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        shimmerFrameLayout = view.findViewById(R.id.fav_shimmer_loading_layout);
        recyclerView = view.findViewById(R.id.fav_recyclerview);
        checkEmpty = view.findViewById(R.id.nodatafound);
        checkEmpty.setVisibility(View.GONE);
        layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        allWallpapers.clear();
        wallpaperSize.clear();
        fetchWallpapers();


        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchWallpapers() {
        AppDatabase db = Room.databaseBuilder(getActivity(),
                AppDatabase.class, "GlassyWallpaper").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        UserDao userDao = db.dao();
        allWallpapers = userDao.getAllWallpapers();
        if (allWallpapers.isEmpty()){
            checkEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.INVISIBLE);
            shimmerFrameLayout.setVisibility(View.INVISIBLE);
        }else{
            for (int i = 0 ;  i < allWallpapers.size(); i++){
                wallpaperSize.add(allWallpapers.get(i).getImageURL());
            }
            wallpaperAdapter = new FavouritesWallpaperAdapter(getActivity(), wallpaperSize);
            checkEmpty.setVisibility(View.GONE);
            recyclerView.setAdapter(wallpaperAdapter);
            shimmerFrameLayout.startShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
            wallpaperAdapter.notifyDataSetChanged();

        }
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