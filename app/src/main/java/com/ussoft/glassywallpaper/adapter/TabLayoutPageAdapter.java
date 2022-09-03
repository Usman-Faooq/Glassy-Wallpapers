package com.ussoft.glassywallpaper.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ussoft.glassywallpaper.fragment.FragmentCategory;
import com.ussoft.glassywallpaper.fragment.FragmentFavourites;
import com.ussoft.glassywallpaper.fragment.FragmentHome;

public class TabLayoutPageAdapter extends FragmentPagerAdapter {
    int totalCount;
    public TabLayoutPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        totalCount = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new FragmentHome();
        }else if (position == 1){
            return new FragmentCategory();
        }else{
            return new FragmentFavourites();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
