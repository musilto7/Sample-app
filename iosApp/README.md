# iOS Application

This folder contains the native iOS app that hosts the shared Kotlin Multiplatform (Compose) UI.

## Prerequisites

- Xcode 15+
- macOS with Apple Silicon or Intel
- Kotlin/Native framework built from the root project

## Building the shared framework

From the **project root** (MyFlickerapplication), run:

**Option 1: XCFramework (one artifact for simulator and device)**

```bash
./gradlew :shared:assembleSharedReleaseXCFramework
# or both debug and release:
./gradlew :shared:assembleSharedXCFramework
```

Output: `shared/build/XCFrameworks/release/shared.xcframework`  
Use this single path in Xcode for both simulator and device.

**Option 2: Separate frameworks per target**

```bash
# Both targets
./gradlew :shared:linkReleaseFrameworkIosArm64 :shared:linkReleaseFrameworkIosSimulatorArm64

# Device only (iosArm64)
./gradlew :shared:linkReleaseFrameworkIosArm64

# Simulator only (Apple Silicon)
./gradlew :shared:linkReleaseFrameworkIosSimulatorArm64
```

Output:
- Device: `shared/build/bin/iosArm64/releaseFramework/shared.framework`
- Simulator: `shared/build/bin/iosSimulatorArm64/releaseFramework/shared.framework`

## Setting up the Xcode project

An Xcode project is provided in this folder.

1. **Build the shared framework first** (from the **project root**):
   ```bash
   ./gradlew :shared:assembleSharedReleaseXCFramework
   ```
2. Open **`iosApp/MyFlickerApp.xcodeproj`** in Xcode (double‑click or `File → Open`).
3. The project already references **shared.xcframework** at `../shared/build/XCFrameworks/release/shared.xcframework` (embedded in the app target). No extra framework search paths are needed.
4. Select the **MyFlickerApp** scheme, choose a simulator or device, and run (⌘R).

If you prefer to set up manually from a new project:
1. Open Xcode and create a new **App** project.
2. Set the **Framework Search Paths** (or add the framework):
   - **XCFramework:** `$(SRCROOT)/../shared/build/XCFrameworks/release` — one path for both simulator and device.
   - **Separate frameworks:** `$(SRCROOT)/../shared/build/bin/iosArm64/releaseFramework` and `$(SRCROOT)/../shared/build/bin/iosSimulatorArm64/releaseFramework`.
3. Add **shared.xcframework** (or **shared.framework**) to the app target: **General → Frameworks, Libraries, and Embedded Content** → **+** → **Add Other** → select the framework. Set to **Embed & Sign**.
4. Ensure the app target has **Build Phases → Link Binary With Libraries** including the framework.
5. Build and run on simulator or device.

## Running from terminal (simulator)

After building (XCFramework or simulator framework):

```bash
./gradlew :shared:assembleSharedReleaseXCFramework
# or
./gradlew :shared:linkReleaseFrameworkIosSimulatorArm64
```

Then open the Xcode project, select an iOS simulator, and run (⌘R).

## Kotlin entry point

The Compose UI is provided by `MainViewController()` in the shared framework. From Swift it is called as:

```swift
MainViewController_iosKt.MainViewController()
```

This returns a `UIViewController` that hosts the full Compose Multiplatform UI.
