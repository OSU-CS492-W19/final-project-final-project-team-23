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
    void insert(SingleSearchResult repo);

    @Delete
    void delete(SingleSearchResult repo);

    @Query("SELECT * FROM series")
    LiveData<List<SingleSearchResult>> getAllRepos();

    @Query("SELECT * FROM series WHERE full_name = :fullName LIMIT 1")
    LiveData<SingleSearchResult> getRepoByName(String fullName);
}
