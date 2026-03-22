package com.example.priorityringer

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.app.NotificationManager
import android.telephony.TelephonyManager

class CallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
        val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

        if (state == TelephonyManager.EXTRA_STATE_RINGING && incomingNumber != null) {
            val prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
            val priorityNumber = prefs.getString("priority_num", "") ?: ""

            if (priorityNumber.isNotEmpty() && incomingNumber.contains(priorityNumber)) {
                val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
                val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                if (nm.isNotificationPolicyAccessGranted) {
                    audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
                    val maxVol = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)
                    audioManager.setStreamVolume(AudioManager.STREAM_RING, maxVol, 0)
                }
            }
        }
    }
}
