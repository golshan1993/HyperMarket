package com.example.hypermarket.retrofit;

import java.util.HashMap;
import java.util.Map;

public class NetworkParams {


    public static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/products/";
    public static final Map<String, String> PRODUCTS_VIEW = new HashMap<String, String>(){{
        put("consumer_key", "ck_f025265e3479f7bee8e93bffe5685517b93ec27d");
        put("consumer_secret", "cs_27b19e572ac9cf1333d4d53f7082a15e9fb6a2b0");
    }};
    public static final Map<String, String> ORDER_BY_LATEST = new HashMap<String, String>(){{
        put("consumer_key", "ck_f025265e3479f7bee8e93bffe5685517b93ec27d");
        put("consumer_secret", "cs_27b19e572ac9cf1333d4d53f7082a15e9fb6a2b0");
        put("orderby", "date");
    }};
    public static final Map<String, String> ORDER_BY_POPULARITY = new HashMap<String, String>(){{
        put("consumer_key", "ck_f025265e3479f7bee8e93bffe5685517b93ec27d");
        put("consumer_secret", "cs_27b19e572ac9cf1333d4d53f7082a15e9fb6a2b0");
        put("orderby", "popularity");
    }};
    public static final Map<String, String> ORDER_BY_RATING = new HashMap<String, String>(){{
        put("consumer_key", "ck_f025265e3479f7bee8e93bffe5685517b93ec27d");
        put("consumer_secret", "cs_27b19e572ac9cf1333d4d53f7082a15e9fb6a2b0");
        put("orderby", "rating");
    }};

    public static Map<String, String> getSearchOptions(String query) {
        Map<String, String> searchOptions = new HashMap<>();
        searchOptions.put("consumer_key", "ck_f025265e3479f7bee8e93bffe5685517b93ec27d");
        searchOptions.put("consumer_secret", "cs_27b19e572ac9cf1333d4d53f7082a15e9fb6a2b0");
        searchOptions.put("search", query);
        return searchOptions;
    }
}
