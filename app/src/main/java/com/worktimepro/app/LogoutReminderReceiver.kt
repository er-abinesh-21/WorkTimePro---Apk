package com.worktimepro.app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

/**
 * BroadcastReceiver for handling logout reminder notifications
 * Triggered by AlarmManager 10 minutes before logout time
 */
class LogoutReminderReceiver : BroadcastReceiver() {
    
    companion object {
        const val CHANNEL_ID = "logout_reminder_channel"
        const val NOTIFICATION_ID = 1001
        const val EXTRA_LOGOUT_TIME = "logout_time"
    }
    
    override fun onReceive(context: Context, intent: Intent) {
        val logoutTime = intent.getStringExtra(EXTRA_LOGOUT_TIME) ?: "Unknown"
        
        // Create notification channel (required for Android 8.0+)
        createNotificationChannel(context)
        
        // Build notification
        val notification = buildNotification(context, logoutTime)
        
        // Show notification
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }
    
    /**
     * Create notification channel for Android 8.0+
     */
    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Logout Reminder"
            val descriptionText = "Reminds you 10 minutes before logout time"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                enableVibration(true)
                enableLights(true)
            }
            
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    /**
     * Build the notification with logout time information
     */
    private fun buildNotification(context: Context, logoutTime: String): NotificationCompat.Builder {
        // Intent to open app when notification is tapped
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("⏰ Logout Reminder")
            .setContentText("Your logout time is $logoutTime (in 10 minutes)")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("Your logout time is $logoutTime. You have 10 minutes remaining to complete your work day."))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setVibrate(longArrayOf(0, 500, 200, 500))
    }
}
