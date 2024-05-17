# Cake list app
(1) Cake list loaded and displayed when app started
    a. Remove duplicate entries
    b. Order entries by name
    c. Display image and title for each entry
    d. Display a divider between each entry
(2) Display the cake description in dialog on cake item clicked
(3) Added swipe refresh option that reloads the list
(4) Display an error message if the list cannot be loaded (e.g. no network)
(5) Handle orientation changes using flow and coroutines, ideally without reloading the list
(6) Provide an option to retry when an error like "no internet"
(7) Covered unit test for,
    a. when cakes item are duplicated and sorted by title.
    b. Cake list is success, empty or error case checked.

## Main Features 
Language : Kotlin
Android Architecture : MVVM
Dependency Injection : Hilt
Image loading : Glide
Design and view : XML layout and Databinding
Testing :  Mockito, JUnit and Turbine
