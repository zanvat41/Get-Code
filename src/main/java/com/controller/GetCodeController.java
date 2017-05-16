package com.controller;

import com.database.DatabaseController;
import com.google.appengine.repackaged.com.google.gson.*;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

import com.data.*;
import com.users.*;
import com.form.*;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import org.springframework.web.servlet.ModelAndView;

/**
 * GetCodeController is the class that handles all GET and POST requests for our webapp.
 * Created by tyler on 4/3/2017.
 */
@Controller
public class GetCodeController extends HttpServlet {

    private DatabaseController databaseController = new DatabaseController();          //the controller for handling and accessing the database
    private CompilationController compilationController = new CompilationController();    //the controller for handling user code compilation and translation
    private boolean initialized = false;                    //whether the controllers were initialized or not
    private final boolean clearDatabase = false;             //whether we need to clear the database or not for testing

    //initialize all of the controllers
    private void initControllers() {

        //don't do anything if we already initialized
        if (!initialized) {

            //initialize the controllers

            //set initialized to true since we never have to do this again for the lifetime of the servlet
            initialized = true;

            //if we need to clear the database for testing, clear it
            if (clearDatabase) {
                databaseController.clearDatabase();
            }
        }
    }

    /** method for going to index.jsp */
    @RequestMapping(value = "/")
    public synchronized String index(Model model) {

        //initialize the controllers then forward the user to the index page
        initControllers();
        return "index";
    }

    /** method for logging in and redirecting to the profile */
    @RequestMapping(value = "/profile", method = {RequestMethod.POST, RequestMethod.GET})
    public synchronized String login(Model model,
                        @RequestParam("firstName") String firstName,
                        @RequestParam("lastName") String lastName,
                        @RequestParam("email") String email,
                        HttpSession session) {

        //create the database controller if we need it
        initControllers();

        session.setAttribute("user", new User(firstName, lastName, email));

        //add the user to the database if we haven't already
        databaseController.addUserToDatabase(firstName, lastName, email);

        //get the user from the session
        User user = (User) session.getAttribute("user");

        //get the model back from loading basic profile information
        model = loadBasicProfileData(model, user);

        //get the list of tutorials and progression from the database
        ArrayList<Tutorial> publicTutorials = databaseController.getPublicOrPrivateTutorials("none");
        ArrayList<Progression> userProgression = databaseController.getUserProgressions(user.getEmail());

        //get the list of profile list elements
        ArrayList<ProfileListElement> profileListElements = getProfileListElements(publicTutorials, userProgression);

        //add remaining attributes pertaining to the tutorial list
        model.addAttribute("listElements", profileListElements);
        session.setAttribute("listElements", profileListElements);
        model.addAttribute("query", " Progress Tracker");

        //redirect to profile
        return "profile";
    }

    /** method for logging in and redirecting to the profile */
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public synchronized String login(Model model,
                        HttpSession session) {

        //create the database controller if we need it
        initControllers();

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        //add the new user to the session
        User user = (User) session.getAttribute("user");

        //get the model back from loading basic profile information
        model = loadBasicProfileData(model, user);

        //get the list of tutorials and progression from the database
        ArrayList<Tutorial> publicTutorials = databaseController.getPublicOrPrivateTutorials("none");
        ArrayList<Progression> userProgression = databaseController.getUserProgressions(user.getEmail());

        //get the list of profile list elements
        ArrayList<ProfileListElement> profileListElements = getProfileListElements(publicTutorials, userProgression);

        //add remaining attributes pertaining to the tutorial list
        model.addAttribute("listElements", profileListElements);
        session.setAttribute("listElements", profileListElements);
        model.addAttribute("query", " Progress Tracker");

        //redirect to profile
        return "profile";
    }

    /** method for sending a user to the createtutorial page */
    @RequestMapping(value = "/createtutorial", method = RequestMethod.GET)
    public synchronized String createTutorial(Model model,
                                 HttpSession session) {

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        //add the tutorial to the session
        Tutorial tutorial = new Tutorial();
        User user = (User) session.getAttribute("user");
        tutorial.setCreator(user.getEmail());
        session.setAttribute("tutorial", tutorial);

        //add the tutorial form to the model then go to the create tutorial page
        model.addAttribute("tutorialForm", new TutorialForm());
        return "createtutorial";
    }

    /** if the user goes to preview before creating a tutorial */
    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public synchronized String preview(Model model,
                          HttpSession session) {

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        //send the user to a warning page saying they have to create a tutorial before previewing one
        model.addAttribute("message", "You must <a href=\"/createtutorial\">create a tutorial</a> before you can do that.");
        return "hey!";
    }

    /** method for creating a tutorial */
    @RequestMapping(value = "/preview", method = RequestMethod.POST)
    public synchronized String previewTutorial(Model model,
                                  @ModelAttribute("tutorialForm") TutorialForm tutorialForm,
                                  HttpSession session) {

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        //create the preview content to be added to preview.jsp
        String preview = getPreviewContent(tutorialForm);

        //add the string to the model and go to the preview page
        model.addAttribute("preview", preview);
        return "preview";
    }

    /** if the user goes to preview before creating a tutorial */
    @RequestMapping(value = "/createproblems", method = RequestMethod.GET)
    public synchronized String createproblems(Model model,
                          HttpSession session) {

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        //send the user to a warning page saying they have to create a tutorial before previewing one
        model.addAttribute("message", "You must <a href=\"/createtutorial\">create a tutorial</a> before you can do that.");
        return "hey!";
    }

    /** method for creating a practice problems part of a tutorial */
    @RequestMapping(value = "/createproblems", method = RequestMethod.POST)
    public synchronized String createproblems(Model model,
                                 @ModelAttribute("tutorialForm") TutorialForm tutorialForm,
                                 HttpSession session) {

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, true);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        //get the tutorial from the session and fill in what we got from the form
        Tutorial tutorial = fillTutorialFromForm(((Tutorial) session.getAttribute("tutorial")), tutorialForm);

        //validate to make sure the tutorial has not been created before word for word
        if (!databaseController.validateTutorial(tutorial)) {
            model.addAttribute("message", "This tutorial is already created, click <a href=\"/createtutorial\">here</a> to try again");
            return "hey!";
        }

        if (tutorial.getClassCode().equals("") || tutorial.getClassCode() == null) {
            tutorial.setClassCode("none");
        }

        //check the class code, if it is a public tutorial, then ignore the check
        if (!(tutorial.getClassCode().equals("none"))) {

            //if the class code has less than 6 characters, then go to hey!
            if (tutorial.getClassCode().length() != 6) {
                model.addAttribute("message", "Invalid class code (must be none or the 6 character class code for the class), click <a href=\"/createtutorial\">here</a> to try again");
                return "hey!";
            }

            //get the user and the classes he is teaching
            User user = (User) session.getAttribute("user");
            ArrayList<ClassObj> classesTeaching = databaseController.getClassesTeaching(user.getEmail());

            //check if the user is teaching a class that shares the class code
            boolean isTeaching = false;
            for (ClassObj aClassesTeaching : classesTeaching) {

                //if the class code of a class matches the class code of the tutorial the user is adding, that means the user is the teacher of the class that has the class code they are adding
                if (aClassesTeaching.getClassCode().equals(tutorial.getClassCode())) {
                    isTeaching = true;
                    break;
                }
            }

            //if they are not teaching the class, then go to hey page
            if (!isTeaching) {
                model.addAttribute("message", "You are not teaching that class, click <a href=\"/createtutorial\">here</a> to try again");
                return "hey!";
            }
        }

        //stop them if the tutorial name is empty
        else if (tutorial.getName().equals("") || tutorial.getName() == null) {
            model.addAttribute("message", "You must enter a valid tutorial name, click <a href=\"/createtutorial\">here</a> to try again");
            return "hey!";
        }

        //set the session tutorial object to the new one we just created
        session.setAttribute("tutorial", tutorial);

        //create the preview content to be added to preview.jsp
        ProblemsForm pform = new ProblemsForm();
        pform.setLanguage(tutorialForm.getLanguage());

        //add the string to the model and go to the preview page
        model.addAttribute("problemsForm", pform);
        model.addAttribute("language", pform.getLanguage());
        model.addAttribute("languageName", LangMap.toLanguageName(pform.getLanguage()));
        return "createproblems";
    }

    /** if the user goes to preview before creating a tutorial */
    @RequestMapping(value = "/created", method = RequestMethod.GET)
    public synchronized String created(Model model, HttpSession session) {

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        //send the user to a warning page saying they have to create a tutorial before previewing one
        model.addAttribute("message", "You must <a href=\"/createtutorial\">create a tutorial</a> before you can do that.");
        return "hey!";
    }

    /** method for when a tutorial is finished being created */
    @RequestMapping(value = "/created", method = RequestMethod.POST)
    public synchronized String created(Model model,
                          @ModelAttribute("problemsForm") ProblemsForm problemsForm,
                          HttpSession session) {

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, true);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        //set all of the data from the problems form to the tutorial from the session
        Tutorial tutorial = fillTutorialFromForm(((Tutorial) session.getAttribute("tutorial")), problemsForm);
        session.setAttribute("currentPracticeProblems", tutorial);

        //validate to make sure the tutorial has not been created before word for word
        if (!databaseController.validateTutorial(tutorial)) {
            model.addAttribute("message", "This tutorial is already created, click <a href=\"/createtutorial\">here</a> to try again");
            return "hey!";
        }

        //add the tutorial to the database, update the tutorial's ID, and add it to the session
        int tutID = databaseController.createTutorial(tutorial);
        tutorial.setId(Integer.toString(tutID));
        session.setAttribute("tutorial", tutorial);

        //get the practice problem content
        String created = getPracticeProblemContent(problemsForm);
        int lang = problemsForm.getLanguage();
        String langName = LangMap.toLanguageName(lang);

        //add the string to the model and go to the preview page
        model.addAttribute("created", created);
        model.addAttribute("language", lang);
        model.addAttribute("languageName", langName);
        // swap this for id num when you get a chance
        model.addAttribute("tutID", tutID);

        Tutorial test = (Tutorial) session.getAttribute("tutorial");
        System.out.println("Name: " + test.getName());
        System.out.println("Class Code: " + test.getClassCode());
        System.out.println("Creator: " + test.getCreator());
        System.out.println("Language: " + test.getLanguage());
        System.out.println("ID: " + test.getId());
        System.out.println("# codes: " + test.getTutorialCodes().size());
        System.out.println("# questions: " + test.getTutorialQuestions().size());
        System.out.println("# headings: " + test.getTutorialHeadings().size());
        System.out.println("# texts: " + test.getTutorialTexts().size());
        System.out.println("# pics: " + test.getTutorialPictures().size());
        System.out.println("# vids: " + test.getTutorialVideos().size());

        model.addAttribute("answersForm", new AnswersForm());
        //return created
        return "created";
    }

    /** method for running a program in the created form */
    @RequestMapping(value = "/check-ans", method = RequestMethod.POST)
    public synchronized String run(Model model,
                                   @ModelAttribute("answersForm") AnswersForm answersForm,
                                   HttpSession session) {

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        //get the user and tutorial we are working on
        User user = (User) session.getAttribute("user");
        Tutorial tutorial = (Tutorial) session.getAttribute("currentPracticeProblems");

        //get the score and update the user progression
        int score = getScore(tutorial, answersForm);
        System.out.println("SCORE: " + score);

        if (score == -1) {
            model.addAttribute("message", ("invalid submission: Either there was an error or you left a question blank."));
            return "hey!";
        }

        Progression newProgression = getUpdatedUserProgression(user, tutorial, score);
        databaseController.updateProgression(newProgression);

        //go to the hey page to let the user they completed the tutorial
        model.addAttribute("message", ("You completed this tutorial! Your score was " + score) + "%. Click <a href=\"/profile\">here</a> to go back to the profile page.");
        return "hey!";
    }

    /** if the user goes to preview before creating a tutorial */
    @RequestMapping(value = "/run", method = RequestMethod.GET)
    public synchronized String run(Model model, HttpSession session) {

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        //send the user to a warning page saying they have to create a tutorial before previewing one
        model.addAttribute("message", "You shouldn't be here.");
        return "hey!";
    }

    /** method for running a program in the created form */
    @RequestMapping(value = "/run", method = RequestMethod.POST)
    public synchronized @ResponseBody String run(@RequestBody String json) {

        initControllers();

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        Tutorial myTut = databaseController.viewTutorial(jsonObject.get("tutID").getAsString());

        /*ArrayList<Tutorial_Question> mcs = myTut.getTutorialQuestions();

        for(Tutorial_Question mc : mcs) {
            //if(mc.getQid().equals(jsonObject.get("qid").getAsString())) {
                System.out.println("Found question: " + mc.getAnswer());
                break;
            //}
        }
        */

        ArrayList<Tutorial_Code> codes = myTut.getTutorialCodes();

        Tutorial_Code myCode = new Tutorial_Code();
        System.out.println("QID PASSED IN: " + jsonObject.get("qid").getAsString());

        for(Tutorial_Code code : codes) {
            System.out.println("QID: " + code.getQid());
            if(code.getQid().equals(jsonObject.get("qid").getAsString())) {
                myCode = code;
                System.out.println("Found code: " + jsonObject.get("qid").getAsString());
                break;
            }
        }

        ArrayList<String> inputs = myCode.getInputs();
        ArrayList<String> outputs = myCode.getOutputs();

        Iterator<String> iter = inputs.iterator();

        String testcases = "[";

        for(int i = 0; i < inputs.size(); i++) {
            String escaped = inputs.get(i).replace("\r\n", "\\n");
            testcases += "\"" + escaped + "\"";

            if(i != inputs.size()-1) {
                testcases += ",";
            }
        }

        testcases += "]";

        System.out.println(testcases);

        for(String s : outputs) {
            System.out.println("OUTPUT: " + s);
        }

        for(int i = 0; i < outputs.size(); i++) {
            String escaped = outputs.get(i).replace("\r\n", "\n");
            outputs.set(i, escaped);
        }

        List<String> stdout = compilationController.submitRequest(jsonObject.get("language").getAsInt(), jsonObject.get("code").getAsString(), testcases);

        JsonObject jo = new JsonObject();
        com.google.gson.JsonArray ja = new JsonArray();
        com.google.gson.JsonArray status = new JsonArray();

        int index = 0;

        if(stdout != null) {
            for (String result : stdout) {
                ja.add("result");
                if (result.equals(outputs.get(index))) {
                    status.add(1);
                } else {
                    status.add(0);
                }

                index++;
            }

            jo.add("results", ja);
            jo.add("status", status);
        }
        else {
            ja.add("error");
            status.add(0);
            jo.add("results", ja);
            jo.add("status", status);
        }

        System.out.println(jo.toString());

        //create the preview content to be added to preview.jsp
        //String preview = getPreviewContent(tutorialForm);

        //add the string to the model and go to the preview page
        //model.addAttribute("problemsForm", new ProblemsForm());
        return jo.toString();
    }

    /** method for clicking on a tutorial and opening selected tutorial */
    @RequestMapping(value = "/tutorialPage", method = RequestMethod.GET)
    public synchronized String goTutorial(Model model,
                             @RequestParam("tutorialId") String tutorialId,
                             HttpSession session) {

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        //create the database controller if we need it
        initControllers();
        System.out.println(tutorialId);

        //get the tutorial by the id returned from the page and add it to the model
        Tutorial tutorial = databaseController.viewTutorial(tutorialId);
        System.out.println(tutorial.getName());

        //check if the user is in the class, if not, do not let them view the class
        //don't check if the tutorial is public
        User user = (User) session.getAttribute("user");
        if (!tutorial.getClassCode().equals("none") && !(tutorial.getCreator().equals(user.getEmail()))) {

            //get the user information for the check
            ArrayList<ClassObj> classes = databaseController.getStudentClasses(user.getEmail());

            //iterate over the class list the student is in
            boolean inClass = false;
            for (ClassObj aClass : classes) {

                //if the class code the student is in matches teh tutorials class code, that means the student is in the class that contains the tutorial
                if (aClass.getClassCode().equals(tutorial.getClassCode())) {
                    inClass = true;
                    break;
                }
            }

            //if they are not in the class, go to the hey page
            if (!inClass) {
                model.addAttribute("message", "You are not in the class this tutorial belongs to, nor are you the creator. Click <a href=\"/profile\">here</a> to go back to the profile page");
                return "hey!";
            }
        }

        //get the form from the tutorial
        TutorialForm form = getTutorialFormFromTutorial(tutorial);

        //get the preview content from the form we created
        String preview = getPreviewContent(form);

        //add the tutorial to the session
        session.setAttribute("currentPracticeProblems", tutorial);

        //add the string to the model and go to the preview page
        model.addAttribute("preview", preview);
        model.addAttribute("tutorialId", tutorialId);
        return "preview";
    }

    /** method for clicking "try practice problems" */
    @RequestMapping(value = "/problemsPage", method = RequestMethod.POST)
    public synchronized String tryPracticeProblems(Model model,
                          @RequestParam("tutorialId") String tutorialId,
                          HttpSession session) {

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        //create the database controller if we need it
        initControllers();
        System.out.println("TUTORIAL ID: \"" + tutorialId + "\"");

        //get the tutorial by the id returned from the page and add it to the model
        Tutorial tutorial = databaseController.viewTutorial(tutorialId);
        System.out.println(tutorial.getName());

        //get the form from the tutorial
        ProblemsForm problemsForm = getProblemsFormFromTutorial(tutorial);

        //get the practice problem content
        String created = getPracticeProblemContent(problemsForm);
        int lang = problemsForm.getLanguage();
        String langName = LangMap.toLanguageName(lang);

        //add the string to the model and go to the preview page
        model.addAttribute("created", created);
        model.addAttribute("language", lang);
        model.addAttribute("languageName", langName);
        // swap this for id num when you get a chance
        model.addAttribute("tutID", tutorialId);

        //add the problems form to the model
        model.addAttribute("problemsForm", problemsForm);

        //return created
        return "created";
    }

    /*
    @RequestMapping(value = "/submitPracticeProblems", method = RequestMethod.POST)
    public synchronized String submitPracticeProblems(Model model,
                                                      HttpSession session,
                                                      @ModelAttribute("problemsForm") ProblemsForm problemsForm) {

        //get the user and current practice problems
        User user = (User) session.getAttribute("user");
        Tutorial currentPracticeProblems = (Tutorial) session.getAttribute("currentPracticeProblems");

        //update user progression
        updateUserProgression(user, currentPracticeProblems, problemsForm);

        //go back to the profile page
        return login(model, session);
    }
    */

    /** method for logging out a user and redirecting to the site */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public synchronized String logout(Model model,
                         HttpSession session) {

        //set the session's user attribute to null to log them out, as well as the tutorial in progress if there is one
        if(session.getAttribute("user") != null) {
            session.setAttribute("user", null);
            session.setAttribute("tutorial", null);
            session.setAttribute("listElements", null);
        }
        else {
            model.addAttribute("message", "You must <a href=\"/\">login</a> before you can do that.");
            return "hey!";
        }

        return "redirect:/";
    }

    /** method for getting to the admin login page */
    @RequestMapping(value = "/adminlogin", method = RequestMethod.GET)
    public synchronized String viewAdminLoginForm(Model model) {

        model.addAttribute("adminloginmessage", "Administrator Login");
        return "adminlogin";
    }

    /** method for logging into the admin page */
    @RequestMapping(value = "/adminlogin", method = RequestMethod.POST)
    public synchronized String completeAdminLoginForm(Model model,
                                         @RequestParam("username") String username,
                                         @RequestParam("password") String password) {

        //init the database controller if it hasn't been done already
        initControllers();

        //log in the user
        boolean loggedIn = databaseController.loginAdmin(username, password);

        //if we logged in, then go to the admin page
        if (loggedIn) {
            return "admin";
        }

        //otherwise, go to the admin message page with the message saying login unsuccessful
        else {
            model.addAttribute("adminmessage", "Invalid Login Credentials");
            return "adminmessage";
        }
    }

    /** method for getting to the admin registration page */
    @RequestMapping(value = "/adminregistration", method = RequestMethod.GET)
    public synchronized String viewAdminRegistrationForm(Model model) {

        model.addAttribute("adminregistrationmessage", "Administrator Registration");
        return "adminregistration";
    }

    /** method for registering an admin */
    @RequestMapping(value = "/adminregistration", method = RequestMethod.POST)
    public synchronized String completeAdminRegistrationForm(Model model,
                                                @RequestParam("username") String username,
                                                @RequestParam("password") String password) {

        //initialize the database controller if it hasn't been already
        initControllers();

        //register the admin
        boolean registered = databaseController.registerAdmin(username, password);

        //if registration was a success
        if (registered) {
            model.addAttribute("adminmessage","Registration Successful");
        }
        //otherwise...
        else {
            model.addAttribute("adminmessage","Registration Unsuccessful");
        }
        //go to the adminmessage page
        return "adminmessage";
    }

    /** when a user becomes a teacher and creates their own class or a user becomes a student and joins a class */
    @RequestMapping(value = "/class", method = RequestMethod.GET)
    public synchronized String classPage(Model model,
                                         HttpSession session) {

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        //go to the class page
        return "class";
    }

    /** When a user makes their own class */
    @RequestMapping(value = "/createclass", method = RequestMethod.POST)
    public synchronized String createClass (Model model,
                               @RequestParam("classname") String className,
                               HttpSession session) {

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        //initialize the database controller if it hasn't been already
        initControllers();

        //create the class using the name of the class given and the teacher's email
        String classCode = databaseController.createClass(className, ((User)(session.getAttribute("user"))).getEmail());

        //generate the message
        String message = ("You have successfully created a class! your class code is <br />" + classCode + "  click <a href=\"/profile\">here</a> to return to the profile page");

        //fill the confirmation page
        model.addAttribute("message", message);

        //go to confirmation page
        return "hey!";
    }

    /** When a user joins a pre-existing class */
    @RequestMapping(value = "/joinclass", method = RequestMethod.POST)
    public synchronized String joinClass (Model model,
                             @RequestParam("classcode") String classCode,
                             HttpSession session) {

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        //the class code must be 6 characters long
        if (containsBadCharacters(classCode)) {
            model.addAttribute("message", "Invalid class code. Click <a href=\"/class\">here</a> to retry");
            return "hey!";
        }

        //create a string that will be our message
        String message;

        //initialize the database controller if it hasn't been already
        initControllers();

        //if the database says the class is locked, then set the message and return
        boolean locked = databaseController.getClassLockStatus(classCode);
        if (locked) {
            message = "Error, the class you are trying to join is currently locked, contact the instructor for more information. click <a href=\"/profile\">here</a> to return to the profile page";

            //add the message to the model and go to message page for results
            model.addAttribute("message", message);

            return "hey!";
        }

        //try to join the class with the matching class code
        String joinedClass = databaseController.joinClass(((User)(session.getAttribute("user"))).getEmail(), classCode);

        //find out what message we should send to the message screen
        if (joinedClass.equals("Invalid class code.")) {
            message = "Error, invalid class code, please <a href=\"/class\">try again</a>";
        }
        else if (joinedClass.equals("Already in class.")) {
            message = "You are already enrolled in this class! click <a href=\"/profile\">here</a> to return to the profile page";
        }
        else {
            message = ("Congratulations, you have successfully joined the class \"" + joinedClass + "\"! click <a href=\"/profile\">here</a> to return to the profile page");
        }

        //add the message to the model and go to message page for results
        model.addAttribute("message", message);

        return "hey!";
    }

    /** when a user wants to view all general public tutorials */
    @RequestMapping(value = "/profilepublicgeneral", method = RequestMethod.GET)
    public synchronized String getPublicGeneralTutorials(Model model,
                                            HttpSession session) {

        return login(model, session);
    }

    /** when a user wants to manage the tutorials they have created */
    @RequestMapping(value = "/mytutorials", method = RequestMethod.GET)
    public synchronized String manageMyTutorials(Model model,
                                    HttpSession session) {

        //init controllers if we haven't already
        initControllers();

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        String email = ((User) (session.getAttribute("user"))).getEmail();

        System.out.println("Email: " + email);

        //get the tutorials that were made by the user
        ArrayList<Tutorial> myTutorials = databaseController.getTutorialsFromEmail(email);

        //add the tutorials to the model then return mytutorials
        model.addAttribute("tutorials", myTutorials);
        return "mytutorials";

    }

    /** when a user wants to delete a tutorial they created */
    @RequestMapping(value = "/deletetutorial", method = RequestMethod.POST)
    public synchronized String deleteMyTutorial(Model model,
                                   HttpSession session,
                                   @RequestParam("tutorialId") String tutorialId) {

        //init controllers if we haven't already
        initControllers();

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        //delete the tutorial from the database
        databaseController.deleteTutorial(tutorialId);

        System.out.println("Email: " + ((User) session.getAttribute("user")).getEmail());

        //get the tutorials that were made by the user
        ArrayList<Tutorial> myTutorials = databaseController.getTutorialsFromEmail(((User) session.getAttribute("user")).getEmail());

        //add the tutorials to the model then return mytutorials
        model.addAttribute("tutorials", myTutorials);

        //go back to the my tutorials page
        return "mytutorials";
    }

    /** when a user wants to edit a tutorial they created */
    @RequestMapping(value = "/edittutorial", method = RequestMethod.POST)
    public synchronized String editMyTutorial(Model model,
                                 HttpSession session,
                                 @RequestParam("tutorialId") String tutorialId) {

        //init controllers if we haven't already
        initControllers();

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        return (manageMyTutorials(model, session));

    }

    /** when a student wants to view their class */
    @RequestMapping(value = "/classprofile", method = RequestMethod.GET)
    public synchronized String getClassProfile(Model model,
                                  @RequestParam("classCode") String classCode,
                                  @RequestParam("className") String className,
                                  @RequestParam("classTeacher") String teacherName,
                                  HttpSession session) {

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        User user = (User) session.getAttribute("user");

        //init controllers if not already
        initControllers();

        //get the tutorials and progressions
        ArrayList<Tutorial> tutorials = databaseController.getPublicOrPrivateTutorials(classCode);
        ArrayList<Progression> progressions = databaseController.getUserProgressions(user.getEmail());

        //get the list of profile list elements
        ArrayList<ProfileListElement> profileListElements = getProfileListElements(tutorials, progressions);

        //send the full name of the user to the page
        String fullName = (user.getFirstName() + " " + user.getLastName());
        model.addAttribute("fullName", fullName);
        model.addAttribute("className", className);
        model.addAttribute("listElements", profileListElements);
        model.addAttribute("teacherName", teacherName);

        //redirect to profile
        return "classprofile";
    }

    /** when a student leaves a class */
    @RequestMapping(value = "/leaveclass", method = RequestMethod.POST)
    public synchronized String leaveClass(Model model,
                             @RequestParam("classcode") String classCode,
                             HttpSession session) {

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        User user = (User) session.getAttribute("user");

        //init the controllers if not done already
        initControllers();

        //remove the student from the class
        databaseController.removeStudentFromClass(user.getEmail(), classCode);

        //go to the profile page with updated data using the login function
        return login(model, session);
    }

    /** when a teacher views their class roster */
    @RequestMapping(value = "/roster", method = RequestMethod.GET)
    public synchronized String viewClassRoster(Model model,
                                  @RequestParam("className") String className,
                                  @RequestParam("classCode") String classCode,
                                  HttpSession session) {

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        //init the controllers if not done already
        initControllers();

        //get the lock status of the class
        boolean locked = databaseController.getClassLockStatus(classCode);
        String updateLockStatusPrompt = "Unlock the Class";
        String lockStatus = "Locked";
        if (!locked) {
            lockStatus = "Unlocked";
            updateLockStatusPrompt = "Lock the Class";
        }

        //get all of the users in the class
        ArrayList<User> students = databaseController.getStudentsByClass(classCode);

        //get all of the tutorials that the class uses
        ArrayList<Tutorial> tutorials = databaseController.getPublicOrPrivateTutorials(classCode);

        //add the necessary attributes to the model then return roster
        model.addAttribute("className", className);
        model.addAttribute("lockStatus", lockStatus);
        model.addAttribute("classCode", classCode);
        model.addAttribute("updateLockStatusPrompt", updateLockStatusPrompt);
        model.addAttribute("listElements", tutorials);
        model.addAttribute("students", students);
        return "roster";
    }

    /** when a teacher locks or unlocks a class, meaning a student can no longer join */
    @RequestMapping(value = "/classlock", method = RequestMethod.POST)
    public synchronized String classLock(Model model,
                            @RequestParam("classcode") String classCode,
                            @RequestParam("classname") String className,
                            HttpSession session) {

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        //init the controllers of not done already
        initControllers();

        //lock or unlock the class
        databaseController.updateClassLockStatus(classCode);

        //go back to the roster page
        return viewClassRoster(model, className, classCode, session);
    }

    /** when a teacher removes a class */
    @RequestMapping(value = "/removestudent", method = RequestMethod.POST)
    public synchronized String removeStudent(Model model,
                             @RequestParam("classcode") String classCode,
                             @RequestParam("classname") String className,
                             @RequestParam("email") String email,
                             HttpSession session) {

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        //init the controllers if not done already
        initControllers();

        //remove the student from the class
        databaseController.removeStudentFromClass(email, classCode);

        //go back to the class roster
        return viewClassRoster(model, className, classCode, session);
    }

    /** When a teacher deletes or closes a class */
    @RequestMapping(value = "/deleteclass", method = RequestMethod.POST)
    public synchronized String deleteClass(Model model,
                              HttpSession session,
                              @RequestParam("classcode") String classCode) {

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        //init the controllers if not done already
        initControllers();

        //delete the class from the database
        databaseController.deleteClass(classCode);

        //go back to the profile page
        return login(model, session);
    }

    /** Searching for specific tutorials */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public synchronized String searchTutorials(Model model,
                                  @RequestParam("query") String query,
                                  HttpSession session) {

        System.out.println("Query: " + query);

        //init the controllers if not done already
        initControllers();

        //redirect if the user is not logged in
        String validateMessage = validateLogin(session, false);
        if (validateMessage != null) {
            model.addAttribute("message", validateMessage);
            return "hey!";
        }

        //load the basic profile data
        model = loadBasicProfileData(model, (User) session.getAttribute("user"));

        //if we entered a weird or empty search, leave an empty list. otherwise, do a search
        ArrayList<Tutorial> searchResults;
        if (query.length() <= 0) {
            searchResults = new ArrayList<Tutorial>();
        }
        else {
            searchResults = databaseController.searchTutorials(query);
        }

        //get all of the user's progression and make the list elements
        ArrayList<Progression> progressions = databaseController.getUserProgressions(((User) session.getAttribute("user")).getEmail());
        ArrayList<ProfileListElement> listElements = getProfileListElements(searchResults, progressions);

        //add all of the needed components to the model and return
        model.addAttribute("query", (" Search for: " + query));
        model.addAttribute("listElements", listElements);
        session.setAttribute("listElements", listElements);

        //update the profile page
        return "profile";
    }

    /** Helper method for getting a list of profile list elements from the tutorials and user progression lists */
    private ArrayList<ProfileListElement> getProfileListElements(ArrayList<Tutorial> tutorials, ArrayList<Progression> progressions) {

        //create a new list
        ArrayList<ProfileListElement> list = new ArrayList<ProfileListElement>();

        //iterate through the list of tutorials
        for (int i = 0;i < tutorials.size();i++) {

            //keep track of whether we found a matching progression or not
            boolean foundMatchingProgression = false;

            //iterate through the list of progressions, looking for a progression matching the tutorial we are at currently
            for (int k = 0;k < progressions.size();k++) {

                //if the progression matches the tutorial, add the list element to the list and break
                if (tutorials.get(i).getId().equals(progressions.get(k).getTutorialId())) {

                    //make a new ProfileListElement
                    ProfileListElement newElement = new ProfileListElement(tutorials.get(i).getId(),
                                                                           tutorials.get(i).getName(),
                                                                           tutorials.get(i).getLanguage(),
                                                                           tutorials.get(i).getCreator(),
                                                                           tutorials.get(i).getClassCode(),
                                                                           progressions.get(k).getScore(),
                                                                           progressions.get(k).getAttempts());
                    //add the element to the list, say we found a matching progression, and break
                    list.add(newElement);
                    foundMatchingProgression = true;
                    break;
                }
            }

            //if we did not find a matching progression, then make one with default user progression values
            if (!foundMatchingProgression) {

                //make a new list element without the user progression fields
                ProfileListElement newElement = new ProfileListElement(tutorials.get(i).getId(),
                                                                       tutorials.get(i).getName(),
                                                                       tutorials.get(i).getLanguage(),
                                                                       tutorials.get(i).getCreator(),
                                                                       tutorials.get(i).getClassCode());

                //add the element to the list
                list.add(newElement);
            }
        }

        return list;
    }

    /** make a tutorial object from a tutorial form object */
    private TutorialForm getTutorialFormFromTutorial(Tutorial tutorial) {

        //make a new tutorial form
        TutorialForm form = new TutorialForm();

        //set all of it's attributes to the form's attributes
        form.setName(tutorial.getName());
        form.setLanguage(LangMap.toLanguageId(tutorial.getLanguage()));
        form.setHeadings(tutorial.getTutorialHeadings());
        form.setTexts(tutorial.getTutorialTexts());
        form.setPictures(tutorial.getTutorialPictures());
        form.setVideos(tutorial.getTutorialVideos());

        //return the tutorial form
        return form;
    }

    /** make a tutorial object from a tutorial form object */
    private ProblemsForm getProblemsFormFromTutorial(Tutorial tutorial) {

        //make a new tutorial form
        ProblemsForm form = new ProblemsForm();

        //set all of it's attributes to the form's attributes
        form.setLanguage(LangMap.toLanguageId(tutorial.getLanguage()));
        form.setMcs(tutorial.getTutorialQuestions());
        form.setCodes(tutorial.getTutorialCodes());

        //return the tutorial form
        return form;
    }

    /** get the content meant to be displayed on the preview page */
    private String getPreviewContent(TutorialForm tutorialForm) {

        //create an empty string to append to
        String preview = "";

        //append the name and language to the content
        preview += "<h1><b>" + tutorialForm.getName() + "</b></h1><h4>Language: ";
        preview += LangMap.toLanguageName(tutorialForm.getLanguage()) + "</h4>";

        //create a map and an arraylist to order the content
        TreeMap<Integer,String> map = new TreeMap<Integer, String>();
        ArrayList<Integer> orders = new ArrayList<Integer>();

        //add the text nodes to the map
        List<Tutorial_Text> texts = tutorialForm.getTexts();
        for (Tutorial_Text text : texts) {
            if(text.getOrder() != null) {
                String element = "<p style='font-size:20px;white-space: pre;'>" + text.getContent() + "</p>";

                int index = Integer.parseInt(text.getOrder());
                map.put(index, element);
            }
        }

        //add the heading nodes to the map
        List<Tutorial_Heading> headings = tutorialForm.getHeadings();
        for (Tutorial_Heading heading : headings) {
            if(heading.getOrder() != null) {
                String element = "<h2><b>" + heading.getContent() + "</b></h2>";

                int index = Integer.parseInt(heading.getOrder());
                map.put(index, element);
            }
        }

        //add the picture nodes to the map
        List<Tutorial_Picture> pictures = tutorialForm.getPictures();
        for (Tutorial_Picture picture : pictures) {
            if(picture.getOrder() != null) {
                String element = "<div style='width:1000px'><img style='max-width:100%;' src='" + picture.getLink() + "'></div><br>";

                int index = Integer.parseInt(picture.getOrder());
                map.put(index, element);
            }
        }

        //add the video nodes to the map
        List<Tutorial_Video> videos = tutorialForm.getVideos();
        for (Tutorial_Video video : videos) {
            if(video.getOrder() != null) {
                String element = "<iframe width=\"560\" height=\"315\" src=\"" + video.getLink() + "\" frameborder=\"0\" allowfullscreen></iframe><br><br>";

                int index = Integer.parseInt(video.getOrder());
                map.put(index, element);
            }
        }

        //add all of the map nodes to the content string
        for(Map.Entry<Integer,String> entry : map.entrySet()) {
            preview += entry.getValue();
        }

        //return preview
        return preview;
    }

    /** load the basic information of their profile page for the user */
    private Model loadBasicProfileData(Model model, User user) {

        //get the list of classes a user is in
        ArrayList<ClassObj> classes = databaseController.getStudentClasses(user.getEmail());

        //get the list of classes the user teaches
        ArrayList<ClassObj> classesTeaching = databaseController.getClassesTeaching(user.getEmail());

        //send the full name of the user to the page
        String fullName = (user.getFirstName() + " " + user.getLastName());
        model.addAttribute("fullName", fullName);
        model.addAttribute("classes", classes);
        model.addAttribute("classesTeaching", classesTeaching);

        //return model
        return model;
    }

    /** get the content for the practice problem page */
    private String getPracticeProblemContent(ProblemsForm problemsForm) {

        //create the created string
        String created = "";

        //create the preview content to be added to preview.jsp
        TreeMap<Integer,String> map = new TreeMap<Integer, String>();
        ArrayList<Integer> orders = new ArrayList<Integer>();

        //add the text nodes to the map
        List<Tutorial_Question> mcs = problemsForm.getMcs();

        int count = 1;
        for (Tutorial_Question mc : mcs) {
            if(mc.getQid() != null) {
                String element = "<p style='font-size:20px;white-space: pre;'>" + count + ". " + mc.getQuestion() + "</p>";

                ArrayList<String> choices =  mc.getChoices();
                char letter = 97;
                int radio = 0;
                for (String choice : choices) {
                    if (radio == 0) {
                        element += "<div style=\"padding-left:2em\"><input type=\"radio\" selected=\"selected\" form=\"check-ans\" name=answers[" + Integer.toString(count-1) + "] value=\"" + Integer.toString(radio) + "\"> " + letter++ + ") " + choice + "</div>";

                    }
                    else {
                        element += "<div style=\"padding-left:2em\"><input type=\"radio\" form=\"check-ans\" name=answers[" + Integer.toString(count-1) + "] value=\"" + Integer.toString(radio) + "\"> " + letter++ + ") " + choice + "</div>";
                    }
                    radio++;
                }

                element += "<br>";

                //element += "<div>Answer: " + mc.answerAsLetter() + ")</div><br>";

                int index = Integer.parseInt(mc.getQid());
                map.put(index, element);
                count++;
            }
        }

        List<Tutorial_Code> codes = problemsForm.getCodes();

        int temp = 1;
        for (Tutorial_Code code : codes) {
            if(code.getQid() != null) {
                String element = "<button class=\"btn-primary\" id=\"run\" onclick=\"event.preventDefault(); runHackerRank('c" + Integer.toString(count) + "', " + code.getQid() + ");\">Run</button> <p style='display: inline;font-size:20px;white-space: pre;'>" + code.getDesc() + "</p><br><br>";

                element += "<div class=\"editor\" id=\"editor" + temp + "\"></div><textarea hidden class=\"codetext\" id=c" + Integer.toString(count) + " name=c" + Integer.toString(count) + " rows=\"10\" cols=\"150\">" + code.getCode() + "</textarea><br>";
                temp++;

                if(code.getInputs() != null) {
                    Iterator<String> iterInput = code.getInputs().iterator();
                    Iterator<String> iterOutput = code.getOutputs().iterator();
                    int testnum = 1;

                    while (iterInput.hasNext()) {
                        String input = iterInput.next();
                        String output = iterOutput.next();

                        element += "<b>Test " + Integer.toString(testnum) + "</b><br>";

                        if(input != "") {
                            element += "Input: <div style='border:1px solid black;overflow-y:scroll;overflow-x:hidden;width:50col;height:100px;'><p style='font-size:15px;white-space: pre;' id=input>" + input + "</p></div>";
                        }
                        element += "Output: <div style='border:1px solid black;overflow-y:scroll;overflow-x:hidden;width:50col;height:100px;' id='c" + Integer.toString(count) + "-output" + testnum + "-div'><p style='font-size:15px;white-space: pre;' id='c" + Integer.toString(count) + "-output" + testnum + "'></p><input id='c" + Integer.toString(count) + "-output" + testnum + "' type='hidden' name='codes[" + (count-1) + "].inputs[" + (testnum-1) + "]' form='check-ans' value='0'></div><br>";
                        testnum++;
                    }
                }

                //element += "<div>Answer: " + mc.answerAsLetter() + ")</div><br>";

                int index = Integer.parseInt(code.getQid());
                map.put(index, element);
                count++;
            }
        }

        //add all of the map nodes to the content string
        for(Map.Entry<Integer,String> entry : map.entrySet()) {
            created += entry.getValue();
        }
        /*
        for (Tutorial_Question mc : mcs) {
            System.out.println("Question: " + mc.getQuestion());
            ArrayList<String> choices =  mc.getChoices();
            char letter = 97;
            for (String choice : choices) {
                System.out.println(letter++ + ") " + choice);
            }
            System.out.println("Answer: " + mc.answerAsLetter() + ")");
        }
        */

        created += "<script>loadEditors(" + temp + ");</script>";

        //return the created string
        return created;
    }

    /** take a tutorial and fill out the information from the tutorial form */
    private Tutorial fillTutorialFromForm(Tutorial tutorial, TutorialForm form) {

        //set all of the data from the form to the tutorial
        tutorial.setName(form.getName());
        tutorial.setLanguage(LangMap.toLanguageName(form.getLanguage()));
        tutorial.setTutorialHeadings(form.getHeadings());
        tutorial.setTutorialTexts(form.getTexts());
        tutorial.setTutorialPictures(form.getPictures());
        tutorial.setTutorialVideos(form.getVideos());
        tutorial.setClassCode(form.getClassCode());

        //return the tutorial
        return tutorial;
    }

    /** take a tutorial and fill out the information from the problems form */
    private Tutorial fillTutorialFromForm(Tutorial tutorial, ProblemsForm form) {

        //set all of the data from the form to the tutorial
        tutorial.setTutorialQuestions(form.getMcs());
        tutorial.setTutorialCodes(form.getCodes());

        //return the tutorial
        return tutorial;
    }

    /** validate whether a user is logged in or not, as well as check to see if we are on the right page according to sequence */
    private String validateLogin(HttpSession session, boolean checkTutorial) {

        //redirect if the user is not logged in
        if(session.getAttribute("user") == null) {
            return "You must <a href=\"/\">login</a> before you can do that.";
        }

        //if we are checking the tutorial, redirect if we did not come from the create tutorial page
        else if (checkTutorial && (session.getAttribute("tutorial") == null)) {
            return "You must <a href=\"/createtutorial\">create a tutorial</a> before you can do that.";
        }

        //return null if no issues
        return null;
    }

    @RequestMapping("/tableFilter")
    public void tableFilter(HttpSession session, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ArrayList<ProfileListElement> listElements = (ArrayList<ProfileListElement>) session.getAttribute("listElements");

        for (int i = 0; i < listElements.size(); i++) {
            ProfileListElement item = listElements.get(i);
            if(request.getParameter("langFilter").equals("nf") || request.getParameter("langFilter").equals(item.getTutorialLanguage())) {
                //ProfileListElement item = listElements.get(i);
                out.println("<tr>");
                out.println("<td>");
                out.println("<a href=\"/tutorialPage?tutorialId=" + item.getTutorialId() + "\">" + item.getTutorialName() + "</a>");
                out.println("</td>");
                out.println("<td>" + item.getTutorialLanguage() + "</td>");
                out.println("<td>" + item.getTutorialCreator() + "</td>");
                out.println("<td>" + item.getTutorialClassCode() + "</td>");
                out.println("<td>" + item.getUserScore() + "</td>");
                out.println("<td>" + item.getUserAttempts() + "</td>");
                out.println("</tr>");
            }
        }

    }

    /** get a new progression */
    private Progression getUpdatedUserProgression(User user, Tutorial tutorial, int score) {

        //find what progression we are updating
        ArrayList<Progression> progressions = databaseController.getUserProgressions(user.getEmail());
        Progression progression = null;
        for (int i = 0;i < progressions.size();i++) {
            if (progressions.get(i).getTutorialId().equals(tutorial.getId())) {
                progression = progressions.get(i);
                break;
            }
        }

        //if we did not make a new progression, then make a new progression for this user with this tutorial
        if (progression == null) {
            progression = new Progression();
            progression.setEmail(user.getEmail());
            progression.setAttempts("0");
            progression.setClassCode(tutorial.getClassCode());
            progression.setTutorialId(tutorial.getId());
            progression.setScore("0");
        }

        //increase the attempts by one
        int attempts = Integer.parseInt(progression.getAttempts());
        progression.setAttempts(Integer.toString(attempts + 1));

        //compare scores and set the highest score to the highest of the previous scores
        int highest = Integer.parseInt(progression.getScore());
        if (score > highest) {
            progression.setScore(Integer.toString(score));
        }

        //return the progression we updated
        return progression;
    }

    /** returns the score of the user for that tutorial after completing practice problems */
    private int getScore(Tutorial tutorial, AnswersForm form) {

        //if there are no questions then just give a 100
        if (tutorial.getTutorialQuestions().size() == 0 && tutorial.getTutorialCodes().size() == 0) {
            return 100;
        }

        ArrayList<Tutorial_Code> formcodes = new ArrayList<Tutorial_Code>();
        for (Tutorial_Code code : form.getCodes()) {
            if (code.getInputs().size() != 0) {
                formcodes.add(code);
            }
            else {
                System.out.println("NULL FOUND IN FORMCODES");
            }
            System.out.println("formcode");
        }

        ArrayList<Tutorial_Code> tutorialcodes = new ArrayList<Tutorial_Code>();
        for (Tutorial_Code code : tutorial.getTutorialCodes()) {
            if (code.getInputs().size() != 0) {
                tutorialcodes.add(code);
            }
            else {
                System.out.println("NULL FOUND IN TUTORIALCODES");
            }
            System.out.println("tutorialcode");
        }

        //immediately return -1 if a question or all of them are left blank
        if (form.getAnswers().size() != tutorial.getTutorialQuestions().size() || formcodes.size() != tutorialcodes.size()) {
            if (form.getAnswers().size() != tutorial.getTutorialQuestions().size()) {
                System.out.println("error with questions");
                System.out.println("formcodes = " + formcodes.size());
                System.out.println("tutorialcodes = " + tutorialcodes.size());
                System.out.println("formquestions = " + form.getAnswers().size());
                System.out.println("tutorialquestions = " + tutorial.getTutorialQuestions().size());
            }
            else {
                System.out.println("error with codes");
                System.out.println("formcodes = " + formcodes.size());
                System.out.println("tutorialcodes = " + tutorialcodes.size());
                System.out.println("formquestions = " + form.getAnswers().size());
                System.out.println("tutorialquestions = " + tutorial.getTutorialQuestions().size());
            }
            return -1;
        }

        //make the score int
        int score;

        //check the answers
        int numberRight = 0;
        int totalQuestions = tutorial.getTutorialQuestions().size();
        for (int i = 0;i < tutorial.getTutorialQuestions().size();i++) {

            //if the answer matches the entry the user entered, its a question right
            int answer = Integer.parseInt(tutorial.getTutorialQuestions().get(i).getAnswer());
            if (answer == form.getAnswers().get(i)) {
                numberRight++;
            }
        }

        //check code questions
        for (int i = 0;i < tutorialcodes.size();i++) {
            for (int k = 0;k < tutorialcodes.get(i).getInputs().size();k++) {
                if (formcodes.get(i).getInputs().get(k).equals("1")) {
                    numberRight++;
                }
                totalQuestions++;
            }
        }

        //convert the score
        double scoreDouble = ((double) numberRight) / ((double) (totalQuestions)) * 100.0;
        score = (int) scoreDouble;

        System.out.println("right = " + numberRight);
        System.out.println("total = " + totalQuestions);

        //return the score
        return score;
    }

    /** resets the current status of the user so whatever they were working on in the past will not affect them later */
    private HttpSession resetUserStatus(HttpSession session) {

        session.setAttribute("tutorial", null);
        session.setAttribute("currentPracticeProblems", null);

        return session;
    }

    /** checks if the class code contains bad characters */
    private boolean containsBadCharacters(String classCode) {
        boolean bad = false;

        //return true if the class code is not 6
        if (classCode.length() != 6) {
            return true;
        }

        //if one character is not A-Z, a-z, or 0-9 then return true
        for (int i = 0;i < classCode.length();i++) {
            char character = classCode.charAt(i);
            if (    (character < 'A' && character > 'Z') ||
                    (character < 'a' && character > 'z') ||
                    (character < '0' && character > '9')) {
                return true;
            }
        }

        return bad;
    }

}