package com.worktimepro.app

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.worktimepro.app.database.WorkTimeDatabase
import com.worktimepro.app.utils.AlarmScheduler

/**
 * BroadcastReceiver to restore alarms after device reboot
 * Reschedules the logout reminder if there's a recent calculation
 */
class BootReceiver : BroadcastReceiver() {
    
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Restore alarm for the most recent calculation
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val database = WorkTimeDatabase.getDatabase(context)
                    val latestRecord = database.workTimeDao().getLatestRecord()
                    
                    if (latestRecord != null) {
                        // Check if the logout time is still in the future
                        val logoutTimeMillis = AlarmScheduler.parseTimeToMillis(latestRecord.logoutTime)
                        val currentTimeMillis = System.currentTimeMillis()
                        
                        if (logoutTimeMillis > currentTimeMillis) {
                            // Reschedule the alarm
                            AlarmScheduler.scheduleLogoutReminder(
                                context,
                                logoutTimeMillis,
                                latestRecord.logoutTime
                            )
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}
