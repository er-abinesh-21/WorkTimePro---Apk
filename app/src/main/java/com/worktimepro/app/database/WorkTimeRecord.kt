package com.worktimepro.app.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity class representing a work time calculation record
 * Stored in Room database for history tracking
 */
@Entity(tableName = "work_time_history")
data class WorkTimeRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    // Date of the calculation (format: yyyy-MM-dd)
    val date: String,
    
    // Office in-time (format: HH:mm:ss)
    val inTime: String,
    
    // Break duration in seconds
    val breakDurationSeconds: Int,
    
    // Selected work hours (8 or 9)
    val workHours: Int,
    
    // Calculated logout time (format: HH:mm:ss)
    val logoutTime: String,
    
    // Timestamp when record was created
    val timestamp: Long = System.currentTimeMillis()
)
