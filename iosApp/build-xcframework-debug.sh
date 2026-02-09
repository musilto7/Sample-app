#!/usr/bin/env bash
set -euo pipefail

# Build the shared Kotlin Multiplatform XCFramework for iOS (debug) and copy it
# to the release folder so Xcode (which points at release) uses the debug build
# without changing the project.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"
RELEASE_DIR="$REPO_ROOT/shared/build/XCFrameworks/release"
DEBUG_DIR="$REPO_ROOT/shared/build/XCFrameworks/debug"

echo "Building shared XCFramework (debug) (repo root: $REPO_ROOT)"
cd "$REPO_ROOT"

./gradlew :shared:assembleSharedDebugXCFramework

if [[ ! -d "$DEBUG_DIR/shared.xcframework" ]]; then
  echo "Expected XCFramework not found at: $DEBUG_DIR/shared.xcframework" >&2
  exit 1
fi

echo "Copying debug XCFramework to release folder for Xcode..."
rm -rf "$RELEASE_DIR/shared.xcframework"
mkdir -p "$RELEASE_DIR"
cp -R "$DEBUG_DIR/shared.xcframework" "$RELEASE_DIR/"

echo "Done. Xcode will use: $RELEASE_DIR/shared.xcframework (debug build)"
