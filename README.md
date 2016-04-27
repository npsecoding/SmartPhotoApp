# SmartPhotoApp

This android app allows photography fanatics to log points of interest and monitor the weather and lighting conditions at those locations.
The user logs GPS points using a hardware module and syncs the points to the android app.

Following the MVC design pattern I completed the View and Controller portions for the app, below is a breakdown of views:
- A custom splash screen to give time for app to initialize  
- History of Points view displaying all the points logged by the user in a list of cards, each point is displayed in a card with the street view image and city of the location, clicking on a card transitions to a marker on the Map View
- Map View has red markers at each logged location on a zoomable gesture controlled map, each marker has a tooltip summary of the weather at the location, clicking on the tooltip transitions to the POI(Point of Interest) View with specific information for that point
- POI View contains in depth weather information for the weather now(today), tomorrow, and two days later 
- Setting View to allow the user to sync new GPS points logged by hardware system or to fetch new weather data for the POI View

All the views displayed data from data structures and reacted to user input by modifying the data structures.
