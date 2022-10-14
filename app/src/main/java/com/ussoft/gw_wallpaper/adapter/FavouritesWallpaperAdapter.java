package com.ussoft.gw_wallpaper.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ussoft.gw_wallpaper.PageSliderActivity;
import com.ussoft.gw_wallpaper.R;

import java.util.ArrayList;

public class FavouritesWallpaperAdapter extends RecyclerView.Adapter<FavouritesWallpaperAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> wallpapers;

    public FavouritesWallpaperAdapter(Context context, ArrayList<String> wallpapers) {
        this.context = context;
        this.wallpapers = wallpapers;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_mainactivity, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(wallpapers.get(position))
                .into(holder.imageThumbnail);
        holder.imageThumbnail.setOnClickListener(view -> {
            Intent intent = new Intent(context, PageSliderActivity.class);
            intent.putExtra("page_position", position);
            intent.putStringArrayListExtra("WallpaperSrc", wallpapers);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return wallpapers.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageThumbnail;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageThumbnail = itemView.findViewById(R.id.image_thumbnail);
        }
    }
}
