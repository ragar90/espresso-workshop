package com.codesgood.espressoworkshop.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;


public class NetworkService {

    private static BotAPI API_INSTANCE;
    private static Gson gson = new Gson();

    public static Gson getGson() {
        return gson;
    }

    /**
     * Method that returns a Singleton instance of the API
     *
     * @param context Context
     * @return {#FeedAPI}
     */
    public static BotAPI getAPI(Context context) {
        if (API_INSTANCE == null) {
            RestAdapter restAdapter = getCustomRestAdapter(context.getApplicationContext(), "http://www.personalityforge.com/", new GsonConverter(gson));
            API_INSTANCE = restAdapter.create(BotAPI.class);
        }

        return API_INSTANCE;
    }

    /**
     * Returns a RestAdapter with custom cache configurations
     *
     * @param context   Context
     * @param baseUrl   Base Url of the endpoints
     * @param converter Converter
     * @return {#RestAdapter}
     */
    public static RestAdapter getCustomRestAdapter(final Context context, String baseUrl, Converter converter) {
        //Setting up cache configurations
        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        Cache cache = null;
        try {
            cache = new Cache(httpCacheDirectory, 1024 * 1024);
        } catch (IOException e) {
            Log.w("OKHttp", "Couldn't create http cache", e);
        }

        OkHttpClient okHttpClient = new OkHttpClient();
        if (cache != null) {
            okHttpClient.setCache(cache);
        }

        //Customizing RestAdapter and adding cache configurations.
        return new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(baseUrl)
                .setClient(new OkClient(okHttpClient))
                .setConverter(converter)
                .build();
    }

    /**
     * Verifies whether the device has internet connection or not.
     *
     * @param context Context
     * @return true if connected, false otherwise.
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
