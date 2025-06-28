package com.mehmaancoders.planme.data

data class PlanRequest(
    val goals: List<String>,
    val free_time_blocks: List<String>,
    val mood: String
)

data class Task(
    val title: String,
    val start_time: String?,
    val end_time: String?
)

data class PlanResponse(
    val plan: List<Task>
)
