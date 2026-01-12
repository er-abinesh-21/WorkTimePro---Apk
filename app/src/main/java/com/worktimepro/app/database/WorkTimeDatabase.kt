package com.worktimepro.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Room Database class for WorkTime Pro
 * Singleton pattern ensures only one instance exists
 */
@Database(entities = [WorkTimeRecord::class], version = 1, exportSchema = false)
abstract class WorkTimeDatabase : RoomDatabase() {
    
    abstract fun workTimeDao(): WorkTimeDao
    
    companion object {
        @Volatile
        private var INSTANCE: WorkTimeDatabase? = null
        
        /**
         * Get database instance (Singleton)
         * Thread-safe implementation using double-checked locking
         */
        fun getDatabase(context: Context): WorkTimeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WorkTimeDatabase::class.java,
                    "work_time_database"
                )
                    .fallbackToDestructiveMigration() // For development; remove in production
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
