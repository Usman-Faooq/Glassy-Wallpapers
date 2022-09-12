package com.ussoft.glassywallpaper.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavImages {
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "ImageURL")
    String imageURL;

    public FavImages(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
