package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search") //root route;
public class SearchController {

    @RequestMapping(value = "") //root route
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices); //pass in column choices to view
        return "search"; //render search.html
    }

    // TODO #1 - Create handler to process search request and display results

}
