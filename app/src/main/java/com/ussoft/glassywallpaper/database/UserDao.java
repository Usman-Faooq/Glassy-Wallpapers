package com.ussoft.glassywallpaper.database;

import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@androidx.room.Dao
public interface UserDao {

    @Insert
    void insert(FavImages images);

    @Query("Select * from FavImages")
    List<FavImages> getAllWallpapers();

    @Query("Select Exists(Select * from FavImages where ImageURL = :url)")
    Boolean is_Exist(String url);

    @Query("Delete from FavImages where ImageURL = :url")
    void deleteWallpaper(String url);


}