# WorkTime Pro - Android Application

## 📱 Overview

**WorkTime Pro** is a production-ready Native Android application built with Kotlin and XML layouts. It helps office employees calculate their exact logout time based on their office in-time, break duration, and selected working hours (8 or 9 hours).

## ✨ Features

### Core Features
- ⏰ **Time Input**: 24-hour TimePicker for office in-time selection
- 🕐 **Work Hours Selection**: Radio buttons for 8 or 9 hour workdays
- ⏸️ **Break Input**: Separate inputs for break minutes and seconds
- 🧮 **Accurate Calculation**: Precise logout time calculation with overflow handling
- 📊 **Result Display**: Prominent display in 12-hour format (hh:mm:ss AM/PM)

### Advanced Features
- 📜 **Daily History**: Room Database storage with RecyclerView display
- 🔔 **Logout Reminder**: Notification 10 minutes before logout time using AlarmManager
- 🌙 **Dark Mode**: Full Material 3 dark theme support
- 📤 **Share Feature**: Share logout time via Android share intent

## 🏗️ Technical Architecture

### Technology Stack
- **Language**: Kotlin
- **UI**: XML Layouts (Material Design 3)
- **Database**: Room Database with LiveData
- **Architecture**: MVVM pattern with Repository
- **Notifications**: AlarmManager + NotificationCompat
- **Min SDK**: 21 (Android 5.0 Lollipop)
- **Target SDK**: 34 (Android 14)

### Project Structure
```
WorkTimePro/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/worktimepro/app/
│   │       │   ├── MainActivity.kt                 # Main activity
│   │       │   ├── LogoutReminderReceiver.kt       # Notification receiver
│   │       │   ├── BootReceiver.kt                 # Boot receiver
│   │       │   ├── adapter/
│   │       │   │   └── WorkTimeHistoryAdapter.kt   # RecyclerView adapter
│   │       │   ├── database/
│   │       │   │   ├── WorkTimeRecord.kt           # Room entity
│   │       │   │   ├── WorkTimeDao.kt              # Room DAO
│   │       │   │   └── WorkTimeDatabase.kt         # Room database
│   │       │   └── utils/
│   │       │       ├── TimeCalculator.kt           # Time calculation logic
│   │       │       └── AlarmScheduler.kt           # Alarm scheduling
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   │   ├── activity_main.xml           # Main layout
│   │       │   │   └── item_work_time_history.xml  # History item layout
│   │       │   ├── values/
│   │       │   │   ├── strings.xml                 # String resources
│   │       │   │   ├── colors.xml                  # Light theme colors
│   │       │   │   └── themes.xml                  # Light theme
│   │       │   ├── values-night/
│   │       │   │   ├── colors.xml                  # Dark theme colors
│   │       │   │   └── themes.xml                  # Dark theme
│   │       │   ├── drawable/
│   │       │   │   ├── ic_calculate.xml            # Calculate icon
│   │       │   │   ├── ic_share.xml                # Share icon
│   │       │   │   ├── ic_notification.xml         # Notification icon
│   │       │   │   └── bg_work_hours_badge.xml     # Badge background
│   │       │   └── xml/
│   │       │       ├── data_extraction_rules.xml   # Backup rules (API 31+)
│   │       │       └── backup_rules.xml            # Backup rules
│   │       └── AndroidManifest.xml                 # App manifest
│   ├── build.gradle                                # App-level Gradle
│   └── proguard-rules.pro                          # ProGuard rules
├── build.gradle                                    # Project-level Gradle
├── settings.gradle                                 # Gradle settings
└── README.md                                       # This file
```

## 🧮 Calculation Logic

The logout time calculation follows this formula:

```
Logout Time = In-Time + Work Hours + Break Duration
```

### Implementation Details

1. **Input Collection**:
   - Office in-time (hour and minute from TimePicker)
   - Work hours (8 or 9 from RadioGroup)
   - Break duration (minutes and seconds from EditText)

2. **Calculation Process**:
   ```kotlin
   val calendar = Calendar.getInstance().apply {
       set(Calendar.HOUR_OF_DAY, inHour)
       set(Calendar.MINUTE, inMinute)
       set(Calendar.SECOND, 0)
   }
   
   // Add work hours
   calendar.add(Calendar.HOUR_OF_DAY, workHours)
   
   // Add break duration
   calendar.add(Calendar.MINUTE, breakMinutes)
   calendar.add(Calendar.SECOND, breakSeconds)
   ```

3. **Overflow Handling**:
   - Calendar API automatically handles minute/second overflow
   - Example: 59 minutes + 2 minutes = 1 hour 1 minute
   - Example: 59 seconds + 5 seconds = 1 minute 4 seconds

4. **Output Format**:
   - Display: 12-hour format (hh:mm:ss AM/PM)
   - Database: 24-hour format (HH:mm:ss)

### Example Calculation

**Input**:
- In-Time: 09:00
- Work Hours: 8 hours
- Break: 30 minutes 0 seconds

**Calculation**:
- 09:00 + 8 hours = 17:00
- 17:00 + 30 minutes = 17:30
- 17:30 + 0 seconds = 17:30:00

**Output**: `05:30:00 PM`

## 📦 Database Schema

### WorkTimeRecord Entity

| Column | Type | Description |
|--------|------|-------------|
| id | Long | Primary key (auto-generated) |
| date | String | Date in yyyy-MM-dd format |
| inTime | String | In-time in HH:mm:ss format |
| breakDurationSeconds | Int | Total break in seconds |
| workHours | Int | Selected work hours (8 or 9) |
| logoutTime | String | Calculated logout time (HH:mm:ss) |
| timestamp | Long | Record creation timestamp |

## 🔔 Notification System

### Features
- Scheduled 10 minutes before logout time
- Uses AlarmManager for exact timing
- Works even when app is closed
- Restored after device reboot
- Android 8+ notification channel support

### Implementation
```kotlin
// Schedule notification
AlarmScheduler.scheduleLogoutReminder(
    context = this,
    logoutTimeMillis = logoutTimeMillis,
    logoutTimeFormatted = "05:30:00 PM"
)
```

## 🎨 UI/UX Design

### Material Design 3
- Modern Material 3 components
- Adaptive color system
- Proper elevation and shadows
- Smooth animations

### Dark Mode Support
- Automatic system theme detection
- Optimized colors for both themes
- Consistent experience across modes

### Responsive Layout
- ScrollView for small screens
- Proper spacing and padding
- Touch-friendly input elements

## 🔧 Building the APK

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 17 or later
- Android SDK 34
- Gradle 8.0

### Steps to Build

1. **Open Project in Android Studio**:
   ```
   File → Open → Select WorkTimePro folder
   ```

2. **Sync Gradle**:
   - Android Studio will automatically sync Gradle
   - Wait for dependencies to download

3. **Build Debug APK**:
   ```
   Build → Build Bundle(s) / APK(s) → Build APK(s)
   ```
   - APK location: `app/build/outputs/apk/debug/app-debug.apk`

4. **Build Release APK**:
   ```
   Build → Generate Signed Bundle / APK
   ```
   - Select APK
   - Create or select keystore
   - Enter keystore credentials
   - Choose release build variant
   - APK location: `app/build/outputs/apk/release/app-release.apk`

### Command Line Build

```bash
# Navigate to project directory
cd WorkTimePro

# Build debug APK
./gradlew assembleDebug

# Build release APK (requires keystore)
./gradlew assembleRelease
```

## 🧪 Testing

### Manual Testing Checklist

- [ ] Time picker displays correctly
- [ ] Radio buttons work (8h/9h selection)
- [ ] Break input accepts valid values
- [ ] Calculate button performs calculation
- [ ] Result displays in correct format
- [ ] History saves to database
- [ ] RecyclerView displays history
- [ ] Share button opens share dialog
- [ ] Notification scheduled correctly
- [ ] Notification appears at right time
- [ ] Dark mode switches correctly
- [ ] App works after device reboot

### Test Cases

**Test Case 1: Basic Calculation**
- Input: 09:00, 8 hours, 30 min break
- Expected: 05:30:00 PM

**Test Case 2: Overflow Handling**
- Input: 09:30, 9 hours, 45 min 30 sec break
- Expected: 07:15:30 PM

**Test Case 3: Night Shift**
- Input: 22:00, 8 hours, 60 min break
- Expected: 07:00:00 AM (next day)

## 📱 Permissions

### Required Permissions
- `POST_NOTIFICATIONS` - For showing logout reminders (Android 13+)
- `SCHEDULE_EXACT_ALARM` - For exact alarm scheduling (Android 12+)
- `USE_EXACT_ALARM` - Alternative for exact alarms
- `WAKE_LOCK` - To wake device for notifications
- `RECEIVE_BOOT_COMPLETED` - To restore alarms after reboot

### Runtime Permissions
- Notification permission is requested at runtime on Android 13+
- User can deny permission; app will still work but without notifications

## 🚀 Future Enhancements

- [ ] Multiple shift support
- [ ] Custom work hour input
- [ ] Export history to CSV
- [ ] Statistics and analytics
- [ ] Widget for home screen
- [ ] Overtime calculation
- [ ] Multiple notification reminders
- [ ] Backup/restore to cloud

## 📄 License

This project is created for educational and professional demonstration purposes.

## 👨‍💻 Developer Notes

### Code Quality
- Clean architecture principles
- SOLID principles followed
- Comprehensive comments
- Error handling implemented
- Memory leak prevention

### Production Readiness
- ✅ No hardcoded strings
- ✅ Proper resource management
- ✅ Database migrations handled
- ✅ ProGuard rules configured
- ✅ Backup rules defined
- ✅ Permission handling
- ✅ Dark mode support
- ✅ Material Design 3

### Interview Ready
This project demonstrates:
- Kotlin proficiency
- Android SDK knowledge
- Database management (Room)
- Background tasks (AlarmManager)
- Notifications
- Material Design
- Clean code practices
- Production-ready architecture

---

**Built with ❤️ using Kotlin and Android Studio**
