# My Results For The Adyen Android Assignment
The Adyen Android Assignment project now uses FourSquare Web service RESTful API to display nearby
venues based on the current location of the user on a RecyclerView. It also uses Google Maps API to
display the current location of the user on a Google Map.
The app covers all the expected minimal requirements with more extra features (you can see project
code and Git commits).

App link: https://bit.ly/3i9anA4
App screencast demo video link: https://bit.ly/3Ia5XmZ


# Adyen Android Assignment

This repository contains the coding challenge for candidates applying for an Android role at Adyen.

Your task is to implement a small app using the Foursquare Places API that shows a list of venues around the user’s location.
Decide yourself which venue details should be relevant to the user. You have full freedom on how to present data on screen.
We've already added some code to make it a bit easier for you, but you are free to change any part of it.
We are going to check your implementation for understanding Android specifics (like handling configuration changes), UX choices, and overall architecture.
You are free to add any feature or code you want, but we also value quality over quantity, so whatever you decide to do, try to show us your best.

## Setup
Add your Foursquare client ID and secret to `local.gradle`. See `local.gradle.example` for details.
Tip: You can verify your credentials with `src/test/java/com/adyen/android/assignment/PlacesUnitTest.kt`

Minimal requirements:
- Implement permission handling
- Show a list of venues based on the current location
- Apply best practices to handle configuration changes