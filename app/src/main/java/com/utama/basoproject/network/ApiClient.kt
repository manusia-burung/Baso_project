package com.utama.basoproject.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    // Menggunakan 10.0.2.2 untuk emulator, dan port 8080 sesuai screenshot phpMyAdmin Anda
    private const val BASE_URL = "http://10.0.2.2:8080/backend/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
