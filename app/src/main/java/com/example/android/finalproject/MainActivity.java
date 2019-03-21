package com.example.android.finalproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.finalproject.data.SingleSearchResult;
import com.example.android.finalproject.utils.AnimeUtils;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements AnimeSearchAdapter.OnSearchItemClickListener, LoaderManager.LoaderCallbacks<String>,
            NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String RESULTS_ARRAY_KEY = "searchResultsList";
    private static final String SEARCH_URL_KEY = "SearchURL";

    private static final int SEARCH_LOADER_ID = 0;

    private RecyclerView mSearchResultsRV;
    private EditText mSearchBoxET;
    private TextView mLoadingErrorTV;
    private ProgressBar mLoadingPB;
    private DrawerLayout mDrawerLayout;

    private AnimeSearchAdapter mAnimeSearchAdapter;
    private ArrayList<SingleSearchResult> mResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxET = findViewById(R.id.et_search_box);
        mSearchResultsRV = findViewById(R.id.rv_search_results);
        mLoadingErrorTV = findViewById(R.id.tv_loading_error);
        mLoadingPB = findViewById(R.id.pb_loading);
        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nv_nav_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_nav_menu);

        mSearchResultsRV.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultsRV.setHasFixedSize(true);

        mAnimeSearchAdapter = new AnimeSearchAdapter(this);
        mSearchResultsRV.setAdapter(mAnimeSearchAdapter);

        if (savedInstanceState != null && savedInstanceState.containsKey(RESULTS_ARRAY_KEY)) {
            mResults = (ArrayList<SingleSearchResult>) savedInstanceState.getSerializable(RESULTS_ARRAY_KEY);
            mAnimeSearchAdapter.updateSearchResults(mResults);
        }

        getSupportLoaderManager().initLoader(SEARCH_LOADER_ID, null, this);

        Button searchButton = findViewById(R.id.btn_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = mSearchBoxET.getText().toString();
                if (!TextUtils.isEmpty(searchQuery)) {
                    doAnimeSearch(searchQuery);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(Gravity.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void doAnimeSearch(String query) {
        String url = AnimeUtils.buildSearchURL(query);
        Log.d(TAG, "querying search URL: " + url);

        Bundle args = new Bundle();
        args.putString(SEARCH_URL_KEY, url);
        mLoadingPB.setVisibility(View.VISIBLE);
        getSupportLoaderManager().restartLoader(SEARCH_LOADER_ID, args, this);
    }

    @Override
    public void onSearchItemClick(SingleSearchResult result) {
        Intent intent = new Intent(this, AnimeDetailActivity.class);
        intent.putExtra(AnimeUtils.EXTRA_SEARCH_RESULT, result);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mResults != null) {
            outState.putSerializable(RESULTS_ARRAY_KEY, mResults);
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        String url = null;
        if (bundle != null) {
            url = bundle.getString(SEARCH_URL_KEY);
        }
        return new AnimeSearchLoader(this, url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        Log.d(TAG, "loader finished loading");
        if (s != null) {
            mLoadingErrorTV.setVisibility(View.INVISIBLE);
            mSearchResultsRV.setVisibility(View.VISIBLE);
            //Log.d(TAG, "xml results:" + s);
            mResults = AnimeUtils.parseAnimeSearchResults(s);
            mAnimeSearchAdapter.updateSearchResults(mResults);
        } else {
            mLoadingErrorTV.setVisibility(View.VISIBLE);
            mSearchResultsRV.setVisibility(View.INVISIBLE);
        }
        mLoadingPB.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // Nothing to do here...
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        mDrawerLayout.closeDrawers();
        switch (menuItem.getItemId()) {
            case R.id.nav_search:
                return true;
            case R.id.nav_saved_series:
                Intent savedResultsIntent = new Intent(this, SavedResultsActivity.class);
                startActivity(savedResultsIntent);
                return true;
            default:
                return false;
        }
    }
}
