package org.launchcode.models;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by LaunchCode
 */
public class JobData {

    private static final String DATA_FILE = "job_data.csv"; //private = can only be used by methods in class JobData; static = shared by all instances of JobData class; final = value cannot change; initiates a new string that contains all the characters from job_data.csv
    private static boolean isDataLoaded = false; //private = can only be used by methods in class JobData; static = shared by all instances of JobData class
    private static ArrayList<HashMap<String, String>> allJobs; //makes an ArrayList of HashMaps; each HashMap contains a collection of key/value pairs (e.g. key = location, value = Saint Louis, etc.)


    /**
     * Fetch list of all values from loaded data,
     * without duplicates, for a given column.
     *
     * @param field The column to retrieve values from
     * @return List of all of the values of the given field
     */


    public static ArrayList<String> findAll(String field) { //method to return ArrayList of Strings that contains all data (values) from a given column, named 'field' (name, location, employer, etc.)

        loadData(); //load data, if not already loaded

        ArrayList<String> values = new ArrayList<>(); //makes new ArrayList to store values of a specific column

        for (HashMap<String, String> row : allJobs) { //iterates over each HashMap (job entry, named 'row') in allJobs (ArrayList)
            String aValue = row.get(field); //gets the key from each HashMap, stores in variable 'aValue'; each key is a column ('field') e.g. employer (field is a parameter that is passed into the method)

            if (!values.contains(aValue)) { // if ArrayList values does not already contain the key (column, e.g. employer),
                values.add(aValue);         // then add it to the ArrayList
            }
        }

        Collections.sort(values); // Bonus mission: sort the results

        return values;  // returns the ArrayList of the column (names of employers, etc.)
    }


    public static ArrayList<HashMap<String, String>> findAll() {  //method to return ArrayList of HashMaps (find all job entries)
        {

            loadData(); // load data, if not already loaded

            return new ArrayList<>(allJobs); // Bonus mission; normal version returns allJobs (ArrayList of HashMap - all job entries)
        }
    }


    /**
     * Returns results of search the jobs data by key/value, using
     * inclusion of the search term.
     *
     * For example, searching for employer "Enterprise" will include results
     * with "Enterprise Holdings, Inc".
     *
     * @param column   Column that should be searched.
     * @param value Value of teh field to search for
     * @return List of all jobs matching the criteria
     */


    public static ArrayList<HashMap<String, String>> findByColumnAndValue(String column, String value) {  //method to return ArrayList of HashMaps (job entries - rows) that contain search-specific value ('Enterprise') from one particular column ('employer', etc.)

        loadData(); //load data, if not already loaded

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>(); //makes a new ArrayList of HashMaps to store job entries (from a particular column) that contains the search term

        for (HashMap<String, String> row : allJobs) { //iterates over each HashMap (job entry, named 'row') in allJobs (ArrayList)

            String aValue = row.get(column);  //gets the key from each HashMap, stores in variable 'aValue'; each key is a column ('field') e.g. employer (column is a parameter that was previously passed into the method)

            if (aValue != null && aValue.toLowerCase().contains(value.toLowerCase())) {  //does aValue exist and does the entry in that column contain the search term? (ignore case)
                jobs.add(row);  //if so, add the job entry (row) to the jobs ArrayList
            }
        }

        return jobs; //return jobs ArrayList
    }


    /**
     * Search all columns for the given term
     *
     * @param value The search term to look for
     * @return List of all jobs with at least one field containing the value
     */


    public static ArrayList<HashMap<String, String>> findByValue (String value)
    { //method to return ArrayList of HashMaps (job entries - rows) that contains search-specific value ('Enterprise) from all columns ('employer', etc.)

        loadData(); // load data, if not already loaded

        ArrayList<HashMap<String, String>> jobs = new ArrayList<>(); //makes a new ArrayList of HashMaps (job entries - rows)

        for (HashMap<String, String> row : allJobs) { // iterates over each HashMap (job entry, named 'row') in allJobs (ArrayList)

            for (String key : row.keySet()) { //iterates over each key/value pair in one HashMap (job entry, row) - rename 'entry' 'column'*****
                String aValue = row.get(key);  //get the value from the key/value pair (e.g. 'Saint Louis', 'Web Developer')

                if (aValue.toLowerCase().contains(value.toLowerCase())) {  // if the row contains the searchTerm (HashMap value),
                    jobs.add(row);      // then add the row (job entry) to jobs ArrayList of HashMaps


                    break; // Finding one field in a job that matches is sufficient (exits out of method so you don't have duplicate results)
                }
            }
        }

        return jobs;  //return jobs (ArrayList of HashMaps)
    }


    /**
     * Read in data from a CSV file and store it in a list
     */


    private static void loadData() {

        // Only load data once
        if (isDataLoaded) {
            return;   //exits the method
        }

        try {

            // Open the CSV file and set up pull out column header info and records
            Resource resource = new ClassPathResource(DATA_FILE);  //creates new ClassPathResource object using the string DATA_FILE (contains all characters from job_data.csv)
            InputStream is = resource.getInputStream();
            Reader reader = new InputStreamReader(is);
            CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader); //specifies the format (RFC4180) of a CSV file and parses input ; makes first record the header (name, employer, location, etc.)
            List<CSVRecord> records = parser.getRecords(); //creates new list for CSV input
            Integer numberOfColumns = records.get(0).size();  //sets the number of columns for the list depending on the number of comma separated values in each row?
            String[] headers = parser.getHeaderMap().keySet().toArray(new String[numberOfColumns]);  //set the header names

            allJobs = new ArrayList<>();  // creates empty ArrayList allJobs

            // Put the records into a more friendly format
            for (CSVRecord record : records) {    //iterate through list of CSV input
                HashMap<String, String> newJob = new HashMap<>();  //create new HashMap for each job entry

                for (String headerLabel : headers) {    //iterate through CSV headers
                    newJob.put(headerLabel, record.get(headerLabel));  //put headers in newJob HashMap
                }

                allJobs.add(newJob);  //add the HashMap to the allJobs ArrayList
            }

            isDataLoaded = true;  // flag the data as loaded, so we don't do it twice

        } catch (IOException e) {
            System.out.println("Failed to load job data");
            e.printStackTrace();
        }
    }
}



