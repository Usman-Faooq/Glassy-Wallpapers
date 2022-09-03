package com.ussoft.glassywallpaper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ussoft.glassywallpaper.R;

import java.util.ArrayList;

public class PagerSliderAdapter extends RecyclerView.Adapter<PagerSliderAdapter.MyHolder>{

    Context context;
    ArrayList<String> wallpapers;

    public PagerSliderAdapter(Context context, ArrayList<String> wallpapers) {
        this.context = context;
        this.wallpapers = wallpapers;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cardview_sliderlayout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Glide.with(context)
                .load(wallpapers.get(position))
                .into(holder.sliderImage);

    }

    @Override
    public int getItemCount() {
        return wallpapers.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        ImageView sliderImage;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            sliderImage = itemView.findViewById(R.id.slider_pager_image);
        }
    }
}
