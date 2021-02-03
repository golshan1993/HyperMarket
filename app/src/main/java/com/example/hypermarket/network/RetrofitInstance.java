package com.example.hypermarket.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit retrofitInstance;
    private static final String BASE_URL = "https://woocommerce.maktabsharif.ir/wp-json/wc/v3/";
    public static final String USER_NAME = "ck_120a89c914da239359b2683859fb36ce3c94fc0a";
    public static final String PASSWORD = "cs_0dabb4ea47c464969eaad199a30370b9e7cb7e7b";
    public static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczpcL1wvd29vY29tbWVyY2UubWFrdGFic2hhcmlmLmlyIiwiaWF0IjoxNTc1Nzg2MjI2LCJuYmYiOjE1NzU3ODYyMjYsImV4cCI6MTU3NjM5MTAyNiwiZGF0YSI6eyJ1c2VyIjp7ImlkIjoiMTEifX19.W35eIyG9slS-PBx_6V3TyzE0LREWpPJZKXHhJEvl6gk" ;

    public static Retrofit getRetrofit() {
        if (retrofitInstance == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new BasicAuthInterceptor(USER_NAME, PASSWORD))
                    .build();

            retrofitInstance = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }

        return retrofitInstance;
    }



    private static class BasicAuthInterceptor implements Interceptor {

        private String consumerKey , consumerSecret;

        public BasicAuthInterceptor(String consumerKey, String consumerSecret) {
            this.consumerKey = consumerKey;
            this.consumerSecret = consumerSecret;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            HttpUrl url = request.url().newBuilder()
                    .addQueryParameter("consumer_key",consumerKey)
                    .addQueryParameter("consumer_secret" , consumerSecret)
                    .build();
            request = request.newBuilder().url(url).build();
            return chain.proceed(request);
        }

    }




}
