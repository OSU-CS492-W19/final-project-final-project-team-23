package com.example.android.finalproject.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

@Entity(tableName = "queries")
public class ASearchQuery implements Serializable {
    @NonNull
    @PrimaryKey
    public String query;
}
