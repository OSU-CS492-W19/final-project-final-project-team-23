package com.example.android.finalproject;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.android.finalproject.data.ASearchQuery;
import com.example.android.finalproject.data.ASearchQueryRepository;

import java.util.List;

public class ASearchQueryViewModel extends AndroidViewModel {
    private ASearchQueryRepository mASearchQueryRepository;

    public ASearchQueryViewModel(Application app){
        super(app);
        mASearchQueryRepository = new ASearchQueryRepository(app);
    }

    public void insertASearchQuery(ASearchQuery query){
        mASearchQueryRepository.insertASearchQuery(query);
    }

    public void deleteASearchQuery(ASearchQuery query){
        mASearchQueryRepository.deleteASearchQuery(query);
    }

    public LiveData<List<ASearchQuery>> getAllSearchQueries(){
        return mASearchQueryRepository.getAllSearchResults();
    }
}
