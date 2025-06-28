package com.mehmaancoders.planme.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MoodRequest(
    val user_id: String,
    val mood: String,
    val timestamp: String
)
