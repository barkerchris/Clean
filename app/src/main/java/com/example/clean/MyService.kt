package com.example.clean

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.JobIntentService

//This service sends out a broadcast every hour to check to see if there are any new articles
class MyService : JobIntentService() {
    private val TAG = "MyService"

    //Starts the work
    fun startWork(context: Context, intent: Intent) {
        enqueueWork(context, MyService::class.java, 123, intent)
    }

    //This actually does the work
    override fun onHandleWork(intent: Intent) {
        Log.d(TAG, "onHandleWork")
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, NotificationBroadcast::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        //Every hour we will send a broadcast out
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), AlarmManager.INTERVAL_HOUR, pendingIntent)
    }

}