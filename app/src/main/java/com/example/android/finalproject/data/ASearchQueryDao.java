package com.example.android.finalproject.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ASearchQueryDao {
    @Insert
    void insert(ASearchQuery query);

    @Delete
    void delete(ASearchQuery query);

    @Query("SELECT * FROM queries")
    LiveData<List<ASearchQuery>> getAllSearchQueries();

}
