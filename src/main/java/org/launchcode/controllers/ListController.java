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
@RequestMapping(value = "list") //request path is /list (root route)
public class ListController { //class allows users to see lists of all values of given data column (e.g. employer, location, etc.)

    static HashMap<String, String> columnChoices = new HashMap<>(); //creates new HashMap named columnChoices of string keys and string values;

    public ListController () {  //constructor - allows for data to be initialized
        columnChoices.put("core competency", "Skill"); //populate columnChoices wth key/value pairs
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");
    }

    @RequestMapping(value = "")  //request path is /list ("" = root route)
    public String list(Model model) { //adds attributes to the model e.g. JobData.java - are we creating an instance of the Model class? /
                                      // this method simply displays the different types of lists the user can view; doesn't display any actual data taken from JobData

        model.addAttribute("columns", columnChoices);

        return "list"; //renders list.html template; passes any data between controller and view
    }

    @RequestMapping(value = "values") //request path is '/values'
    public String listColumnValues(Model model, @RequestParam String column) { // Model model is a way to package data from the controller to the client-side (view) column gets passed into method from ???? and is used to determine which values to fetch from JobData

        if (column.equals("all")) { //if user chooses all columns,
            ArrayList<HashMap<String, String>> jobs = JobData.findAll();  //create new ArrayList of HashMaps (jobs) by fetching all of the data from JobData class using method findAll
            model.addAttribute("title", "All Jobs"); //pass in title "All Jobs" to list-jobs template (view)
            model.addAttribute("jobs", jobs); //pass in jobs ArrayList of HashMaps to list-jobs template (view)
            return "list-jobs"; //renders list-jobs.html template; passes in any data between controller and view

        } else {
            ArrayList<String> items = JobData.findAll(column); //if user chooses a specific column,
            model.addAttribute("title", "All " + columnChoices.get(column) + " Values"); //pass in title "All (employer, location, etc.) Values" to list-column template (view)
            model.addAttribute("column", column); //pass in column (passed in to listColumnValues as a parameter above) to view
            model.addAttribute("items", items); //pass in items (ArrayList of all items within one column) to view
            return "list-column"; //renders list-column.html template
        }

    }

    @RequestMapping(value = "jobs") //request path is /jobs
    public String listJobsByColumnAndValue(Model model,
            @RequestParam String column, @RequestParam String value) { //new method takes in parameters column and value (from where?)
                                                                       //we get to this handler method by clicking on a link within one of the views, rather than via submitting a form

        ArrayList<HashMap<String, String>> jobs = JobData.findByColumnAndValue(column, value); //makes a new ArrayList of HashMaps (jobs) by fetching data from JobData using findByColumnAndValue method (returns ArrayList of HashMaps (job entries - rows) that contain search-specific value ('Enterprise') from one particular column ('employer', etc.))
        model.addAttribute("title", "Jobs with " + columnChoices.get(column) + ": " + value); //pass in title "Jobs with (employer, location, etc): (search term)" to view
        model.addAttribute("jobs", jobs); //pass in jobs (ArrayList of HashMaps)

        return "list-jobs"; //renders list-jobs template
    }
}
