# 📱 WorkTime Pro - Complete Installation Guide

## From Zero to Installed App on Your Phone

This guide will take you step-by-step from opening the project to having the app running on your mobile phone.

---

## 📋 PART 1: Prerequisites & Setup

### Step 1.1: Check if Android Studio is Installed

**Open Command Prompt** and type:
```cmd
where android-studio
```

**If Android Studio is NOT installed:**

1. **Download Android Studio**
   - Go to: https://developer.android.com/studio
   - Click **"Download Android Studio"**
   - Accept terms and download

2. **Install Android Studio**
   - Run the downloaded installer
   - Follow the setup wizard
   - Choose **"Standard"** installation
   - Let it download SDK and other components (~3-5 GB, takes 10-20 minutes)
   - Click **"Finish"** when done

3. **Launch Android Studio**
   - The first launch will take 1-2 minutes
   - You'll see the "Welcome to Android Studio" screen

---

### Step 1.2: Verify Android SDK is Installed

1. **Open Android Studio**

2. **Go to SDK Manager**:
   - Click **"More Actions"** (three dots) on welcome screen
   - OR go to **File → Settings** (if project is open)
   - Navigate to **Appearance & Behavior → System Settings → Android SDK**

3. **Check SDK Platforms Tab**:
   - ✅ Ensure **"Android 14.0 (API 34)"** is checked
   - ✅ Also recommended: **"Android 10.0 (API 29)"** for testing
   - If not installed, check the boxes and click **"Apply"**
   - Wait for download (can take 5-10 minutes)

4. **Check SDK Tools Tab**:
   - ✅ Ensure **"Android SDK Build-Tools"** is installed
   - ✅ Ensure **"Android Emulator"** is installed (if you want to test on emulator)
   - Click **"OK"**

---

## 📂 PART 2: Opening the Project

### Step 2.1: Launch Android Studio

**Method 1: From Desktop**
- Double-click **"Android Studio"** icon on desktop

**Method 2: From Start Menu**
- Press **Windows key**
- Type **"Android Studio"**
- Click the app

**When Android Studio opens**, you'll see:
```
┌─────────────────────────────────────┐
│  Welcome to Android Studio          │
│                                     │
│  ○ New Project                      │
│  ○ Open                            │
│  ○ Get from VCS                    │
│                                     │
│  Recent Projects:                   │
│  (list of recent projects)          │
└─────────────────────────────────────┘
```

---

### Step 2.2: Open WorkTimePro Project

1. **Click "Open"** (or File → Open if you have another project open)

2. **Navigate to the WorkTimePro folder**:
   ```
   C:\Users\Intern Engineering 1\Downloads\WorkTimePro
   ```
   
   **Important**: Select the **folder** itself, not any file inside it
   
   You should see:
   ```
   WorkTimePro/
   ├── app/
   ├── gradle/
   ├── build.gradle
   ├── settings.gradle
   └── ... other files
   ```

3. **Click "OK"**

4. **Wait for Project to Load** (10-30 seconds)
   - You'll see a progress bar at the bottom
   - Status will show: "Loading project..."

---

## ⚙️ PART 3: Gradle Sync (IMPORTANT!)

### Step 3.1: Automatic Gradle Sync

**What is Gradle Sync?**
- Gradle is the build system that compiles your app
- Sync downloads all dependencies and configures the project
- **This is REQUIRED** before building

**When you open the project, Gradle will automatically start syncing:**

1. **Look at the bottom-right corner** of Android Studio:
   ```
   🔄 Gradle sync in progress...
   ```

2. **Or look at the bottom status bar**:
   ```
   Gradle: Downloading dependencies... (1/25)
   ```

3. **Wait for sync to complete** (First time: 2-5 minutes)
   - ✅ When done, you'll see: **"Gradle sync finished"**
   - Or: **"BUILD SUCCESSFUL"**

---

### Step 3.2: If Gradle Sync Doesn't Start

**If nothing happens after opening:**

1. **Click "Sync Now"**
   - Look for a blue banner at the top saying:
     ```
     "Gradle files have changed since last project sync..."
     [Sync Now]
     ```
   - Click **"Sync Now"**

2. **OR manually trigger sync**:
   - Go to **File → Sync Project with Gradle Files**
   - OR click the 🐘 (elephant icon) in the toolbar

---

### Step 3.3: Troubleshooting Gradle Sync

**If sync fails with errors:**

#### Error: "SDK location not found"

**Solution 1**: Create `local.properties` file:
1. In Android Studio, right-click on **"WorkTimePro"** (root folder)
2. Click **New → File**
3. Name it: `local.properties`
4. Add this line:
   ```properties
   sdk.dir=C\:\\Users\\Intern Engineering 1\\AppData\\Local\\Android\\Sdk
   ```
   *Note: Replace with your actual SDK path. Find it in File → Settings → Android SDK*

**Solution 2**: Let Android Studio create it:
1. Go to **File → Project Structure**
2. Click **"OK"**
3. This will auto-create the file

#### Error: "Failed to download..."

**Solution**: Internet connection issue
1. Check your internet connection
2. Retry: **File → Sync Project with Gradle Files**

#### Error: "Unsupported Gradle version"

**Solution**: Update Gradle
1. Go to **File → Settings**
2. Navigate to **Build, Execution, Deployment → Build Tools → Gradle**
3. Select **"Use Gradle from: 'gradle-wrapper.properties'"**
4. Click **"OK"**

---

## 🔨 PART 4: Building the APK

### Step 4.1: Build Debug APK (Recommended for Testing)

**Debug APK** = App with debugging enabled, not optimized, faster to build

1. **Wait for Gradle sync to finish** (status bar shows "✓")

2. **Go to Menu Bar**:
   ```
   Build → Build Bundle(s) / APK(s) → Build APK(s)
   ```

3. **Click "Build APK(s)"**

4. **Wait for build to complete**:
   - Bottom status bar shows: **"Gradle Build Running..."**
   - Progress: **"BUILD SUCCESSFUL"** or build percentage
   - Time: **1-3 minutes** (first build)

5. **When build succeeds**, you'll see a notification:
   ```
   ┌────────────────────────────────────────┐
   │  APK(s) generated successfully         │
   │  locate                                │
   └────────────────────────────────────────┘
   ```

6. **Click "locate"** to open the folder with APK

**OR find it manually at**:
```
C:\Users\Intern Engineering 1\Downloads\WorkTimePro\app\build\outputs\apk\debug\app-debug.apk
```

---

### Step 4.2: Build Release APK (For Production)

**Release APK** = Optimized, smaller size, needs signing

**Skip this if you just want to test the app. Use Debug APK above.**

**For release APK, you need a signing key first:**

1. **Create Keystore** (one-time setup):
   - Go to **Build → Generate Signed Bundle / APK**
   - Select **APK**
   - Click **"Next"**
   - Click **"Create new..."**
   - Fill in:
     ```
     Key store path: C:\keystore\worktime-pro.jks
     Password: [YOUR_STRONG_PASSWORD]
     Alias: worktime-pro-key
     Alias password: [YOUR_ALIAS_PASSWORD]
     Validity: 9125 (25 years)
     
     First and Last Name: Your Name
     Organization: Your Company
     City: Your City
     State: Your State
     Country Code: IN
     ```
   - Click **"OK"**
   - **SAVE PASSWORDS SECURELY!** You'll need them for updates

2. **Build Signed APK**:
   - Click **"Next"**
   - Select **"release"** build variant
   - Check ☑ **V1 (Jar Signature)**
   - Check ☑ **V2 (Full APK Signature)**
   - Click **"Finish"**

3. **Find release APK at**:
   ```
   C:\Users\Intern Engineering 1\Downloads\WorkTimePro\app\build\outputs\apk\release\app-release.apk
   ```

---

### Step 4.3: Troubleshooting Build Errors

#### Error: "Build failed with exception"

**Check Logcat/Build Output**:
1. Click **"Build"** tab at bottom
2. Read the error message
3. Common solutions below

#### Error: "Execution failed for task ':app:compileDebugKotlin'"

**Solution**:
1. **File → Invalidate Caches / Restart**
2. Select **"Invalidate and Restart"**
3. Wait for restart and rebuild

#### Error: "Cannot resolve symbol 'R'"

**Solution**:
1. **Build → Clean Project**
2. **Build → Rebuild Project**

#### Error: "Out of memory"

**Solution**: Increase heap size
1. Go to **File → Settings**
2. Navigate to **Build, Execution, Deployment → Compiler**
3. Change **"Build process heap size"** to **2048** MB
4. Click **"OK"**

---

## 📲 PART 5: Preparing Your Mobile Phone

### Step 5.1: Enable USB Debugging (Android Phone)

**On your Android phone:**

1. **Enable Developer Options**:
   - Go to **Settings → About Phone**
   - Find **"Build Number"**
   - Tap **"Build Number" 7 times** rapidly
   - You'll see: **"You are now a developer!"**

2. **Enable USB Debugging**:
   - Go to **Settings → System → Developer Options**
   - (Or **Settings → Developer Options** on some phones)
   - Toggle ON: **"USB Debugging"**
   - Toggle ON: **"Install via USB"** (if available)

3. **Allow Unknown Sources** (for APK installation):
   - Go to **Settings → Security** (or **Settings → Apps**)
   - Enable **"Unknown Sources"** 
   - OR **"Install unknown apps"** → Select **"My Files"** or **"Chrome"** → Allow

---

### Step 5.2: Connect Phone to Computer

1. **Get USB Cable**:
   - Use the USB cable that came with your phone
   - Or any compatible USB cable

2. **Connect Phone to Computer**:
   - Plug USB into phone
   - Plug other end into computer

3. **Select USB Mode**:
   When you connect, your phone will ask:
   ```
   Use USB for:
   ○ Charging
   ○ File Transfer / MTP
   ○ PTP
   ○ USB Tethering
   ```
   
   **Select "File Transfer" or "MTP"**

4. **Allow USB Debugging**:
   A popup appears on phone:
   ```
   Allow USB debugging?
   Computer: RSA key fingerprint: ...
   
   [Always allow from this computer]
   [Cancel] [OK]
   ```
   
   - Check ☑ **"Always allow from this computer"**
   - Tap **"OK"**

5. **Verify Connection in Android Studio**:
   - Look at top toolbar in Android Studio
   - You should see your device name in device dropdown
   - Example: **"Samsung Galaxy A51"** or **"Pixel 6"**

---

## 📥 PART 6: Installing APK on Phone

### Method 1: Install Directly from Android Studio (Easiest)

**If your phone is connected via USB:**

1. **In Android Studio**, make sure device is selected:
   - Top toolbar: Click device dropdown
   - Select your phone (e.g., "Samsung Galaxy A51")

2. **Click the Green ▶ "Run" button**
   - OR go to **Run → Run 'app'**
   - OR press **Shift + F10**

3. **App will automatically**:
   - Build APK
   - Install on your phone
   - Launch the app

4. **Done!** The app is now on your phone

---

### Method 2: Transfer APK and Install Manually

**If USB connection doesn't work or you prefer manual installation:**

#### Option A: Transfer via USB Cable

1. **Locate the APK**:
   ```
   C:\Users\Intern Engineering 1\Downloads\WorkTimePro\app\build\outputs\apk\debug\app-debug.apk
   ```

2. **Copy APK to Phone**:
   - Open **File Explorer** (Windows + E)
   - Navigate to the APK location above
   - Right-click **app-debug.apk** → **Copy**
   - In File Explorer sidebar, find your phone (e.g., "Galaxy A51")
   - Open **Internal Storage** → **Download** folder
   - Right-click → **Paste**

3. **Install on Phone**:
   - On your phone, open **My Files** or **File Manager** app
   - Navigate to **Downloads** folder
   - Tap **app-debug.apk**
   - Tap **"Install"**
   - Tap **"Open"** when installation completes

#### Option B: Transfer via Email

1. **Email to yourself**:
   - Open Gmail or any email app on computer
   - Attach **app-debug.apk**
   - Send to your email

2. **On your phone**:
   - Open email
   - Download attachment
   - Tap the APK file
   - Tap **"Install"**

#### Option C: Transfer via Cloud Storage

1. **Upload to Google Drive**:
   - Go to https://drive.google.com
   - Click **"+ New" → File Upload**
   - Select **app-debug.apk**
   - Wait for upload

2. **Download on phone**:
   - Open **Google Drive** app on phone
   - Find **app-debug.apk**
   - Tap to download
   - Tap **"Install"**

#### Option D: Transfer via WhatsApp/Telegram

1. **Send APK to yourself**:
   - Open WhatsApp Web or Telegram Web
   - Find your own chat ("Messages to myself")
   - Attach **app-debug.apk** as file
   - Send

2. **On phone**:
   - Open WhatsApp/Telegram
   - Download the APK file
   - Tap to install

---

### Step 6.1: Installation Warnings (Normal!)

**When installing, you may see:**

#### Warning 1: "Install unknown app"
```
For your security, your phone is not allowed to install
unknown apps from this source.
```

**Solution**:
- Tap **"Settings"**
- Toggle ON **"Allow from this source"**
- Go back and try again

#### Warning 2: "This app may harm your device"
```
Google Play Protect
App not scanned
```

**This is NORMAL for custom APKs!**
- Your app is safe (you built it!)
- Tap **"Install anyway"** or **"More details" → "Install anyway"**

---

## ✅ PART 7: Launching the App

### Step 7.1: Open WorkTime Pro

**After successful installation:**

1. **Option 1**: Tap **"Open"** in the installer

2. **Option 2**: Find app in app drawer
   - Open app drawer (swipe up or tap grid icon)
   - Look for **"WorkTime Pro"** icon
   - Tap to open

---

### Step 7.2: Grant Permissions

**When you first open the app, it will ask for permissions:**

1. **Notification Permission** (Android 13+ only):
   ```
   Allow WorkTime Pro to send you notifications?
   [Don't allow] [Allow]
   ```
   - Tap **"Allow"** (needed for logout reminders)

2. **If you deny by mistake**:
   - Go to **Settings → Apps → WorkTime Pro → Permissions**
   - Enable **"Notifications"**

---

## 🎉 PART 8: Testing the App

### Step 8.1: Test Basic Functionality

1. **Set Office In-Time**:
   - Use the time picker to select your office arrival time
   - Example: 09:00

2. **Select Work Hours**:
   - Tap **"8 Hours"** or **"9 Hours"**

3. **Enter Break Duration**:
   - Minutes: **30**
   - Seconds: **0**

4. **Calculate**:
   - Tap **"Calculate Logout Time"** button
   - You should see the logout time displayed
   - Example: **05:30:00 PM**

5. **Check History**:
   - Scroll down
   - You should see your calculation saved in history

6. **Test Share**:
   - Tap **"Share"** button
   - Select WhatsApp or any app
   - You should see: "Today's logout time is 05:30:00 PM"

7. **Test Dark Mode**:
   - On your phone, enable dark mode:
     - **Settings → Display → Dark mode → ON**
   - Open WorkTime Pro again
   - App should be in dark theme

---

### Step 8.2: Test Notifications

**Test if notifications work:**

1. **Calculate a logout time** that's **15 minutes from now**
   - Example: If current time is 10:30 AM
   - Set in-time: 10:35 AM
   - Work hours: 8 hours
   - Break: 0 minutes
   - Result will be: 06:35 PM

2. **Wait 10 minutes before the logout time**
   - Notification should appear 10 minutes before
   - In this example: at 06:25 PM

3. **Check notification**:
   - You should see: "⏰ Logout Reminder"
   - "Your logout time is 06:35:00 PM (in 10 minutes)"

---

## 🔧 PART 9: Troubleshooting

### App won't install on phone

**Error: "App not installed"**

**Solutions**:
1. Uninstall any previous version first
2. Go to **Settings → Storage** → Clear cache
3. Restart phone and try again

---

### App crashes on launch

**Solutions**:
1. **Check Android version**: Minimum Android 5.0 required
2. **Clear app data**:
   - **Settings → Apps → WorkTime Pro**
   - Tap **"Storage"**
   - Tap **"Clear Data"**
3. **Reinstall**: Uninstall and install again

---

### Notifications not showing

**Solutions**:
1. **Check notification permission**:
   - **Settings → Apps → WorkTime Pro → Permissions**
   - Enable **Notifications**

2. **Check Do Not Disturb**:
   - Turn off Do Not Disturb mode

3. **Check battery optimization**:
   - **Settings → Apps → WorkTime Pro**
   - **Battery → Unrestricted**

---

### Can't connect phone to Android Studio

**Solutions**:
1. **Install phone drivers**:
   - Google "USB drivers for [your phone model]"
   - Install manufacturer drivers

2. **Try different USB port**

3. **Try different USB cable**

4. **Restart ADB**:
   - In Android Studio Terminal:
   ```bash
   adb kill-server
   adb start-server
   ```

---

## 📊 Summary Checklist

### Before Building:
- [ ] Android Studio installed
- [ ] Android SDK (API 34) installed
- [ ] Project opened in Android Studio
- [ ] Gradle sync completed successfully

### Building APK:
- [ ] Build → Build APK(s) clicked
- [ ] Build successful
- [ ] APK located in `app/build/outputs/apk/debug/`

### Phone Setup:
- [ ] Developer Options enabled
- [ ] USB Debugging enabled
- [ ] Unknown Sources allowed
- [ ] Phone connected via USB (for direct install)

### Installation:
- [ ] APK transferred to phone
- [ ] APK installed successfully
- [ ] App opens without crash

### Testing:
- [ ] Time calculation works
- [ ] History saves correctly
- [ ] Share feature works
- [ ] Dark mode works
- [ ] Notifications appear (test with future time)

---

## 🎯 Quick Reference Commands

### In Android Studio Terminal:

```bash
# Build debug APK
gradle assembleDebug

# Check connected devices
adb devices

# Install APK to connected phone
adb install app/build/outputs/apk/debug/app-debug.apk

# Uninstall app from phone
adb uninstall com.worktimepro.app

# View logs
adb logcat | findstr WorkTime
```

---

## 📞 Need More Help?

### If you encounter any issues:

1. **Check Build Output**:
   - Click **"Build"** tab at bottom of Android Studio
   - Read error messages

2. **Check Logcat** (for runtime errors):
   - Click **"Logcat"** tab at bottom
   - Filter by app name: WorkTime

3. **Review Documentation**:
   - [BUILD_GUIDE.md](BUILD_GUIDE.md) - Detailed build instructions
   - [QUICK_REFERENCE.md](QUICK_REFERENCE.md) - Troubleshooting
   - [README.md](README.md) - General info

---

## 🎉 Success!

**Once installed and tested, you have:**
- ✅ Successfully built an Android APK
- ✅ Installed a custom app on your phone
- ✅ A working productivity app for calculating logout time
- ✅ Experience with Android development workflow

**Enjoy your WorkTime Pro app!** 🚀

---

**Last Updated**: January 12, 2026  
**Project**: WorkTime Pro v1.0.0  
**Difficulty**: Beginner-Friendly ⭐⭐☆☆☆
