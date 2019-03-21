package com.example.android.finalproject.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {SingleSearchResult.class, ASearchQuery.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "anime_bookmarks_db").build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract SingleSearchResultDao singleSearchResultDao();
    public abstract ASearchQueryDao aSearchQueryDao();
}
