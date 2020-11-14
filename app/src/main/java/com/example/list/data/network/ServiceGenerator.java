package com.example.list.data.network;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Credentials;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static final long CONNECT_TIMEOUT_IN_SECONDS = 30L;

    private static final long WRITE_TIMEOUT_IN_SECONDS = 50L;

    private static final long READ_TIMEOUT_IN_SECONDS = 40L;

    private final static OkHttpClient.Builder httpClient;

    static {


        httpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
    }

    //Retrofit2 OkHttp Url
    public static <T> T createService(Class<T> serviceClass, HttpUrl url, String imei) {
        String authToken = Credentials.basic(imei, "fv@123");
        return createAuthenticatedService(serviceClass, url, authToken);
    }

    //Retrofit2 OkHttp Url
    private static <T> T createAuthenticatedService(Class<T> serviceClass, HttpUrl url, String authToken) {
        if (!TextUtils.isEmpty(authToken)) {
            AuthenticationInterceptor interceptor = new AuthenticationInterceptor(authToken);
            if (!httpClient.interceptors().contains(interceptor)) {
                httpClient.addInterceptor(interceptor);
            }
        }
        return createService(serviceClass, url);
    }

    //Retrofit2 OkHttp Url
    public static <T> T createService(Class<T> serviceClass, HttpUrl url) {
        Gson gson = getGsonInstance();
        return new Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(serviceClass);
    }

    public static Gson getGsonInstance(){
        return new GsonBuilder().create();
    }
}
