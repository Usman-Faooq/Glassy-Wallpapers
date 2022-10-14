package com.ussoft.gw_wallpaper.dataclasses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WallpaperData {

    @SerializedName("photos")
    List<Wallpapers> photosList;

    public WallpaperData(List<Wallpapers> photosList) {
        this.photosList = photosList;
    }

    public List<Wallpapers> getPhotosList() {
        return photosList;
    }

    public void setPhotosList(List<Wallpapers> photosList) {
        this.photosList = photosList;
    }
}
