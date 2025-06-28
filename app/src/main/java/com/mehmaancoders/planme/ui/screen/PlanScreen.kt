package com.mehmaancoders.planme.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mehmaancoders.planme.data.Task

@Composable
fun PlanScreen(viewModel: PlanViewModel = viewModel()) {
    val tasks by viewModel.plan.observeAsState(emptyList())
    val err by viewModel.error.observeAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Button(onClick = {
            viewModel.generate(
                listOf("Read a chapter", "Evening walk"),
                listOf("2025-07-10T08:00", "2025-07-10T18:00"),
                "relaxed"
            )
        }) {
            Text("Generate Plan")
        }

        err?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Error: $it", color = MaterialTheme.colors.error)
        }

        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(tasks.size) { index ->
                val t: Task = tasks[index]
                Text("${t.title}: ${t.start_time} → ${t.end_time}")
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
