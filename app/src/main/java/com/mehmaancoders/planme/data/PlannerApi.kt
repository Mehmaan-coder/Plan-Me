package com.mehmaancoders.planme.data

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PlannerApi {
    @POST("generate_plan")
    suspend fun generatePlan(@Body req: PlanRequest): Response<PlanResponse>
}