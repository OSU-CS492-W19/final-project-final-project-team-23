package com.example.android.finalproject;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.android.finalproject.data.SingleSearchResult;
import com.example.android.finalproject.data.SingleSearchResultRepository;

import java.util.List;

public class SingleSearchResultViewModel extends AndroidViewModel {
    private SingleSearchResultRepository mSingleSearchResultRepository;

    public SingleSearchResultViewModel(Application application) {
        super(application);
        mSingleSearchResultRepository = new SingleSearchResultRepository(application);
    }

    public void insertSingleSearchResult(SingleSearchResult result) {
        mSingleSearchResultRepository.insertSingleSearchResult(result);
    }

    public void deleteSingleSearchResult(SingleSearchResult result) {
        mSingleSearchResultRepository.deleteSingleSearchResult(result);
    }

    public LiveData<List<SingleSearchResult>> getAllSearchResults() {
        return mSingleSearchResultRepository.getAllSearchResults();
    }

    public LiveData<SingleSearchResult> getSearchResultByName(String fullName) {
        return mSingleSearchResultRepository.getSearchResultByName(fullName);
    }
}
