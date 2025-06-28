package com.mehmaancoders.planme.presentation.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.*
import com.mehmaancoders.planme.data.model.NotificationItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NotificationViewModel : ViewModel() {

    private val dbRef = FirebaseDatabase.getInstance().getReference("notifications")
    private val _notifications = MutableStateFlow<List<NotificationItem>>(emptyList())
    val notifications: StateFlow<List<NotificationItem>> = _notifications

    init {
        fetchNotifications()
    }

    private fun fetchNotifications() {
        dbRef.orderByChild("timestamp").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<NotificationItem>()
                for (child in snapshot.children) {
                    child.getValue(NotificationItem::class.java)?.let {
                        list.add(it)
                    }
                }
                _notifications.value = list.reversed()
            }

            override fun onCancelled(error: DatabaseError) {
                // handle error if needed
            }
        })
    }
}
