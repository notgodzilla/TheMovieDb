# TheMovieDb
Application using the Movie DB API to search for actors, movies, and TV show

**Current Functionality**
- Able to use the Movie DB API multi-query option and search for movies, tv shows, and actors
- Able to page through results but destroys current instance of SearchActivityResult and creates new instance
- Need to press 'Back' on phone to return to home screen to query for new result

**Known Bugs, Issues, etc**
- Does not account for orientation changes (currently only works with vertical orientation)
- Displays results without images
- Loads all results at once
- All work done on main thread - slow UI

**TODOs**
- Account for orientation change
- Use AsyncTask to fetch results so UI is not slow and does not wait for all results for one page before displaying
- Convert SearchActivity into SearchActivityFragment so that new Activity is not created each time the user needs to page through results
- Hold all results in SearchActivityResultsFragment
- Add search text box on top of results page
