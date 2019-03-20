package com.example.android.finalproject;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
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
    private TextView mRepoNameTV;
    private TextView mRepoStarsTV;
    private TextView mRepoDescriptionTV;
    private ImageView mRepoBookmarkIV;

    private SingleSearchResultViewModel mGitHubRepoViewModel;
    private SingleSearchResult mRepo;
    private boolean mIsSaved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_detail);

        mRepoNameTV = findViewById(R.id.tv_repo_name);
        mRepoStarsTV = findViewById(R.id.tv_repo_stars);
        mRepoDescriptionTV = findViewById(R.id.tv_repo_description);
        mRepoBookmarkIV = findViewById(R.id.iv_repo_bookmark);

        mGitHubRepoViewModel = ViewModelProviders.of(this).get(SingleSearchResultViewModel.class);

        mRepo = null;
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(AnimeUtils.EXTRA_GITHUB_REPO)) {
            mRepo = (SingleSearchResult) intent.getSerializableExtra(AnimeUtils.EXTRA_GITHUB_REPO);
            mRepoNameTV.setText(mRepo.full_name);
            mRepoStarsTV.setText("" + mRepo.stargazers_count);
            mRepoDescriptionTV.setText(mRepo.description);

            mGitHubRepoViewModel.getGitHubRepoByName(mRepo.full_name).observe(this, new Observer<SingleSearchResult>() {
                @Override
                public void onChanged(@Nullable SingleSearchResult repo) {
                    if (repo != null) {
                        mIsSaved = true;
                        mRepoBookmarkIV.setImageResource(R.drawable.ic_bookmark_black_24dp);
                    } else {
                        mIsSaved = false;
                        mRepoBookmarkIV.setImageResource(R.drawable.ic_bookmark_border_black_24dp);
                    }
                }
            });
        }

        mRepoBookmarkIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRepo != null) {
                    if (!mIsSaved) {
                        mGitHubRepoViewModel.insertGitHubRepo(mRepo);
                    } else {
                        mGitHubRepoViewModel.deleteGitHubRepo(mRepo);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.repo_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_view_on_web:
                viewRepoOnWeb();
                return true;
            case R.id.action_share:
                shareRepo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void viewRepoOnWeb() {
        if (mRepo != null) {
            Uri repoURI = Uri.parse(mRepo.html_url);
            Intent intent = new Intent(Intent.ACTION_VIEW, repoURI);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    public void shareRepo() {
        if (mRepo != null) {
            String shareText = getString(R.string.share_repo_text, mRepo.full_name, mRepo.html_url);
            ShareCompat.IntentBuilder.from(this)
                    .setType("text/plain")
                    .setText(shareText)
                    .setChooserTitle(R.string.share_chooser_title)
                    .startChooser();
        }
    }
}
