# Asteroid Radar App

Asteroid Radar is an app to view the asteroids detected by NASA that pass near Earth, you can view all the detected asteroids in a period of time, their data (Size, velocity, distance to Earth) and if they are potentially hazardous.

The app consists of two screens: A Main screen with a list of all the detected asteroids and a Details screen that displays the data of that asteroid once it´s selected in the Main screen list. The main screen also shows the NASA image of the day to make the app more striking.

The application:

*Includes the Main screen with a list of clickable asteroids
*Includes a Details screen that displays the selected asteroid data once it’s clicked in the Main screen
*Downloads and parses data from the NASA NeoWS (Near Earth Object Web Service) API.
*Once an asteroid is saved in the database, the list of asteroids is displayed
*The asteroids data is cached by using a worker, so it downloads and saves week asteroids in background when device is charging and wifi is enabled, as well as deleted the asteroids data of the previous day
*App works in multiple screen sizes and orientations, also it provides talk back and push button navigation.

# Instructions for using API

To build this project the NASA NeoWS (Near Earth Object Web Service) API is used, which can be found here: https://api.nasa.gov/

In order to run the app, you need an API Key which is provided for you in that same link, just fill the fields in the form and click Signup.
