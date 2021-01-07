package com.example.hypermarket.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ResourceBundle;

public class QueryPreferences {
    public static final String PREF_SEARCH_QUERY = "searchquery";
    public static final String PREF_IS_SEARCH = "issearch";
    public static String getPrefSearchQuery(Context context){
        return getSharedPreferences(context).getString(PREF_SEARCH_QUERY, null);
    }

    public static void setPrefSearchQuery(Context context, String query){
        getSharedPreferences(context).edit().putString(PREF_SEARCH_QUERY, query).apply();
    }

    public static void setIsSearch(Context context, boolean isSearch){
        getSharedPreferences(context).edit().putBoolean(PREF_IS_SEARCH, isSearch).apply();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }
}
