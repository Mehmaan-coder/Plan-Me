package com.mehmaancoders.planme.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.EventDateTime
import java.util.*

object GoogleCalendarHelper {

    // Create Google Calendar service from signed-in account
    fun getCalendarService(context: Context, account: GoogleSignInAccount): Calendar {
        val credential = GoogleAccountCredential.usingOAuth2(
            context, listOf(com.google.api.services.calendar.CalendarScopes.CALENDAR)
        )
        credential.selectedAccount = account.account

        return Calendar.Builder(
            AndroidHttp.newCompatibleTransport(),
            GsonFactory.getDefaultInstance(),
            credential
        ).setApplicationName("PlanMe").build()
    }

    // Create a calendar event and insert it into the user's Google Calendar
    fun createCalendarEvent(
        context: Context,
        account: GoogleSignInAccount,
        title: String,
        description: String,
        startMillis: Long,
        endMillis: Long
    ) {
        val service = getCalendarService(context, account)

        val start = DateTime(startMillis)
        val end = DateTime(endMillis)

        val event = Event()
            .setSummary(title)
            .setDescription(description)
            .setStart(
                EventDateTime()
                    .setDateTime(start)
                    .setTimeZone(TimeZone.getDefault().id)
            )
            .setEnd(
                EventDateTime()
                    .setDateTime(end)
                    .setTimeZone(TimeZone.getDefault().id)
            )

        Thread {
            try {
                service.events().insert("primary", event).execute()
                (context as? Activity)?.runOnUiThread {
                    Toast.makeText(context, "Event added to Google Calendar 🎉", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                (context as? Activity)?.runOnUiThread {
                    Toast.makeText(context, "Failed to add event: ${e.message}", Toast.LENGTH_LONG).show()
                }
                e.printStackTrace()
            }
        }.start()
    }
}
