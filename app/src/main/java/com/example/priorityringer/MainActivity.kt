package com.example.priorityringer

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Simple UI via Code
        val layout = android.widget.LinearLayout(this).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(50, 50, 50, 50)
        }
        
        val input = EditText(this).apply { hint = "Enter Priority Phone Number" }
        val btn = Button(this).apply { text = "Save & Enable" }
        
        layout.addView(input)
        layout.addView(btn)
        setContentView(layout)

        val prefs = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        input.setText(prefs.getString("priority_num", ""))

        ActivityCompat.requestPermissions(this, 
            arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_CALL_LOG), 1)

        val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (!nm.isNotificationPolicyAccessGranted) {
            startActivity(Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS))
        }

        btn.setOnClickListener {
            prefs.edit().putString("priority_num", input.text.toString()).apply()
            android.widget.Toast.makeText(this, "Saved!", android.widget.Toast.LENGTH_SHORT).show()
        }
    }
}
