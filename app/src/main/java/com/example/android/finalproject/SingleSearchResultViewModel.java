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

    public void insertGitHubRepo(SingleSearchResult repo) {
        mSingleSearchResultRepository.insertGitHubRepo(repo);
    }

    public void deleteGitHubRepo(SingleSearchResult repo) {
        mSingleSearchResultRepository.deleteGitHubRepo(repo);
    }

    public LiveData<List<SingleSearchResult>> getAllGitHubRepos() {
        return mSingleSearchResultRepository.getAllGitHubRepos();
    }

    public LiveData<SingleSearchResult> getGitHubRepoByName(String fullName) {
        return mSingleSearchResultRepository.getGitHubRepoByName(fullName);
    }
}
