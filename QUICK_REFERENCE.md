# WorkTime Pro - Quick Reference Guide

## 🚀 Quick Start (3 Minutes)

### 1. Open Project
```
Android Studio → File → Open → Select "WorkTimePro" folder
```

### 2. Wait for Gradle Sync
```
Bottom status bar: "Gradle sync in progress..."
Wait until: "Gradle sync finished"
```

### 3. Build APK
```
Build → Build Bundle(s) / APK(s) → Build APK(s)
```

**Done!** APK is at: `app/build/outputs/apk/debug/app-debug.apk`

---

## 📁 Key Files Reference

### Main Application Files
| File | Purpose | Lines |
|------|---------|-------|
| `MainActivity.kt` | Main app logic | ~350 |
| `TimeCalculator.kt` | Calculation logic | ~120 |
| `WorkTimeHistoryAdapter.kt` | History display | ~130 |
| `LogoutReminderReceiver.kt` | Notifications | ~100 |
| `AlarmScheduler.kt` | Alarm management | ~100 |

### Database Files
| File | Purpose |
|------|---------|
| `WorkTimeRecord.kt` | Entity (table schema) |
| `WorkTimeDao.kt` | Database operations |
| `WorkTimeDatabase.kt` | Database instance |

### Layout Files
| File | Purpose |
|------|---------|
| `activity_main.xml` | Main screen UI |
| `item_work_time_history.xml` | History item UI |

### Configuration Files
| File | Purpose |
|------|---------|
| `AndroidManifest.xml` | App configuration & permissions |
| `build.gradle` (app) | Dependencies & build config |
| `strings.xml` | All text resources |
| `themes.xml` | Light/dark themes |

---

## 🎯 Core Calculation Logic

### Formula
```kotlin
Logout Time = In-Time + Work Hours + Break Duration
```

### Example
```
Input:
  In-Time: 09:00
  Work Hours: 8 hours
  Break: 30 minutes 0 seconds

Calculation:
  09:00 + 8:00:00 + 00:30:00 = 17:30:00

Output:
  05:30:00 PM
```

### Implementation (TimeCalculator.kt)
```kotlin
fun calculateLogoutTime(
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
    }
    
    calendar.add(Calendar.HOUR_OF_DAY, workHours)
    calendar.add(Calendar.MINUTE, breakMinutes)
    calendar.add(Calendar.SECOND, breakSeconds)
    
    return formatTo12Hour(calendar)
}
```

---

## 🗄️ Database Schema

### Table: work_time_history

| Column | Type | Description |
|--------|------|-------------|
| id | Long | Auto-generated primary key |
| date | String | Date (yyyy-MM-dd) |
| inTime | String | In-time (HH:mm:ss) |
| breakDurationSeconds | Int | Break in seconds |
| workHours | Int | 8 or 9 |
| logoutTime | String | Logout time (HH:mm:ss) |
| timestamp | Long | Record creation time |

### Sample Data
```
id: 1
date: "2026-01-09"
inTime: "09:00:00"
breakDurationSeconds: 1800  (30 minutes)
workHours: 8
logoutTime: "17:30:00"
timestamp: 1736416800000
```

---

## 🔔 Notification System

### Trigger
```
Logout Time - 10 minutes = Notification Time
```

### Example
```
Logout Time: 05:30:00 PM
Notification: 05:20:00 PM
```

### Implementation Flow
```
1. User calculates logout time
2. MainActivity calls AlarmScheduler.scheduleLogoutReminder()
3. AlarmManager schedules exact alarm
4. At trigger time, LogoutReminderReceiver.onReceive() fires
5. Notification appears
```

### Code
```kotlin
// Schedule (AlarmScheduler.kt)
val reminderTimeMillis = logoutTimeMillis - (10 * 60 * 1000)
alarmManager.setExactAndAllowWhileIdle(
    AlarmManager.RTC_WAKEUP,
    reminderTimeMillis,
    pendingIntent
)

// Receive (LogoutReminderReceiver.kt)
override fun onReceive(context: Context, intent: Intent) {
    val logoutTime = intent.getStringExtra(EXTRA_LOGOUT_TIME)
    // Show notification
}
```

---

## 🎨 Theme Colors

### Light Theme
```xml
Primary:    #1976D2 (Blue)
Secondary:  #00897B (Teal)
Tertiary:   #F57C00 (Orange)
Background: #F5F5F5 (Light Gray)
Surface:    #FFFFFF (White)
```

### Dark Theme
```xml
Primary:    #90CAF9 (Light Blue)
Secondary:  #4DB6AC (Light Teal)
Tertiary:   #FFB74D (Light Orange)
Background: #121212 (Dark Gray)
Surface:    #1E1E1E (Dark Gray)
```

---

## 📱 Permissions

### Required Permissions
```xml
<!-- Notifications (Android 13+) -->
<uses-permission android:name="android.permission.POST_NOTIFICATIONS" />

<!-- Exact Alarms (Android 12+) -->
<uses-permission android:name="android.permission.SCHEDULE_EXACT_ALARM" />
<uses-permission android:name="android.permission.USE_EXACT_ALARM" />

<!-- Wake Device -->
<uses-permission android:name="android.permission.WAKE_LOCK" />

<!-- Boot Receiver -->
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
```

### Runtime Permission Request
```kotlin
// Android 13+ only
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    ActivityCompat.requestPermissions(
        this,
        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
        NOTIFICATION_PERMISSION_CODE
    )
}
```

---

## 🔧 Common Tasks

### Change App Name
**File**: `res/values/strings.xml`
```xml
<string name="app_name">Your New Name</string>
```

### Change Primary Color
**File**: `res/values/colors.xml`
```xml
<color name="md_theme_light_primary">#YOUR_COLOR</color>
```

### Change Work Hour Options
**File**: `MainActivity.kt`
```kotlin
// Modify calculation logic
val workHours = if (radio8Hours.isChecked) 8 else 9
// Change to: val workHours = if (radio7Hours.isChecked) 7 else 10
```

### Add New Database Field
**File**: `database/WorkTimeRecord.kt`
```kotlin
@Entity(tableName = "work_time_history")
data class WorkTimeRecord(
    // ... existing fields ...
    val newField: String  // Add new field
)
```
**Note**: Increment database version in `WorkTimeDatabase.kt`

---

## 🐛 Troubleshooting

### Gradle Sync Failed
```
Solution:
1. File → Invalidate Caches / Restart
2. Delete .gradle folder
3. Rebuild project
```

### SDK Not Found
```
Solution:
1. File → Settings → Android SDK
2. Install Android 14.0 (API 34)
3. Apply changes
```

### Build Error: "Manifest merger failed"
```
Solution:
Check AndroidManifest.xml for duplicate entries
```

### App Crashes on Launch
```
Solution:
1. Check Logcat for error
2. Verify all dependencies are synced
3. Clean and rebuild project
```

### Notification Not Showing
```
Solution:
1. Check notification permission granted
2. Verify alarm scheduled (check Logcat)
3. Test on real device (not emulator)
```

---

## 📊 Testing Checklist

### Basic Functionality
- [ ] App launches without crash
- [ ] TimePicker displays correctly
- [ ] Radio buttons work
- [ ] Break input accepts numbers
- [ ] Calculate button works
- [ ] Result displays correctly
- [ ] History saves to database
- [ ] RecyclerView shows history

### Advanced Features
- [ ] Notification scheduled
- [ ] Notification appears at right time
- [ ] Share button works
- [ ] Dark mode switches correctly
- [ ] App survives rotation
- [ ] Database persists after restart

### Edge Cases
- [ ] Empty break input handled
- [ ] Negative values rejected
- [ ] Seconds > 59 rejected
- [ ] Midnight boundary works
- [ ] Next day calculation works

---

## 📈 Performance Tips

### Build Faster
Add to `gradle.properties`:
```properties
org.gradle.parallel=true
org.gradle.caching=true
org.gradle.daemon=true
org.gradle.jvmargs=-Xmx4096m
```

### Reduce APK Size
Enable in `app/build.gradle`:
```gradle
buildTypes {
    release {
        minifyEnabled true
        shrinkResources true
    }
}
```

---

## 🎓 Code Snippets

### Get Current Date
```kotlin
val currentDate = TimeCalculator.getCurrentDate()
// Returns: "2026-01-09"
```

### Format Time to 12-Hour
```kotlin
val time12Hour = TimeCalculator.calculateLogoutTime(9, 0, 8, 30, 0)
// Returns: "05:30:00 PM"
```

### Insert Database Record
```kotlin
lifecycleScope.launch {
    val record = WorkTimeRecord(
        date = "2026-01-09",
        inTime = "09:00:00",
        breakDurationSeconds = 1800,
        workHours = 8,
        logoutTime = "17:30:00"
    )
    database.workTimeDao().insert(record)
}
```

### Schedule Notification
```kotlin
AlarmScheduler.scheduleLogoutReminder(
    context = this,
    logoutTimeMillis = logoutTimeMillis,
    logoutTimeFormatted = "05:30:00 PM"
)
```

---

## 📚 Documentation Files

| File | Purpose |
|------|---------|
| **README.md** | Project overview & features |
| **BUILD_GUIDE.md** | Detailed build instructions |
| **PROJECT_STRUCTURE.md** | Architecture & file organization |
| **FEATURES.md** | Complete feature documentation |
| **PROJECT_SUMMARY.md** | Completion status & statistics |
| **QUICK_REFERENCE.md** | This file - quick tips |

---

## 🎯 Next Steps

### For Learning
1. Read through MainActivity.kt
2. Understand TimeCalculator logic
3. Study Room Database implementation
4. Review notification system
5. Explore Material Design usage

### For Building
1. Open in Android Studio
2. Sync Gradle
3. Build debug APK
4. Install on device
5. Test all features

### For Publishing
1. Create release keystore
2. Build signed APK
3. Test thoroughly
4. Create Play Store listing
5. Submit for review

---

## 💡 Pro Tips

1. **Use Logcat**: Always check Logcat for errors
2. **Test on Real Device**: Notifications work better on real devices
3. **Enable Developer Options**: For USB debugging
4. **Use Breakpoints**: Debug with Android Studio debugger
5. **Read Documentation**: All answers are in the docs!

---

## 🆘 Quick Help

### Can't find a file?
Use Android Studio's search: `Ctrl+Shift+N` (Windows) or `Cmd+Shift+O` (Mac)

### Need to find text?
Use global search: `Ctrl+Shift+F` (Windows) or `Cmd+Shift+F` (Mac)

### App not running?
Check: Build → Clean Project → Rebuild Project

### Gradle issues?
Try: File → Invalidate Caches / Restart

---

## ✅ Final Checklist

Before building:
- [ ] Gradle synced successfully
- [ ] No red errors in code
- [ ] All imports resolved
- [ ] Build configuration correct

Before testing:
- [ ] Device/emulator connected
- [ ] USB debugging enabled
- [ ] Sufficient storage space
- [ ] Android version compatible

Before publishing:
- [ ] All features tested
- [ ] No crashes
- [ ] Keystore created
- [ ] Version updated
- [ ] ProGuard tested

---

**You're all set! Happy coding! 🚀**
