package com.example.android.finalproject.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class SingleSearchResultRepository {
    private SingleSearchResultDao mSingleSearchResultDao;

    public SingleSearchResultRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mSingleSearchResultDao = db.singleSearchResultDao();
    }

    public void insertSingleSearchResult(SingleSearchResult result) {
        new InsertAsyncTask(mSingleSearchResultDao).execute(result);
    }

    public void deleteSingleSearchResult(SingleSearchResult result) {
        new DeleteAsyncTask(mSingleSearchResultDao).execute(result);
    }

    public LiveData<List<SingleSearchResult>> getAllSearchResults() {
        return mSingleSearchResultDao.getAllSearchResults();
    }

    public LiveData<SingleSearchResult> getSearchResultByName(String fullName) {
        return mSingleSearchResultDao.getResultByName(fullName);
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
