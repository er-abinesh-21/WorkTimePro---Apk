# WorkTime Pro - API Documentation

## 📚 Public API Reference

This document provides detailed information about the public API of WorkTime Pro components.

---

## 🧮 TimeCalculator

Utility class for time-related calculations.

### Package
```kotlin
com.worktimepro.app.utils
```

### Class Definition
```kotlin
object TimeCalculator
```

---

### Methods

#### calculateLogoutTime

Calculate logout time in 12-hour format.

**Signature:**
```kotlin
fun calculateLogoutTime(
    inHour: Int,
    inMinute: Int,
    workHours: Int,
    breakMinutes: Int,
    breakSeconds: Int
): String
```

**Parameters:**
| Name | Type | Description | Range |
|------|------|-------------|-------|
| `inHour` | Int | Office in-time hour | 0-23 |
| `inMinute` | Int | Office in-time minute | 0-59 |
| `workHours` | Int | Work duration in hours | 1-24 |
| `breakMinutes` | Int | Break minutes | 0-999 |
| `breakSeconds` | Int | Break seconds | 0-59 |

**Returns:**
- `String` - Formatted time in `hh:mm:ss AM/PM` format

**Example:**
```kotlin
val logout = TimeCalculator.calculateLogoutTime(
    inHour = 9,
    inMinute = 0,
    workHours = 8,
    breakMinutes = 30,
    breakSeconds = 0
)
// Returns: "05:30:00 PM"
```

**Calculation Logic:**
```
Logout Time = In-Time + Work Hours + Break Duration
```

---

#### calculateLogoutTime24Hour

Calculate logout time in 24-hour format (for database storage).

**Signature:**
```kotlin
fun calculateLogoutTime24Hour(
    inHour: Int,
    inMinute: Int,
    workHours: Int,
    breakMinutes: Int,
    breakSeconds: Int
): String
```

**Parameters:**
Same as `calculateLogoutTime()`

**Returns:**
- `String` - Formatted time in `HH:mm:ss` format (24-hour)

**Example:**
```kotlin
val logout = TimeCalculator.calculateLogoutTime24Hour(
    inHour = 9,
    inMinute = 0,
    workHours = 8,
    breakMinutes = 30,
    breakSeconds = 0
)
// Returns: "17:30:00"
```

---

#### getCurrentDate

Get current date in database format.

**Signature:**
```kotlin
fun getCurrentDate(): String
```

**Returns:**
- `String` - Current date in `yyyy-MM-dd` format

**Example:**
```kotlin
val today = TimeCalculator.getCurrentDate()
// Returns: "2026-01-09"
```

---

#### formatInTime

Format in-time for database storage.

**Signature:**
```kotlin
fun formatInTime(hour: Int, minute: Int): String
```

**Parameters:**
| Name | Type | Description | Range |
|------|------|-------------|-------|
| `hour` | Int | Hour | 0-23 |
| `minute` | Int | Minute | 0-59 |

**Returns:**
- `String` - Formatted time in `HH:mm:00` format

**Example:**
```kotlin
val time = TimeCalculator.formatInTime(9, 30)
// Returns: "09:30:00"
```

---

#### calculateBreakSeconds

Convert break duration to total seconds.

**Signature:**
```kotlin
fun calculateBreakSeconds(minutes: Int, seconds: Int): Int
```

**Parameters:**
| Name | Type | Description |
|------|------|-------------|
| `minutes` | Int | Break minutes |
| `seconds` | Int | Break seconds |

**Returns:**
- `Int` - Total break duration in seconds

**Example:**
```kotlin
val totalSeconds = TimeCalculator.calculateBreakSeconds(30, 45)
// Returns: 1845 (30*60 + 45)
```

---

## 🔔 AlarmScheduler

Utility class for scheduling logout reminder notifications.

### Package
```kotlin
com.worktimepro.app.utils
```

### Class Definition
```kotlin
object AlarmScheduler
```

---

### Methods

#### scheduleLogoutReminder

Schedule a notification 10 minutes before logout time.

**Signature:**
```kotlin
fun scheduleLogoutReminder(
    context: Context,
    logoutTimeMillis: Long,
    logoutTimeFormatted: String
)
```

**Parameters:**
| Name | Type | Description |
|------|------|-------------|
| `context` | Context | Application context |
| `logoutTimeMillis` | Long | Logout time in milliseconds |
| `logoutTimeFormatted` | String | Formatted logout time for display |

**Returns:**
- `Unit` (void)

**Example:**
```kotlin
val logoutMillis = AlarmScheduler.parseTimeToMillis("17:30:00")
AlarmScheduler.scheduleLogoutReminder(
    context = this,
    logoutTimeMillis = logoutMillis,
    logoutTimeFormatted = "05:30:00 PM"
)
```

**Behavior:**
- Notification triggers 10 minutes before `logoutTimeMillis`
- Uses `AlarmManager.setExactAndAllowWhileIdle()` on API 23+
- Falls back to `AlarmManager.setExact()` on older versions
- Only schedules if reminder time is in the future

---

#### cancelLogoutReminder

Cancel any existing logout reminder.

**Signature:**
```kotlin
fun cancelLogoutReminder(context: Context)
```

**Parameters:**
| Name | Type | Description |
|------|------|-------------|
| `context` | Context | Application context |

**Returns:**
- `Unit` (void)

**Example:**
```kotlin
AlarmScheduler.cancelLogoutReminder(this)
```

---

#### parseTimeToMillis

Convert time string to milliseconds (for today).

**Signature:**
```kotlin
fun parseTimeToMillis(timeString: String): Long
```

**Parameters:**
| Name | Type | Description | Format |
|------|------|-------------|--------|
| `timeString` | String | Time to parse | `HH:mm:ss` |

**Returns:**
- `Long` - Time in milliseconds since epoch

**Example:**
```kotlin
val millis = AlarmScheduler.parseTimeToMillis("17:30:00")
// Returns: 1736416800000 (varies by date)
```

---

## 🗄️ WorkTimeDao

Data Access Object for database operations.

### Package
```kotlin
com.worktimepro.app.database
```

### Class Definition
```kotlin
@Dao
interface WorkTimeDao
```

---

### Methods

#### insert

Insert a new work time record.

**Signature:**
```kotlin
suspend fun insert(record: WorkTimeRecord): Long
```

**Parameters:**
| Name | Type | Description |
|------|------|-------------|
| `record` | WorkTimeRecord | Record to insert |

**Returns:**
- `Long` - ID of inserted record

**Example:**
```kotlin
lifecycleScope.launch {
    val record = WorkTimeRecord(
        date = "2026-01-09",
        inTime = "09:00:00",
        breakDurationSeconds = 1800,
        workHours = 8,
        logoutTime = "17:30:00"
    )
    val id = database.workTimeDao().insert(record)
}
```

---

#### getAllRecords

Get all records ordered by newest first.

**Signature:**
```kotlin
fun getAllRecords(): LiveData<List<WorkTimeRecord>>
```

**Returns:**
- `LiveData<List<WorkTimeRecord>>` - Observable list of records

**Example:**
```kotlin
database.workTimeDao().getAllRecords().observe(this) { records ->
    adapter.submitList(records)
}
```

---

#### getRecordsByDate

Get records for a specific date.

**Signature:**
```kotlin
fun getRecordsByDate(date: String): LiveData<List<WorkTimeRecord>>
```

**Parameters:**
| Name | Type | Description | Format |
|------|------|-------------|--------|
| `date` | String | Date to filter by | `yyyy-MM-dd` |

**Returns:**
- `LiveData<List<WorkTimeRecord>>` - Observable filtered list

**Example:**
```kotlin
database.workTimeDao().getRecordsByDate("2026-01-09").observe(this) { records ->
    // Handle today's records
}
```

---

#### delete

Delete a specific record.

**Signature:**
```kotlin
suspend fun delete(record: WorkTimeRecord)
```

**Parameters:**
| Name | Type | Description |
|------|------|-------------|
| `record` | WorkTimeRecord | Record to delete |

**Example:**
```kotlin
lifecycleScope.launch {
    database.workTimeDao().delete(record)
}
```

---

#### deleteAll

Delete all records.

**Signature:**
```kotlin
suspend fun deleteAll()
```

**Example:**
```kotlin
lifecycleScope.launch {
    database.workTimeDao().deleteAll()
}
```

---

#### getLatestRecord

Get the most recent record.

**Signature:**
```kotlin
suspend fun getLatestRecord(): WorkTimeRecord?
```

**Returns:**
- `WorkTimeRecord?` - Latest record, or null if none exist

**Example:**
```kotlin
lifecycleScope.launch {
    val latest = database.workTimeDao().getLatestRecord()
    if (latest != null) {
        // Use latest record
    }
}
```

---

## 🗃️ WorkTimeRecord

Database entity representing a work time calculation.

### Package
```kotlin
com.worktimepro.app.database
```

### Class Definition
```kotlin
@Entity(tableName = "work_time_history")
data class WorkTimeRecord(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String,
    val inTime: String,
    val breakDurationSeconds: Int,
    val workHours: Int,
    val logoutTime: String,
    val timestamp: Long = System.currentTimeMillis()
)
```

---

### Fields

| Field | Type | Description | Constraints |
|-------|------|-------------|-------------|
| `id` | Long | Primary key | Auto-generated |
| `date` | String | Date of calculation | Format: `yyyy-MM-dd` |
| `inTime` | String | Office in-time | Format: `HH:mm:ss` |
| `breakDurationSeconds` | Int | Total break in seconds | >= 0 |
| `workHours` | Int | Work hours selected | Usually 8 or 9 |
| `logoutTime` | String | Calculated logout time | Format: `HH:mm:ss` |
| `timestamp` | Long | Record creation time | Milliseconds since epoch |

---

### Creating a Record

**Example:**
```kotlin
val record = WorkTimeRecord(
    date = TimeCalculator.getCurrentDate(),
    inTime = TimeCalculator.formatInTime(9, 0),
    breakDurationSeconds = TimeCalculator.calculateBreakSeconds(30, 0),
    workHours = 8,
    logoutTime = TimeCalculator.calculateLogoutTime24Hour(9, 0, 8, 30, 0)
)
```

---

## 📺 WorkTimeHistoryAdapter

RecyclerView adapter for displaying work time history.

### Package
```kotlin
com.worktimepro.app.adapter
```

### Class Definition
```kotlin
class WorkTimeHistoryAdapter : 
    ListAdapter<WorkTimeRecord, WorkTimeHistoryAdapter.ViewHolder>(DiffCallback())
```

---

### Usage

**Setup:**
```kotlin
val adapter = WorkTimeHistoryAdapter()
recyclerView.layoutManager = LinearLayoutManager(this)
recyclerView.adapter = adapter
```

**Submit Data:**
```kotlin
database.workTimeDao().getAllRecords().observe(this) { records ->
    adapter.submitList(records)
}
```

---

### ViewHolder

Inner class for holding item views.

**Fields:**
```kotlin
private val dateTextView: TextView
private val inTimeTextView: TextView
private val workHoursTextView: TextView
private val breakDurationTextView: TextView
private val logoutTimeTextView: TextView
```

---

### DiffCallback

Efficient list diffing for RecyclerView updates.

**Implementation:**
```kotlin
class DiffCallback : DiffUtil.ItemCallback<WorkTimeRecord>() {
    override fun areItemsTheSame(
        oldItem: WorkTimeRecord,
        newItem: WorkTimeRecord
    ): Boolean = oldItem.id == newItem.id
    
    override fun areContentsTheSame(
        oldItem: WorkTimeRecord,
        newItem: WorkTimeRecord
    ): Boolean = oldItem == newItem
}
```

---

## 🏗️ WorkTimeDatabase

Singleton Room database instance.

### Package
```kotlin
com.worktimepro.app.database
```

### Class Definition
```kotlin
@Database(entities = [WorkTimeRecord::class], version = 1)
abstract class WorkTimeDatabase : RoomDatabase()
```

---

### Getting Instance

**Signature:**
```kotlin
fun getDatabase(context: Context): WorkTimeDatabase
```

**Example:**
```kotlin
val database = WorkTimeDatabase.getDatabase(applicationContext)
val dao = database.workTimeDao()
```

**Thread Safety:**
- Uses double-checked locking
- Singleton pattern ensures one instance
- Safe for concurrent access

---

## 🔗 Integration Examples

### Complete Calculation Flow

```kotlin
// 1. Get user input
val inHour = timePicker.hour
val inMinute = timePicker.minute
val workHours = if (radio8Hours.isChecked) 8 else 9
val breakMinutes = etBreakMinutes.text.toString().toInt()
val breakSeconds = etBreakSeconds.text.toString().toInt()

// 2. Calculate logout time
val logoutTime = TimeCalculator.calculateLogoutTime(
    inHour, inMinute, workHours, breakMinutes, breakSeconds
)

// 3. Display result
tvLogoutTime.text = logoutTime

// 4. Save to database
lifecycleScope.launch {
    val record = WorkTimeRecord(
        date = TimeCalculator.getCurrentDate(),
        inTime = TimeCalculator.formatInTime(inHour, inMinute),
        breakDurationSeconds = TimeCalculator.calculateBreakSeconds(
            breakMinutes, breakSeconds
        ),
        workHours = workHours,
        logoutTime = TimeCalculator.calculateLogoutTime24Hour(
            inHour, inMinute, workHours, breakMinutes, breakSeconds
        )
    )
    database.workTimeDao().insert(record)
}

// 5. Schedule notification
val logoutMillis = AlarmScheduler.parseTimeToMillis(
    TimeCalculator.calculateLogoutTime24Hour(
        inHour, inMinute, workHours, breakMinutes, breakSeconds
    )
)
AlarmScheduler.scheduleLogoutReminder(this, logoutMillis, logoutTime)
```

---

## 🧪 Testing APIs

### Unit Test Example

```kotlin
@Test
fun testTimeCalculation() {
    val result = TimeCalculator.calculateLogoutTime(
        inHour = 9,
        inMinute = 0,
        workHours = 8,
        breakMinutes = 30,
        breakSeconds = 0
    )
    assertEquals("05:30:00 PM", result)
}
```

### Database Test Example

```kotlin
@Test
fun testInsertAndRetrieve() = runBlocking {
    val record = WorkTimeRecord(
        date = "2026-01-09",
        inTime = "09:00:00",
        breakDurationSeconds = 1800,
        workHours = 8,
        logoutTime = "17:30:00"
    )
    
    val id = dao.insert(record)
    val retrieved = dao.getLatestRecord()
    
    assertNotNull(retrieved)
    assertEquals(record.date, retrieved?.date)
}
```

---

## 📝 Notes

### Thread Safety

- **TimeCalculator**: Thread-safe (stateless object)
- **AlarmScheduler**: Thread-safe (stateless object)
- **WorkTimeDatabase**: Thread-safe (singleton with proper locking)
- **WorkTimeDao**: Thread-safe (Room handles threading)

### Performance

- Database queries use LiveData for reactive updates
- RecyclerView uses DiffUtil for efficient updates
- All heavy operations run on background threads via coroutines

### Error Handling

- Input validation in UI layer
- Try-catch blocks for database operations
- Null safety throughout Kotlin code

---

*Last Updated: January 9, 2026*
*Version: 1.0.0*
