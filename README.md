# GitPR App

## Desciption
Android App to display All Closed Pull Requests from Github public
repository of your own code.

The App follows clean MVVM architecture

## Features
- Get all closed Pull Requests
- Move from page to page
- See the user, dates and branch immformation

## Screenshot
[![Screenshot-2022-08-21-at-5-43-33-PM.png](https://i.postimg.cc/g0b4St4K/Screenshot-2022-08-21-at-5-43-33-PM.png)](https://postimg.cc/75XgbVFC)

## Uses
- Kotlin
- Retrofit
- ReactiveX
- LiveData
- Robolectric
- Mockito
- Mockk
- Picasso
- Hilt
- ViewBinding

## Architecture

The App follows MVVM clean architecture. It has 3 parts - 
1. Data
2. Domain
3. Presentation

Each has been represented by their packages

**Data** layer is layer is responsible for fetching all the data from the git hub repo. We fetch the data for each page using [Retrofit](https://square.github.io/retrofit/).
To know more about the APIs, read here - [Docs](https://developer.github.com/v3/)
After gathering the data we forward it to Domain Layer. 

**Domain** layer contains all the business logic. It keeps track of the polling interval so that our data remains up to date. It also keeps track of the page we are on.
This layer forwards the data to the Presentation Layer.

**Presentation** layer consists for all the classes responsible for displaying the data. It consists of the activity, viewmodel and the adapter. 

## Links
- [Robolectric](http://robolectric.org/)
- [ReactiveX](https://reactivex.io/)
- [Retrofit](https://square.github.io/retrofit/)
- [Mockito](https://site.mockito.org/)
- [Mockk](https://mockk.io/)
- [Picasso](https://square.github.io/picasso/)
- [Hilt](https://dagger.dev/hilt/)
- [Clean Architecture](https://medium.com/android-dev-hacks/detailed-guide-on-android-clean-architecture-9eab262a9011)



