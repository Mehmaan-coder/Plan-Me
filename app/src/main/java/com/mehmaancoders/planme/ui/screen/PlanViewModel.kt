package com.mehmaancoders.planme.ui.screen

import androidx.lifecycle.*
import com.mehmaancoders.planme.data.PlanRequest
import com.mehmaancoders.planme.data.RetrofitClient
import com.mehmaancoders.planme.data.Task
import kotlinx.coroutines.launch

class PlanViewModel : ViewModel() {
    val plan = MutableLiveData<List<Task>>()
    val error = MutableLiveData<String?>()

    fun generate(goals: List<String>, blocks: List<String>, mood: String) {
        viewModelScope.launch {
            try {
                val res = RetrofitClient.api.generatePlan(PlanRequest(goals, blocks, mood))
                if (res.isSuccessful) plan.value = res.body()?.plan
                else error.value = "Error ${res.code()}"
            } catch (e: Exception) {
                error.value = e.message
            }
        }
    }
}
