package com.ussoft.glassywallpaper.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavImages.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao dao();
}
