# TheMovieDb
Application using the Movie DB API to search for actors, movies, and TV show

**Current Functionality**
- Able to use the Movie DB API multi-query option and search for movies, tv shows, and actors
- Only displays first page of the result that is returned from query
- Need to press 'Back' on phone to return to home screen

**Known Bugs, Issues, etc**
- Does not account for orientation changes (currently only works with vertical orientation)
- There is no way to page through all results
- Displays results without images
- Loads all results at once

**TODOs**
- Account for orientation change
- Use AsyncTask to fetch results
- Convert SearchActivity into SearchActivityFragment
- Hold all results in SearchActivityResultsFragment
- Add navigation between result pages
- Add search text box on top of results page
