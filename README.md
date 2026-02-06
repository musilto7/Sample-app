# My Flicker Application

A **Kotlin Multiplatform (KMP)** app for browsing Flickr images, with a shared UI built with **Compose Multiplatform** and **Navigation 3**. It runs on **Android** and **iOS** from a single shared codebase.

## Project structure

| Module      | Description |
|------------|-------------|
| **`shared/`** | Kotlin Multiplatform library: domain, data, presentation, and Compose UI. Targets Android + iOS. |
| **`androidApp/`** | Android application that depends on `shared`. |
| **`iosApp/`** | Native iOS app (Xcode) that links the `shared` framework. |

```
MyFlickerapplication/
├── shared/           # KMP module (commonMain + androidMain + iosMain)
├── androidApp/       # Android app
├── iosApp/           # iOS Xcode project + Swift entry
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## Tech stack

- **Kotlin Multiplatform** – shared logic and UI
- **Compose Multiplatform** – UI (Material 3)
- **Navigation 3** – navigation and back stack
- **Koin** – dependency injection
- **Ktor** – HTTP client (OkHttp on Android, Darwin on iOS)
- **Kotlinx Serialization & DateTime**
- **Kamel** – image loading in shared code
- **Lifecycle ViewModel (KMP)** – `ImagesViewModel` in `commonMain`

## Prerequisites

- **JDK 17+**
- **Android Studio** (or IDE with Android tooling) for Android
- **Xcode 15+** on macOS for iOS
- **CocoaPods** is not required

## Running the Android app

From the project root:

```bash
./gradlew :androidApp:installDebug
```

Or open the project in Android Studio, select the **androidApp** run configuration, and run on an emulator or device.

## Running the iOS app

1. **Build the shared framework** (from the project root):

   ```bash
   ./gradlew :shared:assembleReleaseXCFramework
   ```

   Output: `shared/build/XCFrameworks/release/shared.xcframework`

2. **Open the iOS project in Xcode**:

   ```bash
   open iosApp/MyFlickerApp.xcodeproj
   ```

3. Ensure the **MyFlickerApp** target has **shared.xcframework** in **Frameworks, Libraries, and Embedded Content** (Embed & Sign). The project is set up to use `../shared/build/XCFrameworks/release`.

4. Select a simulator or device and press **⌘R** to run.

For more detail (per-architecture frameworks, manual setup), see **[iosApp/README.md](iosApp/README.md)**.

## Building the shared framework (reference)

| Task | Output | Use case |
|------|--------|----------|
| `./gradlew :shared:assembleReleaseXCFramework` | `shared/build/XCFrameworks/release/shared.xcframework` | One artifact for simulator + device (recommended) |
| `./gradlew :shared:linkReleaseFrameworkIosArm64` | `shared/build/bin/iosArm64/releaseFramework/shared.framework` | Device only |
| `./gradlew :shared:linkReleaseFrameworkIosSimulatorArm64` | `shared/build/bin/iosSimulatorArm64/releaseFramework/shared.framework` | Simulator only |

## Running tests

- **Shared (unit) tests** (JVM):  
  `./gradlew :shared:testDebugUnitTest`  
  (or the `shared` test configuration in Android Studio.)

- **Android instrumented tests**:  
  `./gradlew :shared:connectedDebugAndroidTest`  
  (with an emulator or device connected.)

## License

See the repository license file if present.
