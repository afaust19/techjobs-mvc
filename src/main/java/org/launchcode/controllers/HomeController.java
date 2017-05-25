package org.launchcode.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController { //class that displays the home page for the app

    @RequestMapping(value = "") //Request path is / (root route)
    public String index(Model model) { //what is Model used for??

        HashMap<String, String> actionChoices = new HashMap<>(); //creates new HashMap (actionChoices) consisting of string keys and string values
        actionChoices.put("search", "Search"); //adds a key/value pair to the HashMap
        actionChoices.put("list", "List");  //adds a key/value pair to the HashMap

        model.addAttribute("actions", actionChoices);  //passes HashMap object (actionChoices) into view (template)

        return "index";  //renders index.html view; passes any data between controller and view
    }

}
