package com.ussoft.glassywallpaper.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FavImages {
    @PrimaryKey(autoGenerate = true)
    int id;

    @ColumnInfo(name = "ImageID")
    int imageID;

    @ColumnInfo(name = "ImageURL")
    String imageURL;

    public FavImages(int imageID, String imageURL) {
        this.imageID = imageID;
        this.imageURL = imageURL;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
