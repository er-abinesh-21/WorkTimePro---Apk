package com.worktimepro.app.database

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * Data Access Object for WorkTimeRecord
 * Provides methods to interact with the database
 */
@Dao
interface WorkTimeDao {
    
    /**
     * Insert a new work time record
     * @param record The record to insert
     * @return The ID of the inserted record
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: WorkTimeRecord): Long
    
    /**
     * Get all work time records ordered by timestamp (newest first)
     * Returns LiveData for automatic UI updates
     */
    @Query("SELECT * FROM work_time_history ORDER BY timestamp DESC")
    fun getAllRecords(): LiveData<List<WorkTimeRecord>>
    
    /**
     * Get records for a specific date
     * @param date Date in format yyyy-MM-dd
     */
    @Query("SELECT * FROM work_time_history WHERE date = :date ORDER BY timestamp DESC")
    fun getRecordsByDate(date: String): LiveData<List<WorkTimeRecord>>
    
    /**
     * Delete a specific record
     * @param record The record to delete
     */
    @Delete
    suspend fun delete(record: WorkTimeRecord)
    
    /**
     * Delete all records
     */
    @Query("DELETE FROM work_time_history")
    suspend fun deleteAll()
    
    /**
     * Get the most recent record
     */
    @Query("SELECT * FROM work_time_history ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestRecord(): WorkTimeRecord?
}
