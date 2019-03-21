package com.example.android.finalproject.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class ASearchQueryRepository {
    private ASearchQueryDao mASearchQueryDao;

    public ASearchQueryRepository(Application app){
        AppDatabase db = AppDatabase.getDatabase(app);
        mASearchQueryDao = db.aSearchQueryDao();
    }

    public void insertASearchQuery(ASearchQuery query) {
        new InsertAsyncTask(mASearchQueryDao).execute(query);
    }

    public void deleteASearchQuery(ASearchQuery query) {
        new DeleteAsyncTask(mASearchQueryDao).execute(query);
    }

    public LiveData<List<ASearchQuery>> getAllSearchResults() {
        return mASearchQueryDao.getAllSearchQueries();
    }


    private static class InsertAsyncTask extends AsyncTask<ASearchQuery, Void, Void> {
        private ASearchQueryDao mAsyncTaskDao;
        InsertAsyncTask(ASearchQueryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ASearchQuery... aSearchQueries) {
            mAsyncTaskDao.insert(aSearchQueries[0]);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<ASearchQuery, Void, Void> {
        private ASearchQueryDao mAsyncTaskDao;
        DeleteAsyncTask(ASearchQueryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(ASearchQuery... aSearchQueries) {
            mAsyncTaskDao.delete(aSearchQueries[0]);
            return null;
        }
    }
}
