# WealthWatch

A real-time financial asset tracking application for Android. Monitor stocks, cryptocurrencies, commodities, and exchange rates in one place.

![Platform](https://img.shields.io/badge/Platform-Android-3DDC84?style=flat-square&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-2.0+-7F52FF?style=flat-square&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white)
![Min SDK](https://img.shields.io/badge/Min%20SDK-24-green?style=flat-square)
![Target SDK](https://img.shields.io/badge/Target%20SDK-36-blue?style=flat-square)

---

## Features

| Feature | Description |
|---|---|
| Market Screen | Lists stocks, crypto, commodities, and exchange rates with real-time updates |
| Portfolio | Track your holdings by category and visualize allocation with a pie chart |
| Asset Search | Filter by symbol or name with search history support |
| Watchlist | Save favorite assets for quick access |
| Asset Detail | In-depth price charts and detailed asset information |
| Settings | Theme (Light / Dark / System), language, profile editing, PIN security, notifications |
| WebSocket | Real-time price updates via Socket.IO |

---

## Architecture

The project follows **Clean Architecture** principles with a clear separation between layers:

```
com.example.wealthwatch/
├── data/
│   ├── mapper/          # DTO to domain model mappings
│   ├── remote/          # Retrofit API services & Socket.IO
│   │   ├── crypto/
│   │   ├── stock/
│   │   ├── commodity/
│   │   ├── currency/
│   │   ├── market/
│   │   └── search/
│   ├── repository/      # Repository implementations
│   └── provider/        # Utility providers
│
├── domain/
│   ├── model/           # Business logic models
│   ├── repository/      # Repository interfaces
│   └── usecase/         # Use cases
│
└── presentation/
    ├── market/
    ├── portfolio/
    ├── watchlist/
    ├── search/
    ├── search_asset/
    ├── asset_detail/
    ├── settings/
    │   ├── theme/
    │   ├── language/
    │   ├── security/
    │   ├── notifications/
    │   └── edit_profile/
    ├── navigation/
    └── ui/theme/
```

---

## Tech Stack

**UI**
- Jetpack Compose — Declarative UI toolkit
- Material 3 — Component library with dynamic color support
- Vico — Chart and graph library
- Coil — Asynchronous image loading with SVG support
- Accompanist System UI Controller — Status bar color management

**Architecture & Dependency Injection**
- Hilt — Dependency injection
- ViewModel + StateFlow — Reactive UI state management
- Navigation Compose — Type-safe navigation

**Networking & Data**
- Retrofit — REST API client
- OkHttp — HTTP logging interceptor
- kotlinx.serialization — JSON serialization
- Room — Local SQLite database
- Socket.IO Client — Real-time WebSocket communication

**Async**
- Kotlin Coroutines
- Kotlin Flow

---

## Requirements

- Android Studio Iguana or later
- JDK 17
- Android SDK API 24 (Android 7.0) or higher
- Internet connection for real-time data

---

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/WealthWatch.git
   cd WealthWatch
   ```

2. Open in Android Studio via `File > Open` and select the project directory.

3. Wait for Gradle sync to complete.

4. Select an emulator or physical device and run the app (`Shift + F10`).

---

## Design System

A centralized design system ensures visual consistency across the app:

- `Color.kt` — Primary and secondary color palette for light and dark themes
- `Type.kt` — Typography scale and font hierarchy
- `Dimensions.kt` — Spacing, padding, and size constants
- `Theme.kt` — MaterialTheme configuration

---

## License

This project is licensed under the [MIT License](LICENSE).
