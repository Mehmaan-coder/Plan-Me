package com.mehmaancoders.planme.data.remote

import com.mehmaancoders.planme.data.model.MoodRequest
import com.mehmaancoders.planme.data.model.MoodResponse
import com.mehmaancoders.planme.data.model.MoodLog
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MoodApiService {

    @POST("/add-mood/")
    suspend fun addMood(@Body moodRequest: MoodRequest): MoodResponse

    @GET("/get-moods/{user_id}")
    suspend fun getMoods(@Path("user_id") userId: String): List<MoodLog>
}
