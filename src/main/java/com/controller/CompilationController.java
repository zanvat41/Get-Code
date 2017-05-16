package com.controller;

import com.hackerrank.api.hackerrank.api.CheckerApi;
import com.hackerrank.api.hackerrank.model.LanguageResponse;
import com.hackerrank.api.hackerrank.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * CompilationController is the class that handles all compilation of user code
 * Created by tyler on 4/17/2017.
 *
 * Refer to http://restunited.com/docs/3d8qfglb8aqw for documentation
 *
 *  Language Code Reference
 *      "c": 1,
        "cpp": 2,
        "java": 3,
        "csharp": 9,
        "php": 7,
        "ruby": 8,
        "python": 5,
        "perl": 6,
        "haskell": 12,
        "clojure": 13,
        "scala": 15,
        "bash": 14,
        "mysql": 10,
        "oracle": 11,
        "erlang": 16,
        "clisp": 17,
        "lua": 18,
        "go": 21
 *
 */
public class CompilationController {

    /** The name of the project according to HackerRank */
    private final String name = "Get Code";

    /** The API Key we need in order to access the HackerRank API */
    private final String apiKey = "hackerrank|2458877-1339|b0f22a0e05200c818362961057794c66da719019";

    /** We are sending the message in a JSON format */
    private String format = "JSON";

    /** A callback url, on which the submission response will be posted as a JSON
     * string under `data` parameter. */
    private String callbackUrl = "";

    /** true means the response is sent only after the submission is compiled and run.
     * false means the request returns immediately and submission response will posted
     * through the callback URL. */
    private final String wait = "true";

    /** The checker API */
    private CheckerApi checkerApi;


    /** constructor */
    public CompilationController() {
        checkerApi = new CheckerApi();
    }

    /** Print out the supported languages */
    public String getSupportedLanguages() {

        //create a languages string that will be returned
        String languages;

        //create a new checkerAPI object and get the languages
        try {

            //get the languages, and print
            LanguageResponse response = checkerApi.languages();
            languages = response.getLanguages().toString();

        //catch API exception
        } catch (Exception e) {
            System.out.printf("ApiException caught: %s\n", e.getMessage());
            languages = ("ApiException caught: " +  e.getMessage());
        }
        return languages;
    }

    /** Request for the HackerRank API to run user code */
    public List<String> submitRequest(Integer lang, String source, String testcases) {

        //try to make a new CheckerApi, submit the request and print the response
        try {
            Submission response;
            response = checkerApi.submission(apiKey, source, lang, testcases, format, callbackUrl, wait);
            System.out.println(response);
            return response.getResult().getStdout();

        //catch API exception
        } catch (Exception e) {
            System.out.printf("ApiException caught: %s\n", e.getMessage());
            return null;
        }
    }
}
