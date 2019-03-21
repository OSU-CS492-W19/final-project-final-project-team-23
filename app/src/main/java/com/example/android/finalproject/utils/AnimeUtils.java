package com.example.android.finalproject.utils;

import android.net.Uri;

import com.example.android.finalproject.data.SingleSearchResult;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AnimeUtils {
    private final static String API_BASE_URL = "https://cdn.animenewsnetwork.com/encyclopedia/api.xml";
    private final static String API_TITLE_QUERY_PARAM = "title";

    public static final String EXTRA_SEARCH_RESULT = "AnimeUtils.SingleSearchResult";

    public static class AnimeSearchResults {
        public ArrayList<SingleSearchResult> items;
    }

    public static String buildSearchURL(String query) {
        return Uri.parse(API_BASE_URL).buildUpon()
                .appendQueryParameter(API_TITLE_QUERY_PARAM, query)
                .build()
                .toString();
    }

    public static ArrayList<SingleSearchResult> parseAnimeSearchResults(String xml) {
        //Gson gson = new Gson();
        //AnimeSearchResults results = gson.fromJson(json, AnimeSearchResults.class);
        TestParser.parseXML(xml);/*
        if (results != null && results.items != null) {
            return results.items;
        } else {
            return null;
        }*/
        return null;
    }
}
