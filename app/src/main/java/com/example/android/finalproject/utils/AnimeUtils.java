package com.example.finalproject;

import android.net.Uri;
import com.google.gson.Gson;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;



public class AnimeUtils {

    private final static String API_BASE_URL = "https://cdn.animenewsnetwork.com/encyclopedia/api.xml";
    private final static String API_TITLE_QUERY_PARAM = "title";
//
//    private final static String API_TITLE_FORMAT_STR = "title=%s";

    public static String buildForecastURL(String title) {

        return Uri.parse(API_BASE_URL).buildUpon()
                .appendQueryParameter(API_TITLE_QUERY_PARAM, title)
                .build()
                .toString();
    }
}
