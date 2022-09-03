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

    @Query("Select Exists(Select * from FavImages where ImageID = :id)")
    Boolean is_Exist(int id);

    @Query("Delete from FavImages where ImageID = :id")
    void deleteWallpaper(int id);

}