package com.example.android.finalproject;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.finalproject.data.SingleSearchResult;
import com.example.android.finalproject.utils.AnimeUtils;

public class AnimeDetailActivity extends AppCompatActivity {
    private TextView mSeriesNameTV;
    private TextView mSeriesStarsTV;
    private TextView mSeriesDescriptionTV;
    private ImageView mSeriesBookmarkIV;

    private SingleSearchResultViewModel mSingleSearchResultViewModel;
    private SingleSearchResult mSeries;
    private boolean mIsSaved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series_detail);

        mSeriesNameTV = findViewById(R.id.tv_series_name);
        mSeriesStarsTV = findViewById(R.id.tv_series_stars);
        mSeriesDescriptionTV = findViewById(R.id.tv_series_description);
        mSeriesBookmarkIV = findViewById(R.id.iv_series_bookmark);

        mSingleSearchResultViewModel = ViewModelProviders.of(this).get(SingleSearchResultViewModel.class);

        mSeries = null;
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(AnimeUtils.EXTRA_SEARCH_RESULT)) {
            mSeries = (SingleSearchResult) intent.getSerializableExtra(AnimeUtils.EXTRA_SEARCH_RESULT);
            mSeriesNameTV.setText(mSeries.full_name);
            mSeriesStarsTV.setText("" + mSeries.stargazers_count);
            mSeriesDescriptionTV.setText(mSeries.description);

            mSingleSearchResultViewModel.getSearchResultByName(mSeries.full_name).observe(this, new Observer<SingleSearchResult>() {
                @Override
                public void onChanged(@Nullable SingleSearchResult result) {
                    if (result != null) {
                        mIsSaved = true;
                        mSeriesBookmarkIV.setImageResource(R.drawable.ic_bookmark_black_24dp);
                    } else {
                        mIsSaved = false;
                        mSeriesBookmarkIV.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    }
                }
            });
        }

        mSeriesBookmarkIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSeries != null) {
                    if (!mIsSaved) {
                        mSingleSearchResultViewModel.insertSingleSearchResult(mSeries);
                    } else {
                        mSingleSearchResultViewModel.deleteSingleSearchResult(mSeries);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.series_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_view_on_web:
                viewSeriesOnWeb();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void viewSeriesOnWeb() {
        if (mSeries != null) {
            Uri seriesURI = Uri.parse(mSeries.html_url);
            Intent intent = new Intent(Intent.ACTION_VIEW, seriesURI);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

}
