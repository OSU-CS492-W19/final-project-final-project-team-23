package com.example.android.finalproject.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "series")
public class SingleSearchResult implements Serializable {
    @NonNull
    @PrimaryKey
    public String name;
    public String image;
    public String genres;
    public String summary;
    public String airDate;

    public String html_url;
}
