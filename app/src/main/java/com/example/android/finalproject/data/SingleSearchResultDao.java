package com.example.android.finalproject.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SingleSearchResultDao {
    @Insert
    void insert(SingleSearchResult result);

    @Delete
    void delete(SingleSearchResult result);

    @Query("SELECT * FROM series")
    LiveData<List<SingleSearchResult>> getAllSearchResults();

    @Query("SELECT * FROM series WHERE name = :fullName LIMIT 1")
    LiveData<SingleSearchResult> getResultByName(String fullName);
}
