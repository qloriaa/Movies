package cst323.activity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// Access to all the options in the navigation bar.
@Controller
public class HomeController {

    /**
     * "/" - Application's root directory
     * The root directory of the site will be the home view.
     * 
     * @param model
     * @return "index" view
     */
    @GetMapping("/")
    public String display(Model model) {
        model.addAttribute("title", "Welcome!!");

        return "index";
    }

    /**
     * "/home" - Home directory
     * reached by clicking the "Home" option in the navigation bar menu.
     * 
     * @param model
     * @return "index" view
     */
    @GetMapping("/home")
    public String displayHome(Model model) {
        model.addAttribute("title", "Home");

        return "index";
    }

}