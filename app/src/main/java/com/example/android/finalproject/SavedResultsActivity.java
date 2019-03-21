package com.example.android.finalproject;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.finalproject.data.SingleSearchResult;
import com.example.android.finalproject.utils.AnimeUtils;

import java.util.List;

public class SavedResultsActivity extends AppCompatActivity implements AnimeSearchAdapter.OnSearchItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_search_results);

        RecyclerView savedSeriesRV = findViewById(R.id.rv_saved_results);
        savedSeriesRV.setLayoutManager(new LinearLayoutManager(this));
        savedSeriesRV.setHasFixedSize(true);

        final AnimeSearchAdapter adapter = new AnimeSearchAdapter(this);
        savedSeriesRV.setAdapter(adapter);

        SingleSearchResultViewModel viewModel = ViewModelProviders.of(this).get(SingleSearchResultViewModel.class);
        viewModel.getAllSearchResults().observe(this, new Observer<List<SingleSearchResult>>() {
            @Override
            public void onChanged(@Nullable List<SingleSearchResult> singleSearchResults) {
                adapter.updateSearchResults(singleSearchResults);
            }
        });
    }

    @Override
    public void onSearchItemClick(SingleSearchResult result) {
        Intent intent = new Intent(this, AnimeDetailActivity.class);
        intent.putExtra(AnimeUtils.EXTRA_SEARCH_RESULT, result);
        startActivity(intent);
    }
}
