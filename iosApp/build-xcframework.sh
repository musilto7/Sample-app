#!/usr/bin/env bash
set -euo pipefail

# Build the shared Kotlin Multiplatform XCFramework for iOS.
# Run from anywhere; script resolves the repo root relative to iosApp.

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "$SCRIPT_DIR/.." && pwd)"

echo "Building shared XCFramework (repo root: $REPO_ROOT)"
cd "$REPO_ROOT"

./gradlew :shared:assembleSharedReleaseXCFramework

OUTPUT="$REPO_ROOT/shared/build/XCFrameworks/release/shared.xcframework"
if [[ -d "$OUTPUT" ]]; then
  echo "Done. XCFramework: $OUTPUT"
else
  echo "Expected XCFramework not found at: $OUTPUT" >&2
  exit 1
fi
