package com.worktimepro.app.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.worktimepro.app.LogoutReminderReceiver
import java.util.*

/**
 * Utility class for scheduling logout reminder alarms
 */
object AlarmScheduler {
    
    /**
     * Schedule a logout reminder notification 10 minutes before logout time
     * @param context Application context
     * @param logoutTimeMillis Logout time in milliseconds
     * @param logoutTimeFormatted Formatted logout time string for notification
     */
    fun scheduleLogoutReminder(context: Context, logoutTimeMillis: Long, logoutTimeFormatted: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        
        // Calculate reminder time (10 minutes before logout)
        val reminderTimeMillis = logoutTimeMillis - (10 * 60 * 1000)
        
        // Only schedule if reminder time is in the future
        if (reminderTimeMillis <= System.currentTimeMillis()) {
            return
        }
        
        // Create intent for the notification receiver
        val intent = Intent(context, LogoutReminderReceiver::class.java).apply {
            action = "com.worktimepro.app.LOGOUT_REMINDER"
            putExtra(LogoutReminderReceiver.EXTRA_LOGOUT_TIME, logoutTimeFormatted)
        }
        
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        // Schedule exact alarm
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    reminderTimeMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    reminderTimeMillis,
                    pendingIntent
                )
            }
        } catch (e: SecurityException) {
            // Handle case where exact alarm permission is not granted (Android 12+)
            alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                reminderTimeMillis,
                pendingIntent
            )
        }
    }
    
    /**
     * Cancel any existing logout reminder
     */
    fun cancelLogoutReminder(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, LogoutReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }
    
    /**
     * Parse time string (HH:mm:ss) to milliseconds for today
     * @param timeString Time in format HH:mm:ss
     * @return Time in milliseconds
     */
    fun parseTimeToMillis(timeString: String): Long {
        return try {
            val parts = timeString.split(":")
            val hour = parts[0].toInt()
            val minute = parts[1].toInt()
            val second = if (parts.size > 2) parts[2].toInt() else 0
            
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, second)
                set(Calendar.MILLISECOND, 0)
            }
            
            calendar.timeInMillis
        } catch (e: Exception) {
            System.currentTimeMillis()
        }
    }
}
