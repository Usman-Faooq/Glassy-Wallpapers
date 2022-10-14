package com.ussoft.gw_wallpaper.dataclasses;

import com.google.gson.annotations.SerializedName;

public class Wallpapers {

    @SerializedName("src")
    WallpaperSizes src;

    public Wallpapers(WallpaperSizes src) {
        this.src = src;
    }

    public WallpaperSizes getSrc() {
        return src;
    }

    public void setSrc(WallpaperSizes src) {
        this.src = src;
    }
}
