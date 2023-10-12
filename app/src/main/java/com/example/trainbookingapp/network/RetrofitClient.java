package com.example.trainbookingapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://10.0.2.2:7085/api/";

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL) // Set the API base URL.
                    .addConverterFactory(GsonConverterFactory.create()) // Use Gson parser.
                    .build();
        }
        return retrofit;
    }
}
