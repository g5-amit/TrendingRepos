Trending Git Repo Sample

Functionality:

- Each time Trending Repo Data is fetched first from RoomDatabase and if 
data in DB is empty or Stale, then Fresh data is retrieved from Remote Backend Api.

- Remote Api Data is saved in Room DB after Retrofit Network gives successful response.

- Pull to refresh functionality is used for retrieving fresh data from Remote API 



Architecture Componnents Used:

- Room Database for Offline Support of Data
- RxJava for Reactive Programing
- MVVM Design Pattern Using ViewModels  
- Dagger2 for Dependency Injection
- Glide for image Loading
- Mockito for Unit test cases
- Espresso for UI Test cases
- Retrofit for Rest Api Communication



Testing:

UI Tests
The projects uses Espresso for UI testing. Since each fragment is limited to a ViewModel, each test mocks related ViewModel to run the tests.

Database Tests
The project creates an in memory database for each database test but still runs them on the device.

ViewModel Tests
Each ViewModel is tested using local unit tests with mock Repository implementations.

Repository Tests
Each Repository is tested using local unit tests with mock web service data and mock database.

