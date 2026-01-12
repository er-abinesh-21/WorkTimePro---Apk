package com.worktimepro.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.worktimepro.app.R
import com.worktimepro.app.database.WorkTimeRecord

/**
 * RecyclerView Adapter for displaying work time history
 * Uses ListAdapter with DiffUtil for efficient updates
 */
class WorkTimeHistoryAdapter : ListAdapter<WorkTimeRecord, WorkTimeHistoryAdapter.ViewHolder>(DiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_work_time_history, parent, false)
        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    /**
     * ViewHolder for history items
     */
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateTextView: TextView = itemView.findViewById(R.id.tvDate)
        private val inTimeTextView: TextView = itemView.findViewById(R.id.tvInTime)
        private val workHoursTextView: TextView = itemView.findViewById(R.id.tvWorkHours)
        private val breakDurationTextView: TextView = itemView.findViewById(R.id.tvBreakDuration)
        private val logoutTimeTextView: TextView = itemView.findViewById(R.id.tvLogoutTime)
        
        fun bind(record: WorkTimeRecord) {
            dateTextView.text = formatDate(record.date)
            inTimeTextView.text = "In: ${formatTime(record.inTime)}"
            workHoursTextView.text = "${record.workHours}h"
            breakDurationTextView.text = "Break: ${formatBreakDuration(record.breakDurationSeconds)}"
            logoutTimeTextView.text = "Out: ${formatTime(record.logoutTime)}"
        }
        
        /**
         * Format date from yyyy-MM-dd to more readable format
         */
        private fun formatDate(date: String): String {
            return try {
                val parts = date.split("-")
                if (parts.size == 3) {
                    "${parts[2]}/${parts[1]}/${parts[0]}"
                } else {
                    date
                }
            } catch (e: Exception) {
                date
            }
        }
        
        /**
         * Format time from HH:mm:ss to 12-hour format with AM/PM
         */
        private fun formatTime(time: String): String {
            return try {
                val parts = time.split(":")
                if (parts.size >= 2) {
                    val hour = parts[0].toInt()
                    val minute = parts[1]
                    val second = if (parts.size > 2) parts[2] else "00"
                    val amPm = if (hour >= 12) "PM" else "AM"
                    val displayHour = when {
                        hour == 0 -> 12
                        hour > 12 -> hour - 12
                        else -> hour
                    }
                    String.format("%02d:%s:%s %s", displayHour, minute, second, amPm)
                } else {
                    time
                }
            } catch (e: Exception) {
                time
            }
        }
        
        /**
         * Format break duration from seconds to readable format
         */
        private fun formatBreakDuration(seconds: Int): String {
            val minutes = seconds / 60
            val secs = seconds % 60
            return if (minutes > 0) {
                "${minutes}m ${secs}s"
            } else {
                "${secs}s"
            }
        }
    }
    
    /**
     * DiffUtil callback for efficient list updates
     */
    class DiffCallback : DiffUtil.ItemCallback<WorkTimeRecord>() {
        override fun areItemsTheSame(oldItem: WorkTimeRecord, newItem: WorkTimeRecord): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: WorkTimeRecord, newItem: WorkTimeRecord): Boolean {
            return oldItem == newItem
        }
    }
}
