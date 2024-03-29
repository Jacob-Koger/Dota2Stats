package com.jacob.dota2.dota2Application.clients;


import android.content.Context;
import android.support.v4.app.Fragment;

import com.jacob.dota2.dota2Application.CacheStrategy;
import com.jacob.dota2.dota2Application.Config;
import com.jacob.dota2.dota2Application.ResponseCallbacks.CallbackWrapper;
import com.jacob.dota2.dota2Application.ResponseCallbacks.GenericCallback;
import com.jacob.dota2.dota2Application.data.accountinfo.AccountInfo;
import com.jacob.dota2.dota2Application.interceptors.AccountDetailsInterceptor;
import com.jacob.dota2.dota2Application.interceptors.CachedInterceptor;
import com.jacob.dota2.dota2Application.services.AccountDetailsService;

import java.io.File;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoggedInDetailsClient {

    private static final String CACHE_DIR = "HttpResponseCache";
    private static final long CACHE_SIZE = 10 * 1024 * 1024;
    private static volatile LoggedInDetailsClient aClient;
    private final CachedInterceptor mCacheceptor = new CachedInterceptor();
    private Retrofit mService;

    private LoggedInDetailsClient(Context context) {
        final Interceptor mDetailsceptor = new AccountDetailsInterceptor(context);

        final OkHttpClient client = new OkHttpClient.Builder()
                .cache(new Cache(new File(context.getCacheDir(), CACHE_DIR), CACHE_SIZE))
                .addInterceptor(mCacheceptor)
                .addInterceptor(mDetailsceptor)
                .addNetworkInterceptor(mCacheceptor)
                .build();
        mService = new Retrofit.Builder()
                .client(client)
                .validateEagerly(true)
                .baseUrl(Config.STEAM_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized LoggedInDetailsClient with(Context context) {
        if (aClient == null) {
            aClient = new LoggedInDetailsClient(context.getApplicationContext());
        }
        return aClient;
    }

    public static synchronized LoggedInDetailsClient with(Fragment fragment) {
        return with(fragment.getContext());
    }

    public LoggedInDetailsClient cacheStrategy(CacheStrategy cacheStrategy) {
        mCacheceptor.setCacheStrategy(cacheStrategy);
        return this;
    }

    public void enqueueMatchHistory(GenericCallback<AccountInfo> callback) {
        mService.create(AccountDetailsService.class)
                .getResponse()
                .enqueue(CallbackWrapper.wrap(callback));
    }

}