Android app for fetching the APOD list of NASA API (https://api.nasa.gov/) for a date given.
Displaying and managing of Favourite list

In gradle properties file add value of 
api_key="[YOUR_API_KEY]" and
base_url="[NASA_API]" 

USED: 
    Language: Kotlin,
    Design Pattern: MVVM,
    Database: Room,
    Network: OkHttp, Retrofit,
    Dependency injection: Dagger2,
    Memory Leak library: LeakCanary,

Supporting DayNight mode, handles Different screen size, survives screen rotation, display previously loaded data if Internet is not available