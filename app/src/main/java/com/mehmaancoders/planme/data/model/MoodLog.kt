package com.mehmaancoders.planme.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MoodLog(
    val date: String,
    val mood: String
)
