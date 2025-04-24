package cst323.activity.controller;

import cst323.activity.model.MovieModel;
import cst323.activity.service.MovieService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/movies")
public class MovieController {

    // Implements CRUD operations
    @Autowired
    private MovieService service;

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

    @GetMapping("/search")
    public String Search(Model model) {

        return "search";
    }

    /**
     * Display form to add new Movie review entry.
     * 
     * @param model used to set page layout attributes
     * @return "newMovie" view
     */
    @GetMapping("/new")
    public String CreateNew(Model model) {

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
    public String CreateNew(@Valid MovieModel movie, BindingResult bindingResult, Model model) {

        // Invalid user input - return the same page for user to fix errors
        if (bindingResult.hasErrors()) {
            model.addAttribute("movieModel", movie);
            return "newMovie";
        }

        // ELSE - New entry created successfully
        service.saveMovie(movie);

        // redirect to home page and output entry Id for debugging
        return "redirect:/movies/" + movie.getId().toString();
    }
}
