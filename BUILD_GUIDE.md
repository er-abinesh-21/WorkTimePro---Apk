# WorkTime Pro - Build & Deployment Guide

## 📋 Prerequisites

Before building the APK, ensure you have the following installed:

### Required Software
1. **Android Studio** - Hedgehog (2023.1.1) or later
   - Download from: https://developer.android.com/studio
   
2. **Java Development Kit (JDK)** - Version 17 or later
   - Android Studio includes JDK, or download from: https://www.oracle.com/java/technologies/downloads/

3. **Android SDK** - API Level 34
   - Installed via Android Studio SDK Manager

4. **Gradle** - Version 8.0 (included with project)

## 🚀 Quick Start Guide

### Step 1: Open Project in Android Studio

1. Launch **Android Studio**
2. Click **File → Open**
3. Navigate to the `WorkTimePro` folder
4. Click **OK**

### Step 2: Sync Gradle

1. Android Studio will automatically detect the Gradle files
2. Click **Sync Now** if prompted
3. Wait for Gradle sync to complete (may take 2-5 minutes on first run)
4. Ensure no errors appear in the **Build** output

### Step 3: Configure Android SDK

1. Go to **File → Settings** (or **Android Studio → Preferences** on Mac)
2. Navigate to **Appearance & Behavior → System Settings → Android SDK**
3. Ensure **Android 14.0 (API 34)** is installed
4. Click **Apply** if you need to install it

### Step 4: Build Debug APK

#### Option A: Using Android Studio UI

1. Click **Build → Build Bundle(s) / APK(s) → Build APK(s)**
2. Wait for build to complete (progress shown in bottom toolbar)
3. Click **locate** in the notification that appears
4. APK location: `app/build/outputs/apk/debug/app-debug.apk`

#### Option B: Using Terminal in Android Studio

1. Open **Terminal** tab at bottom of Android Studio
2. Run:
   ```bash
   ./gradlew assembleDebug
   ```
3. APK location: `app/build/outputs/apk/debug/app-debug.apk`

### Step 5: Install on Device/Emulator

#### Install on Physical Device

1. Enable **Developer Options** on your Android device:
   - Go to **Settings → About Phone**
   - Tap **Build Number** 7 times
   
2. Enable **USB Debugging**:
   - Go to **Settings → Developer Options**
   - Enable **USB Debugging**
   
3. Connect device via USB

4. In Android Studio:
   - Click **Run → Run 'app'**
   - Select your device from the list
   - App will install and launch automatically

#### Install on Emulator

1. Create an emulator:
   - Click **Tools → Device Manager**
   - Click **Create Device**
   - Select a device (e.g., Pixel 6)
   - Select system image (API 34 recommended)
   - Click **Finish**

2. Run the app:
   - Click **Run → Run 'app'**
   - Select the emulator
   - Wait for emulator to boot and app to install

## 🔐 Building Release APK (Production)

### Step 1: Create Keystore

A keystore is required to sign your release APK.

#### Using Android Studio

1. Click **Build → Generate Signed Bundle / APK**
2. Select **APK** → Click **Next**
3. Click **Create new...** under Key store path
4. Fill in the form:
   - **Key store path**: Choose location (e.g., `C:\keystore\worktime-pro.jks`)
   - **Password**: Enter a strong password (SAVE THIS!)
   - **Alias**: `worktime-pro-key`
   - **Password**: Enter alias password (SAVE THIS!)
   - **Validity**: 25 years (9125 days)
   - **Certificate**:
     - First and Last Name: Your name
     - Organizational Unit: Your team/department
     - Organization: Your company
     - City: Your city
     - State: Your state
     - Country Code: Your country (e.g., US, IN)
5. Click **OK**

#### Using Command Line

```bash
keytool -genkey -v -keystore worktime-pro.jks -keyalg RSA -keysize 2048 -validity 10000 -alias worktime-pro-key
```

**⚠️ IMPORTANT**: Save your keystore file and passwords securely! You'll need them for all future updates.

### Step 2: Build Signed Release APK

#### Using Android Studio

1. Click **Build → Generate Signed Bundle / APK**
2. Select **APK** → Click **Next**
3. Select your keystore file
4. Enter keystore password
5. Select key alias
6. Enter key password
7. Click **Next**
8. Select **release** build variant
9. Check **V1 (Jar Signature)** and **V2 (Full APK Signature)**
10. Click **Finish**
11. APK location: `app/build/outputs/apk/release/app-release.apk`

#### Using Command Line

1. Create `keystore.properties` file in project root:
   ```properties
   storePassword=YOUR_KEYSTORE_PASSWORD
   keyPassword=YOUR_KEY_PASSWORD
   keyAlias=worktime-pro-key
   storeFile=C:/keystore/worktime-pro.jks
   ```

2. Update `app/build.gradle`:
   ```gradle
   android {
       ...
       signingConfigs {
           release {
               def keystorePropertiesFile = rootProject.file("keystore.properties")
               def keystoreProperties = new Properties()
               keystoreProperties.load(new FileInputStream(keystorePropertiesFile))
               
               storeFile file(keystoreProperties['storeFile'])
               storePassword keystoreProperties['storePassword']
               keyAlias keystoreProperties['keyAlias']
               keyPassword keystoreProperties['keyPassword']
           }
       }
       
       buildTypes {
           release {
               signingConfig signingConfigs.release
               minifyEnabled true
               proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
           }
       }
   }
   ```

3. Build:
   ```bash
   ./gradlew assembleRelease
   ```

## 📦 APK Optimization

### Enable ProGuard/R8 (Code Shrinking)

Already configured in `app/build.gradle`:
```gradle
buildTypes {
    release {
        minifyEnabled true  // Enables code shrinking
        proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
    }
}
```

### Reduce APK Size

1. **Enable resource shrinking**:
   ```gradle
   buildTypes {
       release {
           minifyEnabled true
           shrinkResources true  // Add this line
           ...
       }
   }
   ```

2. **Use APK Analyzer**:
   - Build → Analyze APK
   - Select your APK
   - Review size breakdown
   - Identify large resources

## 🧪 Testing Before Release

### Pre-Release Checklist

- [ ] Test on multiple Android versions (API 21, 26, 30, 34)
- [ ] Test on different screen sizes
- [ ] Test in both light and dark mode
- [ ] Test notification permissions (Android 13+)
- [ ] Test alarm scheduling
- [ ] Test after device reboot
- [ ] Test database operations
- [ ] Test share functionality
- [ ] Verify no crashes in Logcat
- [ ] Test with airplane mode
- [ ] Test with low battery mode

### Run Tests

```bash
# Run unit tests
./gradlew test

# Run instrumented tests (requires connected device/emulator)
./gradlew connectedAndroidTest
```

## 📱 Distribution Options

### Option 1: Direct APK Distribution

1. Share the APK file directly
2. Users must enable "Install from Unknown Sources"
3. Good for internal testing or small-scale distribution

### Option 2: Google Play Store

1. Create a Google Play Developer account ($25 one-time fee)
2. Create app listing
3. Upload release APK or AAB (Android App Bundle)
4. Complete store listing (screenshots, description, etc.)
5. Submit for review

### Option 3: Alternative App Stores

- Amazon Appstore
- Samsung Galaxy Store
- F-Droid (for open-source apps)

## 🔍 Troubleshooting

### Common Build Errors

#### Error: "SDK location not found"

**Solution**: Create `local.properties` file in project root:
```properties
sdk.dir=C\:\\Users\\YourUsername\\AppData\\Local\\Android\\Sdk
```

#### Error: "Gradle sync failed"

**Solution**:
1. File → Invalidate Caches / Restart
2. Delete `.gradle` folder in project root
3. Rebuild project

#### Error: "Manifest merger failed"

**Solution**: Check `AndroidManifest.xml` for conflicts. Add to `app/build.gradle`:
```gradle
android {
    ...
    packagingOptions {
        exclude 'META-INF/DEPENDENCIES'
    }
}
```

#### Error: "Room schema export directory not set"

**Solution**: Add to `app/build.gradle`:
```gradle
android {
    ...
    defaultConfig {
        ...
        javaCompileOptions {
            annotationProcessorOptions {
                arguments += ["room.schemaLocation": "$projectDir/schemas".toString()]
            }
        }
    }
}
```

### Build Performance Tips

1. **Enable Gradle daemon**:
   Add to `gradle.properties`:
   ```properties
   org.gradle.daemon=true
   org.gradle.parallel=true
   org.gradle.caching=true
   ```

2. **Increase heap size**:
   ```properties
   org.gradle.jvmargs=-Xmx4096m -XX:MaxPermSize=512m
   ```

3. **Use offline mode** (if dependencies already downloaded):
   - File → Settings → Build, Execution, Deployment → Gradle
   - Check "Offline work"

## 📊 Build Variants

The project supports multiple build variants:

- **debug**: Development build with debugging enabled
- **release**: Production build with ProGuard/R8 optimization

### Create Custom Build Variants

Add to `app/build.gradle`:
```gradle
android {
    ...
    flavorDimensions "version"
    productFlavors {
        free {
            dimension "version"
            applicationIdSuffix ".free"
            versionNameSuffix "-free"
        }
        pro {
            dimension "version"
            applicationIdSuffix ".pro"
            versionNameSuffix "-pro"
        }
    }
}
```

## 🎯 Version Management

### Update Version

Edit `app/build.gradle`:
```gradle
android {
    defaultConfig {
        ...
        versionCode 2        // Increment for each release
        versionName "1.1.0"  // Semantic versioning
    }
}
```

### Version Naming Convention

- **Major.Minor.Patch** (e.g., 1.2.3)
- Major: Breaking changes
- Minor: New features
- Patch: Bug fixes

## 📝 Build Logs

### View Build Logs

1. **Build Output**: View → Tool Windows → Build
2. **Gradle Console**: Click "Toggle view" in Build window
3. **Event Log**: View → Tool Windows → Event Log

### Export Build Report

```bash
./gradlew assembleRelease --scan
```

This generates a build scan URL with detailed information.

## ✅ Final Checklist

Before releasing your APK:

- [ ] Version code and name updated
- [ ] ProGuard rules tested
- [ ] All features tested on real device
- [ ] No hardcoded API keys or secrets
- [ ] Proper permissions requested
- [ ] App icon and name correct
- [ ] Signed with release keystore
- [ ] APK size optimized
- [ ] Tested on minimum SDK version (API 21)
- [ ] Tested on latest Android version
- [ ] Privacy policy prepared (if collecting data)
- [ ] Screenshots and promotional materials ready

## 🆘 Support

For issues or questions:
- Check Android Studio Logcat for errors
- Review Gradle build output
- Consult Android Developer documentation: https://developer.android.com

---

**Happy Building! 🚀**
