package com.ussoft.gw_wallpaper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ussoft.gw_wallpaper.R;

import java.util.ArrayList;

public class FullScreenAdapter extends RecyclerView.Adapter<FullScreenAdapter.MyHolder> {
    Context context;
    ArrayList<String> wallpaperList;

    public FullScreenAdapter(Context context, ArrayList<String> wallpaperList) {
        this.context = context;
        this.wallpaperList = wallpaperList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_fullscreelayout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Glide.with(context)
                .load(wallpaperList.get(position))
                .into(holder.sliderImage);
    }

    @Override
    public int getItemCount() {
        return wallpaperList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        ImageView sliderImage;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            sliderImage = itemView.findViewById(R.id.fullscreen_image);
        }
    }
}
