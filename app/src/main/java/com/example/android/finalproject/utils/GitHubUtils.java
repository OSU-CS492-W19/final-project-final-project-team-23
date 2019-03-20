package com.example.android.finalproject.utils;

import android.net.Uri;

import com.example.android.finalproject.data.GitHubRepo;
import com.google.gson.Gson;

import java.util.ArrayList;

public class GitHubUtils {
    private final static String API_BASE_URL = "https://cdn.animenewsnetwork.com/encyclopedia/api.xml";
    private final static String API_TITLE_QUERY_PARAM = "title";

    public static final String EXTRA_GITHUB_REPO = "GitHubUtils.GitHubRepo";

    public static class GitHubSearchResults {
        public ArrayList<GitHubRepo> items;
    }

    public static String buildGitHubSearchURL(String query) {
        return Uri.parse(API_BASE_URL).buildUpon()
                .appendQueryParameter(API_TITLE_QUERY_PARAM, query)
                .build()
                .toString();
    }

    public static ArrayList<GitHubRepo> parseGitHubSearchResults(String json) {
        Gson gson = new Gson();
        GitHubSearchResults results = gson.fromJson(json, GitHubSearchResults.class);
        if (results != null && results.items != null) {
            return results.items;
        } else {
            return null;
        }
    }
}
