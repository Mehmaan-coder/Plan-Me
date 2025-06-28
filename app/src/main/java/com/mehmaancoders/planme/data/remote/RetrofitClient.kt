package com.mehmaancoders.planme.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8000/" // Emulator - replace with your IP on real device
    private val contentType = "application/json".toMediaType()

    val apiService: MoodApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json { ignoreUnknownKeys = true }.asConverterFactory(contentType))
            .build()
            .create(MoodApiService::class.java)
    }
}
