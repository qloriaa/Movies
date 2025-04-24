package cst323.activity.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cst323.activity.model.MovieModel;
import cst323.activity.model.SearchModel;

/***
 * Business service responsible for all CRUD operations
 */
@Service
public class MovieService {

    // Repository to access database (via direct injection)
    @Autowired
    private MovieRepository movieRepository;

    /***
     * CREATE/SAVE - create a new movie entry or save changes to existing
     * 
     * @param movie
     * @return
     */
    public long saveMovie(MovieModel movie) {
        try {
            // save/add
            movieRepository.save(movie);

            System.out.println("Movie Added: " + movie.getTitle());

            return movie.getId();
        } catch (Exception ex) {
            ex.printStackTrace();

            return -1;
        }

    }

    /***
     * READ - Get All the Movie entries in Database
     * 
     * @return
     */
    public List<MovieModel> getAll() {
        try {
            List<MovieModel> moviesFound = movieRepository.findAll();
            System.out.println("List of movies found.");
            return moviesFound;
        } catch (Exception ex) {

            ex.printStackTrace();
            System.out.println("Error getting list of movies.");
            return null;
        }
    }

    /***
     * READ - Select one Movie entry in database using ID.
     * 
     * @param Id
     * @return
     */
    public MovieModel getMovie(Long Id) {

        Optional<MovieModel> movie = movieRepository.findById(Id);

        try {
            MovieModel movieFound = movie.get();

            return movieFound;
        } catch (Exception ex) {
            ex.printStackTrace();

            return null;
        }
    }

    /***
     * UPDATE - Update contents using JpaRepository save(T) which automatically
     * updates object if it already exists.
     * 
     * @param movie Updated plant instance.
     * @return
     */
    public long updateMovie(MovieModel movie) {
        return saveMovie(movie);
    }

    /***
     * DELETE - Delete selected Movie entry from database.
     * 
     * @param movie
     * @return
     */
    public boolean deleteMovie(MovieModel movie) {

        try {
            movieRepository.delete(movie);

            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            ;

            return false;
        }
    }

    /***
     * READ - Search all movies matching search term.
     * 
     * @param searchTerm
     * @return
     */
    public List<MovieModel> search(SearchModel searchTerms) {
        List<MovieModel> resultsList = new ArrayList<>();

        try {
            // Search all criteria to generate a list
            if (searchTerms.getTitle() != null) {
                resultsList.addAll(movieRepository.findByTitleContainsIgnoreCase(searchTerms.getTitle()));
            }
            if (searchTerms.getYear() != null) {
                resultsList.addAll(movieRepository.findByYear(searchTerms.getYear()));
            }
            if (searchTerms.getGenre() != null) {
                resultsList.addAll(movieRepository.findByGenreContainsIgnoreCase(searchTerms.getGenre()));
            }
            if (searchTerms.getRating() != null) {
                resultsList.addAll(findByRating(searchTerms.getRating()));
            }

            return resultsList;

        } catch (Exception ex) {
            ex.printStackTrace();

            return null;
        }

    }

    /**
     * Helper method to return all movie entries
     * with a rating equal or higher than the search term
     * 
     * @param rating
     * @return
     */
    public List<MovieModel> findByRating(int rating) {
        List<MovieModel> movies = getAll();
        List<MovieModel> resultsList = new ArrayList<>();

        for (MovieModel movie : movies) {
            if (movie.getRating() >= rating) {
                resultsList.add(movie);
            }
        }

        return resultsList;
    }

}