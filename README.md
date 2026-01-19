# 🌍 Global Wallet Explorer

**Global Wallet Explorer** is a high-performance mobile application built with **Kotlin Multiplatform (KMP)**. It allows users to explore global currencies, monitor real-time exchange rates, and track financial data with a focus on efficiency and scalability.

This project demonstrates the power of a shared Kotlin codebase between **Android and iOS**, utilizing modern networking and dependency injection patterns.

## 🛠 Tech Stack

- **Kotlin Multiplatform (KMP):** Shared business logic and networking across platforms.
- **Compose Multiplatform / Jetpack Compose:** Modern, declarative UI.
- **Apollo GraphQL Kotlin:** Type-safe API consumption for [swop.cx](https://swop.cx/).
- **Koin:** Lightweight and efficient Dependency Injection for KMP.
- **Apollo Normalized Cache:** Multi-layered caching (In-memory + SQLite) to optimize API quota and enable offline support.
- **KotlinX Serialization:** High-performance JSON processing.


## 🏗 Architecture & Design Patterns

The project follows **Clean Architecture** principles, ensuring separation of concerns and testability:

* `:composeApp`: UI layer and ViewModels.
* `:core:network`: Apollo client configuration, normalized cache management, and security interceptors.
* `:core:shared`: Shared domain models and internationalization utilities.

## 🔒 Security & API Management

To protect the API quota (1,000 requests/month), this project implements:
1.  **Normalized Caching:** Dramatically reduces the number of network calls by persisting data in a local SQLite database.
2.  **Secret Management:** API keys are stored in `local.properties` (ignored by Git) and injected at build time, ensuring no sensitive data is ever exposed in the repository.

## 🚀 Getting Started

1.  Get your free API Key at [swop.cx](https://swop.cx/).
2.  Add the key to your `local.properties` file:
    ```properties
    SWOP_API_KEY=your_api_key_here
    ```
3.  Sync Gradle and run the project:
    - Android: `./gradlew :composeApp:installDebug`
    - iOS: Open the `iosApp` folder in Xcode.

## 🌍 Internationalization (i18n)

The app is fully internationalized, supporting dynamic locale switching, localized currency formatting, and multi-language support from the ground up.

---
## 🗺️ Roadmap
### Phase 1: Foundation (Completed ✅)
- [x] Initial Project Setup with **Kotlin Multiplatform**.
- [x] **Apollo GraphQL** integration for remote data fetching.
- [x] **Koin** Dependency Injection implementation.
- [x] Basic **Internationalization (i18n)** support.
- [x] Global Currency List screen.

### Phase 2: Testing & Reliability (In Progress 🏗️)
- [ ] **Unit Testing:** Ensuring business logic and ViewModels are covered with JUnit and Kotlin Test.
- [ ] **Compose UI Testing:** Implementing UI tests with Semantics and Test Tag to ensure component reliability.
- [ ] **Snapshot Testing with Paparazzi:** Verifying UI consistency and internationalization (i18n) across different device sizes and locales without an emulator.

### Phase 3: Optimization & UX (Planned 🚀)
- [ ] **Normalized Cache** (SQLite) for offline-first capabilities and API quota saving.
- [ ] Real-time **Currency Converter** tool.
- [ ] Search and Filter functionality for the currency list.
- [ ] Pull-to-refresh and Loading states with Shimmer effect.

### Phase 4: Advanced Features 🚀
- [ ] **Historical Data Charts** using a specialized charting library.
- [ ] User **Watchlist** for favorite currencies.
- [ ] Dark Mode support and UI polishing.
---
Developed with ❤️ by [Roberley Pereira] - 2026