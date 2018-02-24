# News Suite
This is a sample app that uses MVP Architecture with Dagger 2.

## Introduction
### Functionality
The app is composed of 1 main screen.

#### NewsListFragment
Allows you to fetch News Items from a URL. Each News Item fecthed is kept in the database in News table.

## Building
You can open the project in Android studio and press run. Selecting mockDebug will show 2 items fecthed from a mock db.
Selecting prodDebug will fetch from a remote server.

## Testing
There are 2 testing cases. newsDetails_DisplayedInUi & emptyNewsDetails_DisplayedInUi. newsDetails_DisplayedInUi tests for list items displayed correctly. emptyNewsDetails_DisplayedInUi checks if error string is displayed.
Currently both the test cases are failing, cause of strange java.lang.NoSuchMethodError: error is Test file. Would require some more time to debug this issue.

## Build Variants
There are 3 build variants.
1. mockDebug : Displays list items from a Fake Respository. Shows 2 items in the list
2. prodDebug : Fetches from a remote server.
3. prodRelease : Fetches from a remote server.

### UI Tests
The projects uses Espresso for UI testing.

## Libraries
#### [Android Support Library](https://developer.android.com/topic/libraries/support-library/index.html)
#### [Room Persistence Library](https://developer.android.com/topic/libraries/architecture/room.html)
#### [Dagger 2](https://google.github.io/dagger/android) for dependency injection
#### [Retrofit](http://square.github.io/retrofit/) for REST api communication
#### [Picasso](http://square.github.io/picasso/) for image loading
#### [espresso](https://developer.android.com/training/testing/espresso/index.html) for UI tests
#### [mockito](https://developer.android.com/training/testing/unit-testing/local-unit-tests.html) for mocking in tests
