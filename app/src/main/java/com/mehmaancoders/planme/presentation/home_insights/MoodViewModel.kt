package com.mehmaancoders.planme.presentation.home_insights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehmaancoders.planme.data.model.MoodRequest
import com.mehmaancoders.planme.data.model.MoodLog
import com.mehmaancoders.planme.data.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoodViewModel : ViewModel() {

    // POST mood response state
    private val _response = MutableStateFlow<String?>(null)
    val response: StateFlow<String?> = _response

    // GET mood logs state
    private val _moodLogs = MutableStateFlow<List<MoodLog>>(emptyList())
    val moodLogs: StateFlow<List<MoodLog>> = _moodLogs

    // Send mood to backend
    fun addMood(mood: String) {
        val request = MoodRequest(
            user_id = "user_1", // Replace with Firebase Auth UID if needed
            mood = mood,
            timestamp = getCurrentDate()
        )

        viewModelScope.launch {
            try {
                val result = RetrofitClient.apiService.addMood(request)
                _response.value = result.message
            } catch (e: Exception) {
                _response.value = "Error: ${e.localizedMessage}"
            }
        }
    }

    // Fetch mood logs from backend
    fun fetchMoodLogs(userId: String) {
        viewModelScope.launch {
            try {
                _moodLogs.value = RetrofitClient.apiService.getMoods(userId)
            } catch (e: Exception) {
                println("Error fetching mood logs: ${e.localizedMessage}")
            }
        }
    }

    // Get current date string in YYYY-MM-DD
    private fun getCurrentDate(): String {
        val formatter = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        return formatter.format(java.util.Date())
    }
}
