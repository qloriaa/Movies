package cst323.activity.controller;

import cst323.activity.model.MovieModel;
import cst323.activity.model.SearchModel;
import cst323.activity.service.MovieService;
import jakarta.validation.Valid;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/movies")
public class MovieController {

    // Implements CRUD operations
    @Autowired
    private MovieService service;

    // --- VIEW ALL ---

    /**
     * "/movie/" - movie entries directory
     * Reached by selecting the "View All" option in the navigation bar menu.
     * 
     * @param model is used to set page layout attributes.
     * @return "movies" view to list movies.
     */
    @GetMapping(value = { "", "/" })
    public String getAll(Model model) {

        model.addAttribute("title", "View All Movie Ratings");
        model.addAttribute("movies", service.getAll());

        return "movies";
    }

    // --- CREATE ---

    /**
     * Display form to add new Movie review entry.
     * 
     * @param model used to set page layout attributes
     * @return "newMovie" view
     */
    @GetMapping("/new")
    public String createNew(Model model) {

        model.addAttribute("title", "New Movie Entry");
        model.addAttribute("movieModel", new MovieModel());

        return "newMovie";
    }

    /**
     * Validate and create new entry.
     * 
     * @param movie         instance of movie based on user input
     * @param bindingResult used to validate input
     * @param model         used to set page attributes
     * @return success= home page, fail= refresh page
     */
    @PostMapping("new")
    public String createNew(@Valid MovieModel movie, BindingResult bindingResult, Model model) {

        // Invalid user input - return the same page for user to fix errors
        if (bindingResult.hasErrors()) {
            model.addAttribute("movieModel", movie);
            return "newMovie";
        }

        // ELSE - New entry created successfully
        service.saveMovie(movie);

        // return "redirect:/movies/";

        // redirect to home page and output entry Id for debugging
        return "redirect:/movies";
    }

    /**
     * View details of selected Entry.
     * 
     * @param id    Unique identifier
     * @param model used to set page attributes
     * @return "viewMovie" view
     */
    @GetMapping("view/{id}")
    public String viewMovieEntry(@PathVariable("id") Long id, Model model) {
        // Get instance
        MovieModel movie = service.getMovie(id);

        // Not Found
        if (movie == null) {
            JOptionPane.showMessageDialog(null, "Entry not found.");
            return "redirect:/movies";
        }

        System.out.println("MOVIE FOUND : " + movie.getId().toString());

        // Found
        model.addAttribute("title", "Movie Review");
        model.addAttribute("movie", movie);

        if (!movie.getImage().isEmpty()) {
            model.addAttribute("url", movie.getImage());
            System.out.println(movie.getImage() + "...");
        }

        return "viewMovie";
    }

    // --- UPDATE ---

    /**
     * Display update form.
     * 
     * @param id    unique identifier
     * @param model used to set page attributes
     * @return "updateMovie" form
     */
    @GetMapping("update/{id}")
    public String updateMovie(@PathVariable("id") Long id, Model model) {
        MovieModel movie = service.getMovie(id);

        // Not Found
        if (movie == null) {
            JOptionPane.showMessageDialog(null, "Movie entry not found.");
            return "redirect:/movies";
        }

        // Found
        model.addAttribute("title", "Update Movie Review");
        model.addAttribute("movie", movie);

        return "updateMovie";
    }

    /**
     * Validate update form
     * 
     * @param movie         instance of user input
     * @param bindingResult used for validation
     * @param model         used to ste page attributes
     * @return successful= homepage, fail=update form
     */
    @PostMapping("update")
    public String updateMovie(@Valid MovieModel movie, BindingResult bindingResult, Model model) {
        // Invalid user input - return the same page for user to fix errors
        if (bindingResult.hasErrors()) {
            model.addAttribute("title", "Update Movie Review");
            model.addAttribute("movie", movie);

            // refresh form
            return "updateMovie";
        }

        // Valid user input
        service.updateMovie(movie);

        return "redirect:/movies";
    }

    // --- DELETE ---

    /**
     * Delete selected Movie entry.
     * 
     * @param id    unique identifier
     * @param model used to set page attributes
     * @return homepage
     */
    @GetMapping("delete/{id}")
    public String deleteMovie(@PathVariable("id") Long id, Model model) {

        MovieModel movie = service.getMovie(id);

        // If not found
        if (movie == null) {
            JOptionPane.showMessageDialog(null, "Error deleting entry.");
        }

        service.deleteMovie(movie);

        return "redirect:/movies";
    }

    // --- SEARCH ---

    @GetMapping("/search")
    public String search(Model model) {

        model.addAttribute("title", "Search Movie Entries");
        model.addAttribute("searchModel", new SearchModel());

        return "search";
    }

    @PostMapping("search")
    public String search(@Valid SearchModel searchTerms, BindingResult bindingResult, Model model) {

        model.addAttribute("title", "Search Results");
        model.addAttribute("movies", service.search(searchTerms));

        return "movies";
    }
}
