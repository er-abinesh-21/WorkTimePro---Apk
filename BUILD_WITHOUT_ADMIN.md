# 📱 Build APK Without Admin Privileges

## Alternative Methods to Build WorkTime Pro APK

Since you don't have admin access to install Android Studio, here are **4 alternative methods** to build your APK.

---

## ✅ **METHOD 1: Use Command Line (Gradle)** ⭐ **RECOMMENDED**

This method uses Gradle Wrapper which comes with the project - **no installation needed!**

### Prerequisites Check

**Step 1: Check if Java JDK is installed**

Open Command Prompt (cmd) and type:
```cmd
java -version
```

**Expected output:**
```
java version "17.0.x" or higher
```

**If Java is NOT installed:**
- You'll see: `'java' is not recognized as an internal or external command`
- **Solution**: Try Method 2 or Method 3 below

---

### Building APK with Gradle (If Java is installed)

**Step 1: Open Command Prompt**
- Press `Windows + R`
- Type: `cmd`
- Press Enter

**Step 2: Navigate to project folder**
```cmd
cd "C:\Users\Intern Engineering 1\Downloads\WorkTimePro"
```

**Step 3: Check Gradle Wrapper exists**
```cmd
dir gradlew.bat
```

You should see: `gradlew.bat` listed

**Step 4: Build Debug APK**
```cmd
gradlew.bat assembleDebug
```

**What will happen:**
1. Gradle will download Android SDK automatically (no admin needed!)
2. Download all dependencies
3. Build the APK
4. **First time**: Takes 5-10 minutes (downloads ~500MB)
5. **Subsequent builds**: Takes 1-2 minutes

**Step 5: Wait for completion**

You'll see progress:
```
> Task :app:compileDebugKotlin
> Task :app:mergeDebugResources
> Task :app:processDebugManifest
...
BUILD SUCCESSFUL in 3m 45s
```

**Step 6: Find your APK**

APK location:
```
C:\Users\Intern Engineering 1\Downloads\WorkTimePro\app\build\outputs\apk\debug\app-debug.apk
```

**Step 7: Verify APK exists**
```cmd
dir "app\build\outputs\apk\debug\app-debug.apk"
```

You should see the file with size ~5-10 MB.

✅ **DONE! Your APK is ready!**

---

### Troubleshooting Method 1

#### Error: "JAVA_HOME is not set"

**Solution 1: Set JAVA_HOME temporarily**
```cmd
set JAVA_HOME=C:\Program Files\Java\jdk-17
set PATH=%JAVA_HOME%\bin;%PATH%
gradlew.bat assembleDebug
```

**Solution 2: Find Java location**
```cmd
where java
```
This shows Java installation path. Use that path for JAVA_HOME.

#### Error: "SDK location not found"

**This is OK!** Gradle will download SDK automatically to:
```
C:\Users\Intern Engineering 1\.gradle\caches\android-sdk\
```

Just wait for the download to complete.

#### Error: "Permission denied"

**Solution**: Build in a different folder
```cmd
# Copy project to your Documents folder
xcopy "C:\Users\Intern Engineering 1\Downloads\WorkTimePro" "C:\Users\Intern Engineering 1\Documents\WorkTimePro" /E /I /H

# Navigate there
cd "C:\Users\Intern Engineering 1\Documents\WorkTimePro"

# Build
gradlew.bat assembleDebug
```

---

## ✅ **METHOD 2: Use Portable Android Studio** (No Installation!)

If Java is not installed, use a portable version of Android Studio that runs without installation.

### Download Portable Android Studio

**Option A: Android Studio Portable**
1. Go to: https://portableapps.com/node/59489
2. Download: "Android Studio Portable"
3. Extract to: `C:\Users\Intern Engineering 1\Downloads\AndroidStudioPortable`
4. No installation needed!

**Option B: Alternative Portable IDE**
1. Download IntelliJ IDEA Community (portable)
2. Extract and run
3. Open Android project

### Using Portable Android Studio

**Step 1: Extract the portable version**
- Right-click downloaded ZIP
- Extract to a folder (e.g., `Downloads\AndroidStudioPortable`)

**Step 2: Run Android Studio**
- Navigate to extracted folder
- Find: `AndroidStudioPortable.exe` or `studio.bat`
- Double-click to run (no admin needed!)

**Step 3: Open Project**
- File → Open
- Select: `C:\Users\Intern Engineering 1\Downloads\WorkTimePro`

**Step 4: Build APK**
- Build → Build Bundle(s) / APK(s) → Build APK(s)
- Wait for completion
- APK saved in: `app\build\outputs\apk\debug\app-debug.apk`

---

## ✅ **METHOD 3: Online Build Service** (No Software Needed!)

Use online services to build your APK remotely.

### Option A: AppGyver (Free)

**Not suitable for this project** - AppGyver is for no-code apps only.

### Option B: GitHub Actions (Free, Recommended!)

**Upload your project to GitHub and build in the cloud:**

**Step 1: Create GitHub Account**
- Go to: https://github.com
- Sign up (free)

**Step 2: Create New Repository**
- Click: New repository
- Name: `WorkTimePro`
- Public or Private (your choice)
- Click: Create repository

**Step 3: Upload Project Files**
- Click: "uploading an existing file"
- Drag and drop all WorkTimePro files
- OR use GitHub Desktop (portable version available)

**Step 4: Create GitHub Actions Workflow**

Create file: `.github/workflows/build.yml`

```yaml
name: Build APK

on:
  push:
    branches: [ main ]
  workflow_dispatch:

jobs:
  build:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
        
    - name: Grant execute permission for gradlew
      run: chmod +x gradlew
      
    - name: Build Debug APK
      run: ./gradlew assembleDebug
      
    - name: Upload APK
      uses: actions/upload-artifact@v3
      with:
        name: app-debug
        path: app/build/outputs/apk/debug/app-debug.apk
```

**Step 5: Run Workflow**
- Go to: Actions tab
- Click: Build APK
- Click: Run workflow
- Wait 5-10 minutes

**Step 6: Download APK**
- After build completes
- Click on the workflow run
- Download: "app-debug" artifact
- Extract ZIP to get APK

✅ **APK built in the cloud!**

---

## ✅ **METHOD 4: Ask IT Admin / Use Another Computer**

### Option A: Request Admin to Build

**What to tell your IT admin:**

```
Hi,

I need to build an Android APK from a Kotlin project.
Could you please:

1. Install Android Studio on this laptop
   OR
2. Build this APK for me

Project location: C:\Users\Intern Engineering 1\Downloads\WorkTimePro
Command to build: gradlew.bat assembleDebug

The APK will be created at:
app\build\outputs\apk\debug\app-debug.apk

Thank you!
```

### Option B: Use Another Computer

**Computers you can use:**
- Your personal laptop/PC at home
- College/university computer lab
- Friend's computer
- Internet café with Android Studio

**What to do:**
1. Copy WorkTimePro folder to USB drive
2. Take it to another computer with Android Studio
3. Build APK there
4. Copy APK back to USB
5. Transfer to your phone

---

## ✅ **METHOD 5: Pre-Built APK** (I'll Build It!)

**If none of the above methods work**, I can guide you to:

1. Use a cloud build service (GitHub Actions) - I'll set it up
2. Request a colleague/friend with Android Studio to build
3. Use an online Android emulator to test (no APK needed)

---

## 🎯 **RECOMMENDED PATH FOR YOU:**

Since you don't have admin access, here's what I recommend:

### **TRY THIS ORDER:**

1. **First: Try Method 1 (Command Line)**
   - Check if Java is installed: `java -version`
   - If yes: Run `gradlew.bat assembleDebug`
   - **Fastest if it works!** (5-10 minutes)

2. **If Java not installed: Try Method 3 (GitHub Actions)**
   - Upload project to GitHub
   - Set up automated build
   - Download APK from cloud
   - **No local installation needed!**

3. **Last Resort: Method 4 (Ask Admin)**
   - Request admin to install Android Studio
   - OR ask them to build APK for you
   - Takes 1 minute once they help

---

## 📝 **Quick Command Reference**

### Check if you can build without admin:

```cmd
# Check Java
java -version

# If Java exists, build directly:
cd "C:\Users\Intern Engineering 1\Downloads\WorkTimePro"
gradlew.bat assembleDebug

# APK will be at:
# app\build\outputs\apk\debug\app-debug.apk
```

---

## 🔍 **Detailed Steps for Method 1 (Command Line)**

Let me guide you through Method 1 step-by-step:

### **STEP 1: Check Prerequisites**

Open Command Prompt:
```cmd
# Check Java
java -version

# Check Git (optional)
git --version
```

### **STEP 2: Navigate to Project**

```cmd
# Go to project folder
cd "C:\Users\Intern Engineering 1\Downloads\WorkTimePro"

# Verify you're in the right folder
dir

# You should see:
# - app (folder)
# - gradle (folder)
# - gradlew.bat (file)
# - build.gradle (file)
```

### **STEP 3: Make Gradle Wrapper Executable**

```cmd
# This should already be executable, but just in case:
attrib -r gradlew.bat
```

### **STEP 4: Build the APK**

```cmd
# Build debug APK
gradlew.bat assembleDebug
```

**What happens:**
```
Downloading https://services.gradle.org/distributions/gradle-8.0-bin.zip
...........10%...........20%...........30%...........40%
...........50%...........60%...........70%...........80%
...........90%...........100%

Starting a Gradle Daemon (subsequent builds will be faster)

> Task :app:preBuild
> Task :app:preDebugBuild
> Task :app:compileDebugKotlin
> Task :app:mergeDebugResources
> Task :app:processDebugManifest
> Task :app:processDebugResources
> Task :app:compileDebugJavaWithJavac
> Task :app:mergeDebugJavaResource
> Task :app:dexBuilderDebug
> Task :app:mergeDebugDexDebugAndroidTest
> Task :app:packageDebug

BUILD SUCCESSFUL in 8m 32s
45 actionable tasks: 45 executed
```

### **STEP 5: Locate APK**

```cmd
# Check if APK was created
dir "app\build\outputs\apk\debug"

# You should see:
# app-debug.apk (approximately 5-8 MB)

# Copy to easier location
copy "app\build\outputs\apk\debug\app-debug.apk" "%USERPROFILE%\Desktop\WorkTimePro.apk"
```

✅ **APK is now on your Desktop!**

---

## 📤 **Transfer APK to Your Phone**

Since you have the APK now, transfer it to your phone:

### **Method A: Email**
1. Open Gmail
2. Attach `app-debug.apk`
3. Send to yourself
4. Open email on phone
5. Download and install

### **Method B: USB Cable**
1. Connect phone to laptop
2. Copy APK to phone's Download folder
3. Open File Manager on phone
4. Install APK

### **Method C: Cloud Storage**
1. Upload to Google Drive / OneDrive
2. Download on phone
3. Install APK

### **Method D: WhatsApp**
1. WhatsApp Web on laptop
2. Send APK to yourself
3. Download on phone
4. Install

---

## ✅ **SUCCESS CHECKLIST**

After building APK:

- [ ] APK file created (check file size: 5-10 MB)
- [ ] APK transferred to phone
- [ ] Developer options enabled on phone
- [ ] Unknown sources allowed
- [ ] APK installed successfully
- [ ] App opens without crash

---

## 🆘 **STILL STUCK?**

### If Method 1 fails:

**Tell me which error you see, and I'll help you fix it!**

Common errors:
- "java is not recognized" → Use Method 3 (GitHub Actions)
- "Permission denied" → Copy project to Documents and try again
- "SDK not found" → Wait, Gradle will download automatically

### Alternative: I'll Help You Set Up GitHub Actions

If command line doesn't work, let me know and I'll:
1. Create a GitHub Actions workflow for you
2. Help you upload the project
3. Build APK in the cloud (no local installation needed!)

---

## 🎯 **NEXT STEPS**

**Try this now:**

1. Open Command Prompt (Windows + R → type `cmd` → Enter)
2. Type: `java -version` and press Enter
3. **Tell me what you see!**

Based on your response, I'll guide you to the exact method that will work for you!

---

**Remember: You DON'T need admin access to build this APK!** 🚀

There are multiple ways to do it. Let's find the one that works for you!
