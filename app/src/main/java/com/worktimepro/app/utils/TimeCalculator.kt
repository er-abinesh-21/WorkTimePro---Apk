package com.worktimepro.app.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Utility class for calculating logout time
 * Handles all time-related calculations with proper overflow handling
 */
object TimeCalculator {
    
    /**
     * Calculate logout time based on in-time, work hours, and break duration
     * 
     * @param inHour Office in-time hour (0-23)
     * @param inMinute Office in-time minute (0-59)
     * @param workHours Selected work hours (8 or 9)
     * @param breakMinutes Break duration in minutes
     * @param breakSeconds Break duration in seconds
     * @return Formatted logout time string (hh:mm:ss AM/PM)
     */
    fun calculateLogoutTime(
        inHour: Int,
        inMinute: Int,
        workHours: Int,
        breakMinutes: Int,
        breakSeconds: Int
    ): String {
        // Create calendar instance with in-time
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, inHour)
            set(Calendar.MINUTE, inMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        
        // Add work hours
        calendar.add(Calendar.HOUR_OF_DAY, workHours)
        
        // Add break duration
        calendar.add(Calendar.MINUTE, breakMinutes)
        calendar.add(Calendar.SECOND, breakSeconds)
        
        // Format to 12-hour format with AM/PM
        return formatTo12Hour(calendar)
    }
    
    /**
     * Calculate logout time and return as 24-hour format (HH:mm:ss)
     * Used for database storage
     */
    fun calculateLogoutTime24Hour(
        inHour: Int,
        inMinute: Int,
        workHours: Int,
        breakMinutes: Int,
        breakSeconds: Int
    ): String {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, inHour)
            set(Calendar.MINUTE, inMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        
        calendar.add(Calendar.HOUR_OF_DAY, workHours)
        calendar.add(Calendar.MINUTE, breakMinutes)
        calendar.add(Calendar.SECOND, breakSeconds)
        
        return formatTo24Hour(calendar)
    }
    
    /**
     * Format calendar time to 12-hour format (hh:mm:ss AM/PM)
     */
    private fun formatTo12Hour(calendar: Calendar): String {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        
        val amPm = if (hour >= 12) "PM" else "AM"
        val displayHour = when {
            hour == 0 -> 12
            hour > 12 -> hour - 12
            else -> hour
        }
        
        return String.format("%02d:%02d:%02d %s", displayHour, minute, second, amPm)
    }
    
    /**
     * Format calendar time to 24-hour format (HH:mm:ss)
     */
    private fun formatTo24Hour(calendar: Calendar): String {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        
        return String.format("%02d:%02d:%02d", hour, minute, second)
    }
    
    /**
     * Get current date in yyyy-MM-dd format
     */
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }
    
    /**
     * Format in-time to 24-hour format for storage
     */
    fun formatInTime(hour: Int, minute: Int): String {
        return String.format("%02d:%02d:00", hour, minute)
    }
    
    /**
     * Convert break duration to total seconds
     */
    fun calculateBreakSeconds(minutes: Int, seconds: Int): Int {
        return (minutes * 60) + seconds
    }
}
