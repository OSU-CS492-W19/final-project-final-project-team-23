package com.example.android.finalproject.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class SingleSearchResultRepository {
    private SingleSearchResultDao mSingleSearchResultDao;

    public SingleSearchResultRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mSingleSearchResultDao = db.gitHubRepoDao();
    }

    public void insertGitHubRepo(SingleSearchResult repo) {
        new InsertAsyncTask(mSingleSearchResultDao).execute(repo);
    }

    public void deleteGitHubRepo(SingleSearchResult repo) {
        new DeleteAsyncTask(mSingleSearchResultDao).execute(repo);
    }

    public LiveData<List<SingleSearchResult>> getAllGitHubRepos() {
        return mSingleSearchResultDao.getAllRepos();
    }

    public LiveData<SingleSearchResult> getGitHubRepoByName(String fullName) {
        return mSingleSearchResultDao.getRepoByName(fullName);
    }

    private static class InsertAsyncTask extends AsyncTask<SingleSearchResult, Void, Void> {
        private SingleSearchResultDao mAsyncTaskDao;
        InsertAsyncTask(SingleSearchResultDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(SingleSearchResult... singleSearchResults) {
            mAsyncTaskDao.insert(singleSearchResults[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<SingleSearchResult, Void, Void> {
        private SingleSearchResultDao mAsyncTaskDao;
        DeleteAsyncTask(SingleSearchResultDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(SingleSearchResult... singleSearchResults) {
            mAsyncTaskDao.delete(singleSearchResults[0]);
            return null;
        }
    }
}
