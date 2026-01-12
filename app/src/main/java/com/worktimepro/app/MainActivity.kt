package com.worktimepro.app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.worktimepro.app.adapter.WorkTimeHistoryAdapter
import com.worktimepro.app.database.WorkTimeDatabase
import com.worktimepro.app.database.WorkTimeRecord
import com.worktimepro.app.utils.AlarmScheduler
import com.worktimepro.app.utils.TimeCalculator
import kotlinx.coroutines.launch

/**
 * Main Activity for WorkTime Pro
 * Handles time input, calculation, and history display
 */
class MainActivity : AppCompatActivity() {
    
    // UI Components
    private lateinit var timePicker: TimePicker
    private lateinit var radioGroup: RadioGroup
    private lateinit var radio8Hours: RadioButton
    private lateinit var radio9Hours: RadioButton
    private lateinit var etBreakMinutes: EditText
    private lateinit var etBreakSeconds: EditText
    private lateinit var btnCalculate: Button
    private lateinit var btnShare: Button
    private lateinit var tvLogoutTime: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvEmptyHistory: TextView
    
    // Database
    private lateinit var database: WorkTimeDatabase
    private lateinit var adapter: WorkTimeHistoryAdapter
    
    // Current calculation result
    private var currentLogoutTime: String = ""
    
    companion object {
        private const val NOTIFICATION_PERMISSION_CODE = 100
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Initialize database
        database = WorkTimeDatabase.getDatabase(this)
        
        // Initialize UI components
        initializeViews()
        
        // Setup RecyclerView
        setupRecyclerView()
        
        // Request notification permission (Android 13+)
        requestNotificationPermission()
        
        // Set up click listeners
        setupClickListeners()
        
        // Observe database changes
        observeHistory()
    }
    
    /**
     * Initialize all UI components
     */
    private fun initializeViews() {
        timePicker = findViewById(R.id.timePicker)
        radioGroup = findViewById(R.id.radioGroupWorkHours)
        radio8Hours = findViewById(R.id.radio8Hours)
        radio9Hours = findViewById(R.id.radio9Hours)
        etBreakMinutes = findViewById(R.id.etBreakMinutes)
        etBreakSeconds = findViewById(R.id.etBreakSeconds)
        btnCalculate = findViewById(R.id.btnCalculate)
        btnShare = findViewById(R.id.btnShare)
        tvLogoutTime = findViewById(R.id.tvLogoutTime)
        recyclerView = findViewById(R.id.recyclerViewHistory)
        tvEmptyHistory = findViewById(R.id.tvEmptyHistory)
        
        // Set TimePicker to 24-hour format
        timePicker.setIs24HourView(true)
        
        // Set default values
        radio8Hours.isChecked = true
        etBreakMinutes.setText("0")
        etBreakSeconds.setText("0")
        
        // Initially disable share button
        btnShare.isEnabled = false
    }
    
    /**
     * Setup RecyclerView for history display
     */
    private fun setupRecyclerView() {
        adapter = WorkTimeHistoryAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }
    
    /**
     * Setup click listeners for buttons
     */
    private fun setupClickListeners() {
        btnCalculate.setOnClickListener {
            calculateLogoutTime()
        }
        
        btnShare.setOnClickListener {
            shareLogoutTime()
        }
    }
    
    /**
     * Observe history changes from database
     */
    private fun observeHistory() {
        database.workTimeDao().getAllRecords().observe(this) { records ->
            if (records.isEmpty()) {
                recyclerView.visibility = RecyclerView.GONE
                tvEmptyHistory.visibility = TextView.VISIBLE
            } else {
                recyclerView.visibility = RecyclerView.VISIBLE
                tvEmptyHistory.visibility = TextView.GONE
                adapter.submitList(records)
            }
        }
    }
    
    /**
     * Calculate logout time based on user inputs
     */
    private fun calculateLogoutTime() {
        // Validate inputs
        val breakMinutesStr = etBreakMinutes.text.toString()
        val breakSecondsStr = etBreakSeconds.text.toString()
        
        if (breakMinutesStr.isEmpty() || breakSecondsStr.isEmpty()) {
            Toast.makeText(this, "Please enter break duration", Toast.LENGTH_SHORT).show()
            return
        }
        
        val breakMinutes = breakMinutesStr.toIntOrNull() ?: 0
        val breakSeconds = breakSecondsStr.toIntOrNull() ?: 0
        
        if (breakMinutes < 0 || breakSeconds < 0 || breakSeconds >= 60) {
            Toast.makeText(this, "Invalid break duration", Toast.LENGTH_SHORT).show()
            return
        }
        
        // Get selected work hours
        val workHours = if (radio8Hours.isChecked) 8 else 9
        
        // Get in-time from TimePicker
        val inHour = timePicker.hour
        val inMinute = timePicker.minute
        
        // Calculate logout time
        currentLogoutTime = TimeCalculator.calculateLogoutTime(
            inHour, inMinute, workHours, breakMinutes, breakSeconds
        )
        
        // Display result
        tvLogoutTime.text = currentLogoutTime
        tvLogoutTime.visibility = TextView.VISIBLE
        btnShare.isEnabled = true
        
        // Save to database
        saveToDatabase(inHour, inMinute, workHours, breakMinutes, breakSeconds)
        
        // Schedule notification
        scheduleNotification(inHour, inMinute, workHours, breakMinutes, breakSeconds)
        
        Toast.makeText(this, "Logout time calculated!", Toast.LENGTH_SHORT).show()
    }
    
    /**
     * Save calculation to database
     */
    private fun saveToDatabase(
        inHour: Int,
        inMinute: Int,
        workHours: Int,
        breakMinutes: Int,
        breakSeconds: Int
    ) {
        lifecycleScope.launch {
            val record = WorkTimeRecord(
                date = TimeCalculator.getCurrentDate(),
                inTime = TimeCalculator.formatInTime(inHour, inMinute),
                breakDurationSeconds = TimeCalculator.calculateBreakSeconds(breakMinutes, breakSeconds),
                workHours = workHours,
                logoutTime = TimeCalculator.calculateLogoutTime24Hour(
                    inHour, inMinute, workHours, breakMinutes, breakSeconds
                )
            )
            
            database.workTimeDao().insert(record)
        }
    }
    
    /**
     * Schedule logout reminder notification
     */
    private fun scheduleNotification(
        inHour: Int,
        inMinute: Int,
        workHours: Int,
        breakMinutes: Int,
        breakSeconds: Int
    ) {
        val logoutTime24Hour = TimeCalculator.calculateLogoutTime24Hour(
            inHour, inMinute, workHours, breakMinutes, breakSeconds
        )
        
        val logoutTimeMillis = AlarmScheduler.parseTimeToMillis(logoutTime24Hour)
        
        AlarmScheduler.scheduleLogoutReminder(
            this,
            logoutTimeMillis,
            currentLogoutTime
        )
    }
    
    /**
     * Share logout time using Android share intent
     */
    private fun shareLogoutTime() {
        if (currentLogoutTime.isEmpty()) {
            Toast.makeText(this, "No logout time to share", Toast.LENGTH_SHORT).show()
            return
        }
        
        val shareText = "Today's logout time is $currentLogoutTime"
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        
        startActivity(Intent.createChooser(shareIntent, "Share logout time via"))
    }
    
    /**
     * Request notification permission for Android 13+
     */
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_CODE
                )
            }
        }
    }
    
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        
        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Notification permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    this,
                    "Notification permission denied. You won't receive logout reminders.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
