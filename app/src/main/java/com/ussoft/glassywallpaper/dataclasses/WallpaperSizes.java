package com.ussoft.glassywallpaper.dataclasses;

import com.google.gson.annotations.SerializedName;

public class WallpaperSizes {
    @SerializedName("portrait")
    String portraitimage;

    @SerializedName("medium")
    String mediumimage;

    @SerializedName("large")
    String largeimage;

    public WallpaperSizes(String portraitimage, String mediumimage, String largeimage) {
        this.portraitimage = portraitimage;
        this.mediumimage = mediumimage;
        this.largeimage = largeimage;
    }

    public String getPortraitimage() {
        return portraitimage;
    }

    public void setPortraitimage(String portraitimage) {
        this.portraitimage = portraitimage;
    }

    public String getMediumimage() {
        return mediumimage;
    }

    public void setMediumimage(String mediumimage) {
        this.mediumimage = mediumimage;
    }

    public String getLargeimage() {
        return largeimage;
    }

    public void setLargeimage(String largeimage) {
        this.largeimage = largeimage;
    }
}
