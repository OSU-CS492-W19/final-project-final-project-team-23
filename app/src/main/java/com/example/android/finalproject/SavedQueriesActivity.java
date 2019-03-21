package com.example.android.finalproject;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.finalproject.data.ASearchQuery;
import com.example.android.finalproject.utils.AnimeUtils;

import java.util.List;

public class SavedQueriesActivity extends AppCompatActivity implements AnimeQueryAdapter.OnQueryClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_search_history);

        RecyclerView savedQueriesRV = findViewById(R.id.rv_saved_queries);
        savedQueriesRV.setLayoutManager(new LinearLayoutManager(this));
        savedQueriesRV.setHasFixedSize(true);

        final AnimeQueryAdapter adapter = new AnimeQueryAdapter(this);
        savedQueriesRV.setAdapter(adapter);

        ASearchQueryViewModel viewModel = ViewModelProviders.of(this).get(ASearchQueryViewModel.class);
        viewModel.getAllSearchQueries().observe(this, new Observer<List<ASearchQuery>>() {
            @Override
            public void onChanged(@Nullable List<ASearchQuery> aSearchQuery) {
                adapter.updateQueryList(aSearchQuery);
            }
        });
    }

    @Override
    public void onQueryClick(ASearchQuery query) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(AnimeUtils.EXTRA_SEARCH_QUERY, query);
        startActivity(intent);
    }
}
