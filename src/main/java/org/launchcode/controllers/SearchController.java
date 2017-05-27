package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(value = "results") //request path is search/results

    public String results(Model model, @RequestParam String searchType, @RequestParam String searchTerm) { //Model model is a way to package data from the controller to the client-side (view)

        //pass in parameters from search.html; first parameter specifies the type of search (e.g. all, employer, etc.), second parameter specifies the search term
        //don't need to specify GET or POST? Because the search.html view sends query parameters (type of search and search term) to search/results (you can find these values in the browser inspect elements (Network --> Headers)
        //look up the search results via the JobData class (findByColumnAndValue)

        if (searchType.equals("all")) {

            ArrayList<HashMap<String, String>> jobs = JobData.findByValue(searchTerm);
            model.addAttribute("jobs", jobs);
            model.addAttribute("columns", ListController.columnChoices);
            model.addAttribute("listSize", jobs.size());

            return "search";

        } else {
            ArrayList<HashMap<String, String>> jobs = JobData.findByColumnAndValue(searchType, searchTerm);

            // pass results into search.html view via the model
            model.addAttribute("jobs", jobs);

            //also need to pass ListController.columnChoices to the view, as the method above does --why?? just to display search form again?
            model.addAttribute("columns", ListController.columnChoices);

            model.addAttribute("listSize", jobs.size());

            return "search";
        }

    }
}
