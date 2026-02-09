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
- **Coil** – image loading (Android; iOS uses placeholder until Coil 3 multiplatform)
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

See **[iosApp/README.md](iosApp/README.md)** for how to build the shared framework and run the app in Xcode.

## Running tests

- **Shared (unit) tests** (JVM):  
  `./gradlew :shared:testDebugUnitTest`  
  (or the `shared` test configuration in Android Studio.)

- **Android instrumented tests**:  
  `./gradlew :shared:connectedDebugAndroidTest`  
  (with an emulator or device connected.)

## License

See the repository license file if present.
