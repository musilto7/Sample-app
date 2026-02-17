# My Flickr Application

A **Kotlin Multiplatform (KMP)** app for browsing Flickr images, with a shared UI built with **Compose Multiplatform** and **Navigation 3**. It runs on **Android** and **iOS** from a single shared codebase.

## Features

- Search Flickr images by tags (comma-separated)
- Tag mode toggle (ALL vs ANY)
- View image details with full-screen display
- Navigation with slide-in/slide-out animations
- State preservation across configuration changes and process death

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

- **Kotlin Multiplatform** – shared logic and UI across Android and iOS
- **Compose Multiplatform 1.10.0** – declarative UI (Material 3 1.10.0-alpha05)
- **Navigation 3** – type-safe navigation with savable back stack
- **Koin** – dependency injection
- **Ktor 3.4.0** – HTTP client (OkHttp on Android, Darwin engine on iOS)
- **Kotlinx Serialization** – JSON serialization for API and navigation state
- **Kotlinx DateTime** – date/time handling
- **Coil 3.1.0** – async image loading (multiplatform with Ktor network fetcher)
- **Lifecycle ViewModel (KMP)** – `ImagesViewModel` in `commonMain` with `SavedStateHandle`
- **OpenAPI Generator** – generates Flickr API client from Swagger spec

### Architecture

- **Clean Architecture** – domain, data, presentation layers
- **MVVM** – `ImagesViewModel` manages UI state via `ImagesScreenStateHolder`
- **Unidirectional data flow** – UI events → ViewModel → Repository → Domain
- **Expect/actual** – platform-specific implementations (HTTP engines, image loading)

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

See **[iosApp/README.md](iosApp/README.md)** for detailed instructions.

**Quick start:**

```bash
# Build the shared framework (debug; copies to release folder for Xcode)
./iosApp/build-xcframework-debug.sh

# Open Xcode project
open iosApp/MyFlickerApp.xcodeproj

# Select simulator or device and press ⌘R to run
```

The `iosApp` folder includes scripts to build debug and release XCFrameworks.

## Running tests

- **Shared (unit) tests** (JVM):  
  `./gradlew :shared:testDebugUnitTest`  
  (or the `shared` test configuration in Android Studio.)

## Project highlights

- **100% shared UI** – all screens (list, detail, navigation) are in `shared/src/commonMain`
- **Multiplatform ViewModel** – uses JetBrains `androidx.lifecycle` for state management on both platforms
- **Savable navigation state** – `rememberNavBackStack` with polymorphic `NavKey` serialization
- **Type-safe navigation** – `ImageListKey` and `ImageDetailKey` with serializable parameters
- **Platform-specific integrations** – OkHttp/Coil on Android, Darwin/Coil on iOS

## Contributing

This is a sample/demo project. Feel free to fork and adapt for your needs.
