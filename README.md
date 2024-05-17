# Cake list app
- Cake list loaded and displayed when app started
    - Remove duplicate entries
    - Order entries by name
    - Display image and title for each entry
    - Display a divider between each entry
- Display the cake description in dialog on cake item clicked
- Added swipe refresh option that reloads the list
- Display an error message if the list cannot be loaded (e.g. no network)
- Handle orientation changes using flow and coroutines, ideally without reloading the list
- Provide an option to retry when an error like "no internet"
- Covered unit test for,
    - when cakes item are duplicated and sorted by title.
    - Cake list is success, empty or error case checked.

## Main Features 
- Language : Kotlin
- Android Architecture : MVVM
- Dependency Injection : Hilt
- Image loading : Glide
- Coroutines and kotlin Flow api
- Design and view : XML layout and Databinding
- Testing :  Mockito, JUnit and Turbine
- Swipe refresh layout for refresh data
  
