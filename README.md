# Winpressive Launcher

Winpressive is a modern Android launcher that reimagines the Windows Phone tile aesthetic using Jetpack Compose and Material 3 Expressive.

## Features

- **Metro-style Tiles:** Resizable tiles (Small, Medium, Wide) arranged in a grid.
- **Material 3 Design:** Uses dynamic colors (Monet) and expressive shapes.
- **Clean Architecture:** Built with MVVM, Hilt, and Kotlin Coroutines.
- **App Drawer:** Searchable list of all installed apps.
- **Performance:** Efficient icon caching with Coil.

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose (Material 3)
- **DI:** Hilt
- **Persistence:** DataStore (Settings) & In-Memory (Mock Layout for v1)
- **Image Loading:** Coil

## How to Build

1. Open the project in Android Studio Iguana or later.
2. Sync Gradle files.
3. Run on an emulator or device running Android 11+ (API 30+).

## Architecture

The app follows a standard Clean Architecture approach:
- `core/model`: Shared data structures.
- `data`: Repositories and data sources.
- `domain`: Use cases (business logic).
- `ui`: Composable screens and ViewModels.
