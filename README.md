# GoogleSquare

GoogleSquare is a native Android project whose app uses Google Maps API to display the current
location of the user on a Google map. It also uses the FourSquare Web Service RESTful API to display
nearby venues based on the current location of the user.

There's proper handling of location permissions as well as screen rotation configuration changes.

The user gets informed whether or not there's active internet connectivity via a Toast. A SnackBar
is displayed with an opportunity to refresh the map when the user navigates to the map screen
without an active internet connectivity. If the user turns on internet connectivity, the map
refreshes automatically. If the user visits the the nearby venues screen for the first time without
an active internet connectivity, a SnackBar is displayed with an opportunity to refresh the venues
screen since at this point there would be no venues data to display. When the user turns on internet
connectivity, they would have to manually refresh the screen via the SnackBar unless s/he navigated
to the screen with an active internet connectivity. Subsequent visits of the venues screen, one of
two things would happen: if the user visits the screen with an active internet connectivity, the
latest list of nearby venues from the aforesaid web service would be displayed. But if the user
visits the screen without an active internet connectivity, cached copies of nearby venues would be
displayed whenever the user visits the screen while offline.

## Project Tech-stack and Characteristics

* Android Framework
* Kotlin
* MVVM Architectural Design Pattern
* Repository Pattern
* Single Activity App Architecture (just one Activity in the whole app with multiple Fragment classes.)
* Jetpack
* Navigation Components
* Proper Navigation Handling
* Navigation Destinations Back-stack Management
* Proper Configuration Changes Handling (screen rotation)
* Proper Permissions Handling (location)
* SafeArgs
* LiveData
* Offline Storage (via Room)
* Retrofit
* Moshi
* Coroutines
* ViewModel
* Google Maps API
* Location
* FourSquare Web Service RESTful API
* Glide
* Data Binding (very strict)
* Binding Adapters
* Binding Expressions
* RecyclerView DiffUtil
* Broadcast Receiver (for internet connectivity type [e.g.something happens when internet connectivity is toggled on/off])
* Internet Connectivity Checks (whether internet connectivity is on/off)
* Unit Testing
* AndroidX Test
* Hamcrest
* Robolectric
* Architecture Components Core Testing
* Coroutines Test

## Todo

 - [ ] Instrumentation Test
 - [ ] Dependency Injection (Dagger2, Hilt)
 - [ ] Modularization
 - [ ] More...

## Architecture

This is a sample project that presents a good starting point for a modern approach to Android app
development. For a single module app, this project lets you kickstart a Single Activity Architecture
based Android app development with the support of MVVM architectural design and Repository patterns.

### Project Structure

The project contains 3 layers with distinct set of responsibilities.

#### UI Layer

This layer is closest to what the user sees on the screen. The UI layer contains Jetpack ViewModel +
LiveData used to preserve data across Activity restart.

Components:
- **View (Fragment)** - presents data on the screen and pass user interactions to the ViewModel.
Views are hard to test, so they should be as simple as possible.
- **ViewModel** - dispatches (through LiveData) the state changes to the view and deals with user
interactions (these ViewModels are not simply Plain Old Java Object [POJO] classes.)
- **UiModel** - defines the structure of the data that will be used within the UI layer.
- **UiModelMapper** - is used to convert from domain model to UI model (to keep UI layer independent
from other layers).

#### Domain Layer

This is the core layer of the application.

Components:
- **Model** - defines the core structure of the data that will be used within the application. This
is the source of truth for application data.
More importantly, the domain model mediates between the data models from other layers. For example,
if the UI layer needs to use data from the local data source (e.g. Room database) in the Data layer,
the data is first converted from entity (data model for local data source) to domain model and then
to UI model. The conversion process is the reverse if the local data source in the Data layer needs
data from the UI layer. Also if the UI layer needs to use data directly from the remote data source
(e.g. web service via Retrofit) in the Data layer, the data is first converted from data transfer
object (data model for remote data source) to domain model and then to UI model. The conversion
process is the reverse if the remote data source in the Data layer needs data from the UI layer.
Even within the same layer, the domain model mediates between the models from different data
sources. For example, in the Data layer, if the local data source needs data from the remote data
source, the data is first converted from the data transfer object to the domain model and then to
entity. The conversion process is the reverse if the remote data source needs data from the local
data source.
This further strengthens decoupling between UI and Data layers and data sources within the Data layer.
- **ModelMapper** - is used to convert from data models from every other layers to domain data model.
- **Repository interface** - required to keep the UI layer independent from the Data layer 
(Dependency inversion). This is also very useful for testing.
- **DataSource interface** - required for testing.

#### Data Layer

Manages application data and exposes data sources as repositories to the UI layer. Typical
responsibilities of this layer would be to retrieve data from the internet and optionally cache this
data locally.

Components:
- **DataTransferObjectModel** - defines the structure of the data retrieved from the internet and
may contain annotations, so Retrofit/Moshi knows how to parse this internet
data (XML, JSON, Binary...) into objects.
- **DataTransferObjectMapper** - is used to convert from domain model to data transfer object (to
keep the Data layer's remote data source independent from other layers and data sources).
- **Entity** - defines the structure of the data retrieved from or saved to the local database.
- **EntityMapper** - is used to convert from domain model to entity (to keep the Data layer's local
data source independent from other layers and data sources).
- **Repository class** - exposes data to the UI layer.
- **Retrofit Service** - defines a set of API endpoints.

## Upcoming Improvements

Checklist of all upcoming [enhancements](https://github.com/emperorfin/GoogleSquare/issues?q=is%3Aissue+is%3Aopen+sort%3Aupdated-desc+label%3Aenhancement).

## Getting Started

### Command-line
Run git clone https://github.com/emperorfin/GoogleSquare.git command to clone the project.

### Setup
1. Add your Google Maps API key to `local.properties`. See `local.properties.example` for details.
2. Add your Foursquare API key to `local.gradle`. See `local.gradle.example` for details. Tip: You
can verify your credentials with `src/test/java/emperorfin/android/GoogleSquare/PlacesUnitTest.kt`

## Note
You may notice some empty packages, unused classes (e.g. sample data generator) and functions. The
empty packages indicate ongoing project improvements. Working with sample data first is faster
during development. When everything is fine, then the sample data is dropped and then the real data
is directly worked with. I don't recommend removing such sample data generator classes after use as,
if something goes wrong while using real data for example, they may be very helpful.

