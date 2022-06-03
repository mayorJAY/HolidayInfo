<p align="center">
  <img src="app_icon.png" title="App Logo">
</p>

# HolidayInfo

HolidayInfo is an Application that fetches and displays the various holidays observed in different Countries and different years. The App makes use of the [M30 API](https://m3o.com/holidays/api). It contains a Login page, a Home page showing a list of Countries and a Holiday details page which is displayed when a country and year is selected. It is implemented using Clean Architecture, Model-View-ViewModel pattern (MVVM) and uses Modern Android Development pattern and libraries. Adequate Unit Tests were implemented in the codebase.

## Project Characteristics

This application has the following characterisitcs:
* 100% Kotlin
* Modern Architecture (Clean Architecture, Model-View-ViewModel)
* [Android Jetpack Components](https://developer.android.com/jetpack)
* [Material Design](https://material.io/develop/android/docs/getting-started)

## Tech Stack

Minimum API level is set to 21, this means HolidayInfo can run on approximately 98% of Android devices
* [Retrofit](https://square.github.io/retrofit/) which is a type-safe REST client for Android which makes it easier to consume RESTful web services
* [Kotlin Coroutines](https://developer.android.com/kotlin/coroutines) used to perform asynchronous calls to the device storage
* [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) used to store and manage UI-related data in a lifecycle conscious way
* [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) which is an observable data holder class used to handle data in a lifecycle-aware manner
* [View Binding](https://developer.android.com/topic/libraries/view-binding) used to easily write code that interacts with views by referencing them directly
* [Material Design](https://material.io/develop/android/docs/getting-started/) which is an adaptable system that guides in maintaining principles and best practices of contemporary UI
* [SDP/SSP](https://github.com/intuit/sdp) which is a scalable size unit that scales with the screen size. It helps to easily design for multiple screens
* [JUnit4](https://junit.org/junit4), a testing framework used for writing unit tests
