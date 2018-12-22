package com.henallux.smartcity.Service;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceBuilder {
    // ON définit l'url de base
    private  static final String URL ="https://sc-nconnect.azurewebsites.net/api/";

    // créer le logger
    private static HttpLoggingInterceptor loffer =
            new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC);
    private static OkHttpClient.Builder okHttp =
            new OkHttpClient.Builder().addInterceptor(loffer);


    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create()) // map automatiquement les données
            .client(okHttp.build());
    private static Retrofit retrofit = builder.build();

    public  static <S> S buildService(Class<S> serviceType)
    {
        return retrofit.create(serviceType);
    }
}
