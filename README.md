# OpenMyGarage
OpenMyGarage is a project of mine. It's goal is to control gates with a RaspberryPi. An ASP.NET Core API runs on the Pi which communicates with the Android endpoint(s). 
## [OpenMyGarageAPI](OpenMyGarageApi/OpenMyGarageApi)
Currently, the API runs on [Azure](https://openmygarageapi.azurewebsites.net/) and provides these services:
```
Register new user
Login

Get logs
Get stored plates
Add stored plate
Remove stored plate
Update stored plate
```
Additionally, we can simulate an entry attempt that results in a new log.
## [OpenMyGarageAndroid](OpenMyGarageAndroid)
The Android client is capable of:
```
View logs
Search between logs (by plate)
Add stored plate
Remove stored plate
Update stored plate
```
It uses a Room database to cache and save the data coming from the API.
