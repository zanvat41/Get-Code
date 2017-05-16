package com.database;

import com.data.*;
import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.users.User;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the controller for the database. Everything related to database processing should be placed in here.
 * Note that there is a constructor so that this could be used throughout the project.
 *
 * Created by Matthew on 4/4/2017.
 */
public class DatabaseController {
    private SearchController searchController;          // the search controller (for searching, using Search API)

    private final int LENGTH = 6;                                                                           // the length of the auto-generated class code
    private final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";     // the available characters for the class code
    private SecureRandom random;                                                                            // the random object used to get a random character for the class code

    //no-arg constructor
    public DatabaseController() {
        random = new SecureRandom();
        searchController = new SearchController();
    }

    /**
     * This method attempts to add a user to the database. If the user is already there (the email is unique),
     * we simply return. Else, we add the user. This method is "synchronized" so that this can only be completed
     * once at any given moment. This method will only be called when a user logs in.
     *
     * @param firstName - the user's first name
     * @param lastName - the user's last name
     * @param email - the user's email
     */
    public synchronized void addUserToDatabase(String firstName, String lastName, String email) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        // query to see if the user is already in the database
        Query query = new Query("User");
        FilterPredicate filter = new FilterPredicate("Email", FilterOperator.EQUAL, email);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        Entity userEntity = pq.asSingleEntity();

        // if the user is not in the database, we add them
        if (userEntity == null) {
            System.out.println("New user.");

            Entity user = new Entity("User");
            user.setProperty("FName", firstName);
            user.setProperty("LName", lastName);
            user.setProperty("Email", email);

            datastore.put(user);
        }
    }

    /**
     * This method registers an admin. Note that there should only be one admin stored in the database.
     * If there is already an entry here, return false for failure. If not, update the datastore and
     * return true for success. This method is "synchronized" so that this can only be completed once
     * at any given moment. This method will only be called when a user tries to register as an admin.
     *
     * @param username - the username of the admin
     * @param password - the password for the admin
     * @return true on success (database updated), false on failure
     */
    public synchronized boolean registerAdmin(String username, String password) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        // Query to see if anything's in the database. If so, cannot create an admin
        Query query = new Query("Admin");
        PreparedQuery pq = datastore.prepare(query);
        Entity admin = pq.asSingleEntity();

        if (admin == null) {
            System.out.println("New admin.");

            Entity user = new Entity("Admin");
            user.setProperty("Username", username);
            user.setProperty("Password", password);

            datastore.put(user);
            return true;
        }
        return false;
    }

    /**
     * This method logs an admin in if the credentials are correct. This method is "synchronized" so that
     * this can only be completed once at any given moment. This method will only be called when a user
     * tries to log in as an admin.
     *
     * @param username - the username of the admin
     * @param password - the password of the admin
     * @return true on success, false on failure
     */
    public synchronized boolean loginAdmin(String username, String password) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        // query to see if the user is in the database. If so, we return true. If not, return false
        Query query = new Query("Admin");
        FilterPredicate filter1 = new FilterPredicate("Username", FilterOperator.EQUAL, username);
        FilterPredicate filter2 = new FilterPredicate("Password", FilterOperator.EQUAL, password);
        List list = new ArrayList();
        list.add(filter1);
        list.add(filter2);

        CompositeFilter filter = new CompositeFilter(Query.CompositeFilterOperator.AND, list);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);
        Entity admin = pq.asSingleEntity();

        if (admin == null) {
            System.out.println("Wrong credentials.");
            return false;
        }

        System.out.println("Correct credentials.");
        return true;
    }

    /**
     * This method retrieves all the public tutorials from the database. A public tutorial is one with a
     * class code of "none". This method is "synchronized" so that this can only be completed
     * once at any given moment. This will most likely be used when adding data to the profile page.
     *
     * @param classCode - the class code (none or an actual class code)
     * @return the list of tutorials
     */
    public synchronized ArrayList<Tutorial> getPublicOrPrivateTutorials(String classCode) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        ArrayList<Tutorial> tutorials = new ArrayList<Tutorial>();

        // query all tutorial items to start
        Query query = new Query("Tutorial");
        FilterPredicate filter = new FilterPredicate("ClassCode", FilterOperator.EQUAL, classCode);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        // for each entity, create a tutorial object and put it in the list
        for (Entity tutorial : pq.asIterable()) {
            String id = (String)tutorial.getProperty("Id");
            String name = (String)tutorial.getProperty("Name");
            String language = (String)tutorial.getProperty("Language");
            String creator = (String)tutorial.getProperty("Creator");
            String archived = (String)tutorial.getProperty("Archived");

            System.out.println(createClassCode(LENGTH, datastore));

            Tutorial tut = new Tutorial(id, name, language, creator, classCode, archived);
            tutorials.add(tut);
        }

        return tutorials;
    }

    /**
     * This method gets all tutorials based on the creator's email. The string parameter is the the email.
     * This method is "synchronized" so that this can only be completed once at any given moment.
     *
     * @param email - the creator's email
     * @return the list of tutorials
     */
    public synchronized ArrayList<Tutorial> getTutorialsFromEmail(String email) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        ArrayList<Tutorial> tutorials = new ArrayList<Tutorial>();

        System.out.println("Info: " + email);

        // query the database
        Query query = new Query("Tutorial");
        FilterPredicate filter = new FilterPredicate("Creator", FilterOperator.EQUAL, email);

        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        // for each entity, create a tutorial object and put it in the list
        for (Entity entity : pq.asIterable()) {
            String id = (String)entity.getProperty("Id");
            String name = (String)entity.getProperty("Name");
            String language = (String)entity.getProperty("Language");
            String creator = (String)entity.getProperty("Creator");
            String classCode = (String)entity.getProperty("ClassCode");
            String archived = (String)entity.getProperty("Archived");

            System.out.println("Creator: " + creator  + ", Name: " + name);

            Tutorial tut = new Tutorial(id, name, language, creator, classCode, archived);
            tutorials.add(tut);
        }

        return tutorials;
    }

    /**
     * This method is used when a user clicks on a specific tutorial to access it. It gets all the content
     * from a tutorial based on the tutorial id and puts it in the Tutorial object. This method is
     * "synchronized" so that this can only be completed once at any given moment.
     *
     * @param id - the tutorial id
     * @return the filled tutorial
     */
    public synchronized Tutorial viewTutorial(String id) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Tutorial tutorial = getTutorialInformation(id, datastore);

        // get all the other information
        tutorial.setTutorialTexts(getTutorialTextInfo(id, datastore));
        tutorial.setTutorialHeadings(getTutorialHeadingInfo(id, datastore));
        tutorial.setTutorialPictures(getTutorialPictureInfo(id, datastore));
        tutorial.setTutorialVideos(getTutorialVideoInfo(id, datastore));
        tutorial.setTutorialQuestions(getTutorialQuestionInfo(id, datastore));
        tutorial.setTutorialCodes(getTutorialCodeInfo(id, datastore));

        return tutorial;
    }

    /**
     * This method puts a new tutorial in the database if everything is unique. Note that this is just the
     * information about the tutorial; the tutorial content will be in other tables. This method is
     * "synchronized" so that this can only be completed once at any given moment. This will be used when a
     * user or admin creates a tutorial.
     *
     * @param tutorial - the tutorial to be created
     * @return id on success, 0 on failure
     */
    public synchronized int createTutorial(Tutorial tutorial) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        if (validateTutorial(tutorial)) {
            System.out.println("New tutorial accepted.");
            int id = getId();

            // create the new tutorial
            Entity tut = new Entity("Tutorial");
            tut.setProperty("Id", Integer.toString(id));
            tut.setProperty("Name", tutorial.getName());
            tut.setProperty("Language", tutorial.getLanguage());
            tut.setProperty("Creator", tutorial.getCreator());
            tut.setProperty("ClassCode", tutorial.getClassCode());
            tut.setProperty("Archived", tutorial.getArchived());
            datastore.put(tut);

            System.out.println(id);

            // fill in the text content, headings, pictures, videos, questions, and answers
            enterTutorialTextInDatastore(datastore, Integer.toString(id), tutorial.getTutorialTexts());
            enterTutorialHeadingInDatastore(datastore, Integer.toString(id), tutorial.getTutorialHeadings());
            enterTutorialPictureInDatastore(datastore, Integer.toString(id), tutorial.getTutorialPictures());
            enterTutorialVideoInDatastore(datastore, Integer.toString(id), tutorial.getTutorialVideos());
            enterTutorialQuestionInDatastore(datastore, Integer.toString(id), tutorial.getTutorialQuestions());
            enterTutorialCodeInDatastore(datastore, Integer.toString(id), tutorial.getTutorialCodes());

            // send it to the search controller, and then return
            tutorial.setId(Integer.toString(id));
            searchController.createTutorial(tutorial);

            return id;
        }
        return 0;
    }

    /**
     * This method makes sure a tutorial's name, language, and creator is unique. It returns true
     * if the tutorial is valid (unique values), or false otherwise. This method is "synchronized"
     * so that this can only be completed once at any given moment.
     *
     * @param tutorial - the tutorial
     * @return true (on success) or false (on failure)
     */
    public synchronized boolean validateTutorial(Tutorial tutorial) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("Tutorial");
        FilterPredicate filter1 = new FilterPredicate("Name", FilterOperator.EQUAL, tutorial.getName());
        FilterPredicate filter2 = new FilterPredicate("Language", FilterOperator.EQUAL, tutorial.getLanguage());
        FilterPredicate filter3 = new FilterPredicate("Creator", FilterOperator.EQUAL, tutorial.getCreator());

        List list = new ArrayList();
        list.add(filter1);
        list.add(filter2);
        list.add(filter3);

        CompositeFilter filter = new CompositeFilter(Query.CompositeFilterOperator.AND, list);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        // if the item is null, we can create a new tutorial. If not, ask to change something
        Entity item = pq.asSingleEntity();
        return (item == null);
    }

    /**
     * This method is used when a user or admin deletes a tutorial. Note that this deletes everything related
     * to this tutorial (meaning also from the Tutorial tables and the Progression table). This method is
     * "synchronized" so that this can only be completed once at any given moment.
     *
     * @param id - the tutorial id
     * @return true on success, false otherwise
     */
    public synchronized boolean deleteTutorial(String id) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Query query = new Query("Tutorial");
        FilterPredicate filter = new FilterPredicate("Id", FilterOperator.EQUAL, id);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        Entity tutorial = pq.asSingleEntity();
        if (tutorial == null) {
            System.out.println("Tutorial not in database.");
            return false;
        }
        datastore.delete(tutorial.getKey());
        System.out.println("Tutorial deleted.");
        searchController.deleteTutorial(id);

        deleteTutorialInfo("Tutorial_Text", id, datastore);
        deleteTutorialInfo("Tutorial_Heading", id, datastore);
        deleteTutorialInfo("Tutorial_Picture", id, datastore);
        deleteTutorialInfo("Tutorial_Video", id, datastore);
        deleteTutorialInfo("Tutorial_Question", id, datastore);
        deleteTutorialInfo("Tutorial_Code", id, datastore);
        //deleteTutorialInfo("Progression", id, datastore);

        return true;
    }

    /**
     * This method retrieves all the tutorials that the user has done, based on the user's progression.
     * We retrieve the progression stats from a user based on the user's id number. This method is
     * "synchronized" so that this can only be completed once at any given moment. This will most likely
     * be used when adding data to the profile page.
     *
     * @param email - the user's email
     * @return the list of progression statistics for given tutorials
     */
    public synchronized ArrayList<Progression> getUserProgressions(String email) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        ArrayList<Progression> progressions = new ArrayList<Progression>();

        // query all progressions from the specific user
        Query query = new Query("Progression");
        FilterPredicate filter = new FilterPredicate("Email", FilterOperator.EQUAL, email);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        // for each entity, create a progression object and put it in the list
        for (Entity progression : pq.asIterable()) {
            String tutId = (String)progression.getProperty("Id");
            String classCode = (String)progression.getProperty("ClassCode");
            String score = (String)progression.getProperty("Score");
            String attempts = (String)progression.getProperty("Attempts");

            Progression prog = new Progression(email, tutId, classCode, score, attempts);
            progressions.add(prog);
        }

        return progressions;
    }

    /**
     * This method creates/updates a user's progression in a tutorial. On update, it deletes the old progression
     * and adds a new entity. This method is "synchronized" so that this can only be completed once at any given
     * moment. This will most likely be used when a user has completed a tutorial test.
     *
     * @param progress - the progress object
     */
    public synchronized void updateProgression(Progression progress) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("Progression");
        FilterPredicate filter = new FilterPredicate("Id", FilterOperator.EQUAL, progress.getTutorialId());
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        // if the item is null, we can create a new progression item. If not, update the progression item and delete the old one
        Entity item = pq.asSingleEntity();
        if (item != null) {
            datastore.delete(item.getKey());
        }

        Entity prog = new Entity("Progression");
        prog.setProperty("Email", progress.getEmail());
        prog.setProperty("Id", progress.getTutorialId());
        prog.setProperty("ClassCode", progress.getClassCode());
        prog.setProperty("Score", progress.getScore());
        prog.setProperty("Attempts", progress.getAttempts());
        datastore.put(prog);
    }

    /**
     * This method creates a class for a specific teacher. It creates a class code, stores the data, and
     * then sends the code back to the user. This method is "synchronized" so that this can only be
     * completed once at any given moment.
     *
     * @param name - the name of the class
     * @param email - the teacher's email
     * @return the class code
     */
    public synchronized String createClass(String name, String email) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        String classCode = createClassCode(LENGTH, datastore);

        Entity teacherClass = new Entity("Teacher_Class");
        teacherClass.setProperty("Email", email);
        teacherClass.setProperty("ClassCode", classCode);
        teacherClass.setProperty("Name", name);
        teacherClass.setProperty("Locked", "No");
        datastore.put(teacherClass);

        return classCode;
    }

    /**
     * This method tries to enter a student into a class. On success, the student is entered. This method is
     * "synchronized" so that this can only be completed once at any given moment.
     *
     * @param email - the student's email
     * @param classCode - the class code
     * @return the class name (success) or an error message (failure)
     */
    public synchronized String joinClass(String email, String classCode) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        // first check to see if the user is already in the class
        if (isUserInClass(email, classCode, datastore))
            return "Already in class.";

        // now, check to see if the class code is correct
        String name = getClassName(classCode, datastore);
        if (name.equals(""))
            return "Invalid class code.";

        // code's correct; enter the student and send the class name back
        Entity studentClass = new Entity("Student_Class");
        studentClass.setProperty("Email", email);
        studentClass.setProperty("ClassCode", classCode);
        datastore.put(studentClass);

        return name;
    }

    /**
     * This method deletes a class and all relevant information from the database. It first deletes from
     * the Teacher_Class, then the student(s) from the Student_Class, and finally all the tutorials relating
     * to this class (based on the classCode). This method is "synchronized" so that it can only be run once
     * at any given time.
     *
     * @param classCode - the class code
     */
    public synchronized void deleteClass(String classCode) {
        // delete from teacher and student
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        deleteClassInfo("Teacher_Class", classCode, datastore);
        deleteClassInfo("Student_Class", classCode, datastore);

        // now, get all the tutorials and delete it from the database
        Query query = new Query("Tutorial");
        FilterPredicate filter = new FilterPredicate("ClassCode", FilterOperator.EQUAL, classCode);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        for (Entity tutorial : pq.asIterable()) {
            deleteTutorial((String)tutorial.getProperty("Id"));
        }
    }

    /**
     * This method gets all classes that a student is in. It returns a list of classes. This method is
     * "synchronized" so that this can only be completed once at any given moment.
     *
     * @param email - the student's email
     * @return the list of classes
     */
    public synchronized ArrayList<ClassObj> getStudentClasses(String email) {
        ArrayList<ClassObj> classes = new ArrayList<ClassObj>();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        // get all class codes where a user is the student
        Query query = new Query("Student_Class");
        FilterPredicate filter = new FilterPredicate("Email", FilterOperator.EQUAL, email);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        // create the list
        for (Entity entity : pq.asIterable()) {
            ClassObj studClass = new ClassObj();

            // get the class code
            String classCode = (String)entity.getProperty("ClassCode");
            studClass.setClassCode(classCode);

            // get the class name
            studClass.setClassName(getClassName(classCode, datastore));

            // get the teacher name
            studClass.setTeacher(getTeacherName(classCode, datastore, 1));

            // add to classes
            classes.add(studClass);
        }

        return classes;
    }

    /**
     * This method removes a student from a specific class. This method is "synchronized" so
     * that this can only be completed once at any given moment.
     *
     * @param email - the student's email address
     * @param classCode - the class' code
     */
    public synchronized void removeStudentFromClass(String email, String classCode) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("Student_Class");
        FilterPredicate filter1 = new FilterPredicate("Email", FilterOperator.EQUAL, email);
        FilterPredicate filter2 = new FilterPredicate("ClassCode", FilterOperator.EQUAL, classCode);
        List list = new ArrayList();
        list.add(filter1);
        list.add(filter2);
        CompositeFilter filter = new CompositeFilter(Query.CompositeFilterOperator.AND, list);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        Entity entity = pq.asSingleEntity();
        if (entity != null) {
            datastore.delete(entity.getKey());
            System.out.println("Student " + email + " successfully removed.");
        }
    }

    /**
     * This method gets the students in one class, based on the class code. This method is "synchronized"
     * so that this can only be completed once at any given moment.
     *
     * @param classCode - the class code
     * @return a list of students
     */
    public synchronized ArrayList<User> getStudentsByClass(String classCode) {
        ArrayList<User> students = new ArrayList<User>();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        // get the students
        Query query = new Query("Student_Class");
        FilterPredicate filter = new FilterPredicate("ClassCode", FilterOperator.EQUAL, classCode);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);
        PreparedQuery pq2;

        // for each student, get their first and last name and add them to the list
        for (Entity student : pq.asIterable()) {
            User user = new User();
            String email = (String)student.getProperty("Email");
            user.setEmail(email);

            query = new Query("User");
            filter = new FilterPredicate("Email", FilterOperator.EQUAL, email);
            query.setFilter(filter);
            pq2 = datastore.prepare(query);

            Entity e = pq2.asSingleEntity();

            user.setFirstName((String)e.getProperty("FName"));
            user.setLastName((String)e.getProperty("LName"));

            students.add(user);
            System.out.println("Added student.");
        }

        return students;
    }

    /**
     * This method gets all the classes taught by a specified teacher. This method is "synchronized",
     * so it can only be executed once at any given moment.
     *
     * @param email - the teacher's email
     * @return the list of classes
     */
    public synchronized ArrayList<ClassObj> getClassesTeaching(String email) {
        ArrayList<ClassObj> classes = new ArrayList<ClassObj>();
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        String fullName = getTeacherName(email, datastore, 0);

        // query to get all the classes taught by a teacher
        Query query = new Query("Teacher_Class");
        FilterPredicate filter = new FilterPredicate("Email", FilterOperator.EQUAL, email);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        // for each result, make a class object and add it to the list
        for (Entity e : pq.asIterable()) {
            ClassObj c = new ClassObj();
            c.setClassName((String)e.getProperty("Name"));
            c.setClassCode((String)e.getProperty("ClassCode"));
            c.setTeacher(fullName);
            c.setLocked((String)e.getProperty("Locked"));
            classes.add(c);
        }

        return classes;
    }

    /**
     * This method returns whether the class given by the classCode is locked, meaning no student can
     * enter the class right now. It is "synchronized" so that this method can only happen once at any
     * given time.
     *
     * @param classCode - the class' code
     * @return true if the class is locked, false otherwise
     */
    public synchronized boolean getClassLockStatus(String classCode) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("Teacher_Class");
        FilterPredicate filter = new FilterPredicate("ClassCode", FilterOperator.EQUAL, classCode);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        Entity classEnt = pq.asSingleEntity();
        String locked = (String)classEnt.getProperty("Locked");
        return locked.equals("Yes");
    }

    /**
     * This method switches the class' lock status from locked to unlocked, and vice-versa. It loads
     * the old class in the datastore, copies everything but the lock status into a new class entity,
     * and then updates the lock status. After, it deletes the old class from the store and inserts
     * the new class. This method is "synchronized" so that it can only be executed once at any given
     * time.
     *
     * @param classCode - the class' code
     */
    public synchronized void updateClassLockStatus(String classCode) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("Teacher_Class");
        FilterPredicate filter = new FilterPredicate("ClassCode", FilterOperator.EQUAL, classCode);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        // first, we'll get the old class status and create the new class with the same values
        Entity oldClass = pq.asSingleEntity();
        Entity newClass = new Entity("Teacher_Class");

        newClass.setProperty("Email", oldClass.getProperty("Email").toString());
        newClass.setProperty("ClassCode", classCode);
        newClass.setProperty("Name", oldClass.getProperty("Name").toString());

        // update the lock
        if ((oldClass.getProperty("Locked").toString()).equals("Yes"))
            newClass.setProperty("Locked", "No");
        else
            newClass.setProperty("Locked", "Yes");

        // now, delete the old class and insert the new one
        datastore.delete(oldClass.getKey());
        datastore.put(newClass);
    }

    /**
     * This method is called when a user searches for a specific tutorial. This can be called dynamically, however
     * the efficiency of this method may be slow in some cases. Note that the Datastore does not have a LIKE
     * operator to make life easy, so this is an equivalent to it. This method is "synchronized" so that this can
     * only be completed once at any given moment.
     *
     * @param name - the string to be matched
     * @return the list of tutorials matching the string
     */
    public synchronized ArrayList<Tutorial> searchTutorials(String name) {
        // set everything up: the datastore, query, filter, prepared query, and the list of tutorials
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query;
        FilterPredicate filter;
        PreparedQuery pq;
        ArrayList<Tutorial> tutorials = new ArrayList<Tutorial>();

        // get the list of tutorials
        ArrayList<String> list = searchController.searchTutorials(name);

        // for each result from the search, get the tutorial
        for (String identifier : list) {
            query = new Query("Tutorial");
            filter = new FilterPredicate("Id", FilterOperator.EQUAL, identifier);
            query.setFilter(filter);
            pq = datastore.prepare(query);

            // and create a new tutorial from this query, and add it to the list
            Entity tutorial = pq.asSingleEntity();
            System.out.println(tutorial.getProperty("Name"));
            String id = tutorial.getProperty("Id").toString();
            String tutName = tutorial.getProperty("Name").toString();
            String lang = tutorial.getProperty("Language").toString();
            String creator = tutorial.getProperty("Creator").toString();
            String code = tutorial.getProperty("ClassCode").toString();
            String archived = tutorial.getProperty("Archived").toString();

            Tutorial tut = new Tutorial(id, tutName, lang, creator, code, archived);
            tutorials.add(tut);
        }

        return tutorials;
    }

    /**
     * This is a private method invoked when we create a new tutorial. It returns the next id to be used
     * for a specific tutorial. These id's are unique for every tutorial, and is as close to auto-
     * incremented as it gets. Note that this should not be called outside of this class.
     *
     * @return the id to be used
     */
    private synchronized int getId() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("ID");
        PreparedQuery pq = datastore.prepare(query);
        Entity identify = pq.asSingleEntity();

        // if the entity is null, this is the first tutorial. So we create an entity and add it
        if (identify == null) {
            System.out.println("First tutorial.");
            Entity idEntity = new Entity("ID");
            idEntity.setProperty("Number", "1");
            datastore.put(idEntity);
            return 1;
        }

        // else, we update the id, store it in the database, and return it
        int id = Integer.parseInt((String) identify.getProperty("Number"));
        id++;

        // delete the previous entity, store the new one
        datastore.delete(identify.getKey());

        Entity eItem = new Entity("ID");
        eItem.setProperty("Number", Integer.toString(id));
        datastore.put(eItem);
        return id;
    }

    /**
     * This helper method gets the basic tutorial information based on the tutorial id. It creates a Tutorial
     * object and returns it. This method is "synchronized" so that this can only be completed once at any
     * given moment.
     *
     * @param id - the tutorial id
     * @param datastore - the datastore instance
     * @return the Tutorial object
     */
    private synchronized Tutorial getTutorialInformation(String id, DatastoreService datastore) {
        Tutorial tutorial = new Tutorial();

        // query for the tutorial
        Query query = new Query("Tutorial");
        FilterPredicate filter = new FilterPredicate("Id", FilterOperator.EQUAL, id);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        // now fill the tutorial object and return it
        Entity tut = pq.asSingleEntity();
        tutorial.setId((String)tut.getProperty("Id"));
        tutorial.setName((String)tut.getProperty("Name"));
        tutorial.setLanguage((String) tut.getProperty("Language"));
        tutorial.setCreator((String)tut.getProperty("Creator"));
        tutorial.setClassCode((String)tut.getProperty("ClassCode"));
        tutorial.setArchived((String)tut.getProperty("Archived"));
        return tutorial;
    }

    /**
     * This helper method retrieves text info from the datastore, based on the tutorial's id number.
     * This method is "synchronized" so that this can only be completed once at any given moment.
     *
     * @param id - the tutorial id
     * @param datastore - the datastore instance
     * @return the Tutorial_Text list
     */
    private synchronized ArrayList<Tutorial_Text> getTutorialTextInfo(String id, DatastoreService datastore) {
        ArrayList<Tutorial_Text> list = new ArrayList<Tutorial_Text>();
        Query query = new Query("Tutorial_Text");
        FilterPredicate filter = new FilterPredicate("Id", FilterOperator.EQUAL, id);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        for (Entity entity : pq.asIterable()) {
            Tutorial_Text tut = new Tutorial_Text();
            tut.setContent((String)entity.getProperty("Content"));
            tut.setOrder((String)entity.getProperty("Order"));
            list.add(tut);
            System.out.println("Tutorial_Text loaded.");
        }

        return list;
    }

    /**
     * This helper method enters tutorial text information into the datastore. This method is
     * "synchronized" so that this can only be completed once at any given moment.
     *
     * @param datastore - the datastore instance
     * @param id - the tutorial id
     * @param list - the Tutorial_Text list
     */
    private synchronized void enterTutorialTextInDatastore(DatastoreService datastore, String id, ArrayList<Tutorial_Text> list) {
        for (Tutorial_Text text : list) {
            Entity txt = new Entity("Tutorial_Text");
            txt.setProperty("Id", id);
            txt.setProperty("Content", text.getContent());
            txt.setProperty("Order", text.getOrder());
            datastore.put(txt);
            System.out.println("Tutorial_Text entered.");
        }
    }

    /**
     * This helper method retrieves heading info from the datastore, based on the tutorial's id number.
     * This method is "synchronized" so that this can only be completed once at any given moment.
     *
     * @param id - the tutorial id
     * @param datastore - the datastore instance
     * @return the Tutorial_Heading list
     */
    private synchronized ArrayList<Tutorial_Heading> getTutorialHeadingInfo(String id, DatastoreService datastore) {
        ArrayList<Tutorial_Heading> list = new ArrayList<Tutorial_Heading>();
        Query query = new Query("Tutorial_Heading");
        FilterPredicate filter = new FilterPredicate("Id", FilterOperator.EQUAL, id);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        for (Entity entity : pq.asIterable()) {
            Tutorial_Heading tut = new Tutorial_Heading();
            tut.setContent((String)entity.getProperty("Content"));
            tut.setOrder((String)entity.getProperty("Order"));
            list.add(tut);
            System.out.println("Tutorial_Heading loaded.");
        }

        return list;
    }

    /**
     * This helper method enters tutorial heading information into the datastore. This method is
     * "synchronized" so that this can only be completed once at any given moment.
     *
     * @param datastore - the datastore instance
     * @param id - the tutorial id
     * @param list - the Tutorial_Heading list
     */
    private synchronized void enterTutorialHeadingInDatastore(DatastoreService datastore, String id, ArrayList<Tutorial_Heading> list) {
        for (Tutorial_Heading heading : list) {
            Entity head = new Entity("Tutorial_Heading");
            head.setProperty("Id", id);
            head.setProperty("Content", heading.getContent());
            head.setProperty("Order", heading.getOrder());
            datastore.put(head);
            System.out.println("Tutorial_Heading entered.");
        }
    }

    /**
     * This helper method retrieves picture info from the datastore, based on the tutorial's id number.
     * This method is "synchronized" so that this can only be completed once at any given moment.
     *
     * @param id - the tutorial id
     * @param datastore - the datastore instance
     * @return the Tutorial_Picture list
     */
    private synchronized ArrayList<Tutorial_Picture> getTutorialPictureInfo(String id, DatastoreService datastore) {
        ArrayList<Tutorial_Picture> list = new ArrayList<Tutorial_Picture>();
        Query query = new Query("Tutorial_Picture");
        FilterPredicate filter = new FilterPredicate("Id", FilterOperator.EQUAL, id);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        for (Entity entity : pq.asIterable()) {
            Tutorial_Picture tut = new Tutorial_Picture();
            tut.setLink((String)entity.getProperty("Link"));
            tut.setOrder((String)entity.getProperty("Order"));
            list.add(tut);
            System.out.println("Tutorial_Picture loaded.");
        }

        return list;
    }

    /**
     * This helper method enters tutorial picture information into the datastore. This method is
     * "synchronized" so that this can only be completed once at any given moment.
     *
     * @param datastore - the datastore instance
     * @param id - the tutorial id
     * @param list - the Tutorial_Picture list
     */
    private synchronized void enterTutorialPictureInDatastore(DatastoreService datastore, String id, ArrayList<Tutorial_Picture> list) {
        for (Tutorial_Picture picture : list) {
            Entity pic = new Entity("Tutorial_Picture");
            pic.setProperty("Id", id);
            pic.setProperty("Link", picture.getLink());
            pic.setProperty("Order", picture.getOrder());
            datastore.put(pic);
            System.out.println("Tutorial_Picture entered.");
        }
    }

    /**
     * THis helper method retrieves video info from the datastore, based on the tutorial's id number.
     * This method is "synchronized" so that this can only be completed once at any given moment.
     *
     * @param id - the tutorial id
     * @param datastore - the datastore instance
     * @return the Tutorial_Video list
     */
    private synchronized ArrayList<Tutorial_Video> getTutorialVideoInfo(String id, DatastoreService datastore) {
        ArrayList<Tutorial_Video> list = new ArrayList<Tutorial_Video>();
        Query query = new Query("Tutorial_Video");
        FilterPredicate filter = new FilterPredicate("Id", FilterOperator.EQUAL, id);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        for (Entity entity : pq.asIterable()) {
            Tutorial_Video tut = new Tutorial_Video();
            tut.setLink((String)entity.getProperty("Link"));
            tut.setOrder((String)entity.getProperty("Order"));
            list.add(tut);
            System.out.println("Tutorial_Video loaded.");
        }

        return list;
    }

    /**
     * This helper method enters tutorial video information into the datastore. This method is
     * "synchronized" so that this can only be completed once at any given moment.
     *
     * @param datastore - the datastore instance
     * @param id - the tutorial id
     * @param list - the Tutorial_Video list
     */
    private synchronized void enterTutorialVideoInDatastore(DatastoreService datastore, String id, ArrayList<Tutorial_Video> list) {
        for (Tutorial_Video video : list) {
            Entity vid = new Entity("Tutorial_Video");
            vid.setProperty("Id", id);
            vid.setProperty("Link", video.getLink());
            vid.setProperty("Order", video.getOrder());
            datastore.put(vid);
            System.out.println("Tutorial_Video entered.");
        }
    }

    /**
     * This helper method retrieves question info from the datastore, based on the tutorial's id number.
     * This method is "synchronized" so that this can only be completed once at any given moment.
     *
     * @param id - the tutorial id
     * @param datastore - the datastore instance
     * @return the Tutorial_Question list
     */
    private synchronized ArrayList<Tutorial_Question> getTutorialQuestionInfo(String id, DatastoreService datastore) {
        ArrayList<Tutorial_Question> list = new ArrayList<Tutorial_Question>();
        Query query = new Query("Tutorial_Question");
        FilterPredicate filter = new FilterPredicate("Id", FilterOperator.EQUAL, id);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        for (Entity entity : pq.asIterable()) {
            Tutorial_Question tut = new Tutorial_Question();
            tut.setQid((String)entity.getProperty("Qid"));
            tut.setQuestion((String)entity.getProperty("Question"));
            tut.setQuesType((String)entity.getProperty("Type"));

            List choices = (List)entity.getProperty("Choices");
            for (int i = 0; i < choices.size(); i++) {
                String choice = (String)choices.get(i);
                tut.getChoices().add(choice);
                System.out.println("Choice: " + choice);
            }

            tut.setAnswer((String)entity.getProperty("Answer"));
            list.add(tut);
            System.out.println("Tutorial_Question loaded.");
        }

        return list;
    }

    /**
     * This helper method enters tutorial question information into the datastore. This method is
     * "synchronized" so that this can only be completed once at any given moment.
     *
     * @param datastore - the datastore instance
     * @param id - the tutorial id
     * @param list - the Tutorial_Question list
     */
    private synchronized void enterTutorialQuestionInDatastore(DatastoreService datastore, String id, ArrayList<Tutorial_Question> list) {
        for (Tutorial_Question question : list) {
            Entity ques = new Entity("Tutorial_Question");
            ques.setProperty("Id", id);
            ques.setProperty("Qid", question.getQid());
            ques.setProperty("Question", question.getQuestion());
            ques.setProperty("Type", question.getQuesType());

            List choices = question.getChoices();
            ques.setProperty("Choices", choices);
            ques.setProperty("Answer", question.getAnswer());
            datastore.put(ques);
            System.out.println("Tutorial_Question entered.");
        }
    }

    /**
     * This helper method retrieves code info from the datastore, based on the tutorial's id number.
     * This method is "synchronized" so that this can only be completed once at any given moment.
     *
     * @param id - the tutorial id
     * @param datastore - the datastore instance
     * @return the Tutorial_Code list
     */
    private synchronized ArrayList<Tutorial_Code> getTutorialCodeInfo(String id, DatastoreService datastore) {
        ArrayList<Tutorial_Code> list = new ArrayList<Tutorial_Code>();
        Query query = new Query("Tutorial_Code");
        FilterPredicate filter = new FilterPredicate("Id", FilterOperator.EQUAL, id);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        for (Entity entity : pq.asIterable()) {
            Tutorial_Code tut = new Tutorial_Code();
            String qid = (String)entity.getProperty("Qid");
            System.out.println("DATABASE QID: " + qid);
            if(qid != null) {
                tut.setQid(qid);
                tut.setLanguage((String)entity.getProperty("Language"));
                tut.setDesc((String)entity.getProperty("Description"));
                tut.setCode((String)entity.getProperty("Code"));

                List inputs = (List)entity.getProperty("Inputs");
                if (inputs != null) {
                    for (int i = 0; i < inputs.size(); i++) {
                        String input = (String) inputs.get(i);
                        tut.getInputs().add(input);
                        System.out.println("Input: " + input);
                    }

                    List outputs = (List) entity.getProperty("Outputs");
                    for (int i = 0; i < outputs.size(); i++) {
                        String output = (String) outputs.get(i);
                        tut.getOutputs().add(output);
                        System.out.println("Output: " + output);
                    }
                }

                list.add(tut);
                System.out.println("Tutorial_Code loaded.");
            }
        }

        return list;
    }

    /**
     * This helper method enters tutorial code information into the datastore. This method is
     * "synchronized" so that this can only be completed once at any given moment.
     *
     * @param datastore - the datastore instance
     * @param id - the tutorial id
     * @param list - the Tutorial_Code list
     */
    private synchronized void enterTutorialCodeInDatastore(DatastoreService datastore, String id, ArrayList<Tutorial_Code> list) {
        for (Tutorial_Code code : list) {
            Entity c = new Entity("Tutorial_Code");
            c.setProperty("Id", id);
            c.setProperty("Qid", code.getQid());
            c.setProperty("Language", code.getLanguage());
            c.setProperty("Description", code.getDesc());
            c.setProperty("Code", code.getCode());

            // inputs and outputs
            List inputs = code.getInputs();
            c.setProperty("Inputs", inputs);
            List outputs = code.getOutputs();
            c.setProperty("Outputs", outputs);

            datastore.put(c);
            System.out.println("Tutorial_Code entered.");
        }
    }

    /**
     * This helper method deletes a specific tutorial information from the datastore, given the type and id.
     * This method is "synchronized" so that this can only be completed once at any given moment.
     *
     * @param kindQuery - the type of Tutorial information (i.e., Tutorial_Text)
     * @param tutId - the tutorial id
     * @param datastore - the datastore instance
     */
    private synchronized void deleteTutorialInfo(String kindQuery, String tutId, DatastoreService datastore) {
        Query query = new Query(kindQuery);
        FilterPredicate filter = new FilterPredicate("Id", FilterOperator.EQUAL, tutId);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        for (Entity entity : pq.asIterable()) {
            datastore.delete(entity.getKey());
            System.out.println(kindQuery + " deleted.");
        }
    }

    /**
     * This helper method deletes specific class information from the datastore, given the type and class code.
     * This method is "synchronized" so that this can only be completed once at any given moment.
     *
     * @param kindQuery - the type of Class information (i.e., Teacher_Class)
     * @param classCode - the class's code
     * @param datastore - the datastore instance
     */
    private synchronized void deleteClassInfo(String kindQuery, String classCode, DatastoreService datastore) {
        Query query = new Query(kindQuery);
        FilterPredicate filter = new FilterPredicate("ClassCode", FilterOperator.EQUAL, classCode);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        for (Entity entity : pq.asIterable()) {
            datastore.delete(entity.getKey());
            System.out.println(kindQuery + " deleted.");
        }
    }

    /**
     * This helper method creates a unique class code. This method is "synchronized" so that this
     * can only be completed once at any given moment.
     *
     * @param length - the length of the string
     * @param datastore - the datastore instance
     * @return the class code
     */
    private synchronized String createClassCode(int length, DatastoreService datastore) {
        StringBuilder sb = new StringBuilder(length);
        String classCode = "";

        // build the code
        while (classCode.equals("")) {
            for (int i = 0; i < length; i++) {
                sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
            }
            String temp = sb.toString();

            // check if the code is already used
            Query query = new Query("Teacher_Class");
            FilterPredicate filter = new FilterPredicate("ClassCode", FilterOperator.EQUAL, temp);
            query.setFilter(filter);
            PreparedQuery pq = datastore.prepare(query);

            // if the code is not used yet, the entity is null, so we make it the new class code.
            Entity entity = pq.asSingleEntity();
            if (entity == null)
                classCode = temp;
        }

        return classCode;
    }

    /**
     * This method checks if the uesr is already in the class. This method is
     * "synchronized" so that this can only be completed once at any given moment.
     *
     * @param email - the user email
     * @param classCode - the class code
     * @param datastore - the datastore instance
     * @return true if the user is in the class, false otherwise
     */
    private synchronized boolean isUserInClass(String email, String classCode, DatastoreService datastore) {
        Query query = new Query("Student_Class");
        FilterPredicate filter1 = new FilterPredicate("Email", FilterOperator.EQUAL, email);
        FilterPredicate filter2 = new FilterPredicate("ClassCode", FilterOperator.EQUAL, classCode);

        List list = new ArrayList();
        list.add(filter1);
        list.add(filter2);

        CompositeFilter filter = new CompositeFilter(Query.CompositeFilterOperator.AND, list);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        Entity entity = pq.asSingleEntity();
        return (entity != null);
    }

    /**
     * This helper method gets the class name based on the class code. This method is
     * "synchronized" so that this can only be completed once at any given moment.
     *
     * @param classCode - the class code
     * @param datastore - the datastore instance
     * @return the class name, or "" if there was a problem
     */
    private synchronized String getClassName(String classCode, DatastoreService datastore) {
        String className = "";
        Query query = new Query("Teacher_Class");
        FilterPredicate filter = new FilterPredicate("ClassCode", FilterOperator.EQUAL, classCode);
        query.setFilter(filter);
        PreparedQuery pq = datastore.prepare(query);

        Entity entity = pq.asSingleEntity();
        if (entity != null)
            className = (String) entity.getProperty("Name");

        return className;
    }

    /**
     * This helper method gets the teacher's full name. It gets either the classCode or the teacher's email.
     * If it gets the classCode, it first queries the database to get the email. If it gets the email, it
     * uses that email to get the full name from the user's table. This method is "synchronized" so that this
     * can only be completed once at any given moment.
     *
     * @param information - the class code or email (as specified by the classCode int)
     * @param datastore - the datastore instance
     * @param classCode - 0 if we just need the email, 1 if we're getting the classCode
     * @return the teacher's full name
     */
    private synchronized String getTeacherName(String information, DatastoreService datastore, int classCode) {
        String fullName;
        String teacherEmail;

        Query query;
        FilterPredicate filter;
        PreparedQuery pq;

        // get the class code, if necessary
        if (classCode == 1) {
            query = new Query("Teacher_Class");
            filter = new FilterPredicate("ClassCode", FilterOperator.EQUAL, information);
            query.setFilter(filter);
            pq = datastore.prepare(query);

            Entity entity = pq.asSingleEntity();
            teacherEmail = (String) entity.getProperty("Email");
        } else {
            teacherEmail = information;
        }

        // and now get their email
        query = new Query("User");
        filter = new FilterPredicate("Email", FilterOperator.EQUAL, teacherEmail);
        query.setFilter(filter);
        pq = datastore.prepare(query);

        Entity entity = pq.asSingleEntity();
        String firstName = (String)entity.getProperty("FName");
        String lastName = (String)entity.getProperty("LName");
        fullName = firstName + " " + lastName;

        return fullName;
    }

    public void clearDatabase() {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query query = new Query("User");
        PreparedQuery pq = datastore.prepare(query);
        for (Entity item : pq.asIterable()) {
            datastore.delete(item.getKey());
        }
        query = new Query("Admin");
        pq = datastore.prepare(query);
        for (Entity item : pq.asIterable()) {
            datastore.delete(item.getKey());
        }
        query = new Query("Tutorial");
        pq = datastore.prepare(query);
        for (Entity item : pq.asIterable()) {
            datastore.delete(item.getKey());
        }
        query = new Query("Tutorial_Text");
        pq = datastore.prepare(query);
        for (Entity item : pq.asIterable()) {
            datastore.delete(item.getKey());
        }
        query = new Query("Tutorial_Heading");
        pq = datastore.prepare(query);
        for (Entity item : pq.asIterable()) {
            datastore.delete(item.getKey());
        }
        query = new Query("Tutorial_Picture");
        pq = datastore.prepare(query);
        for (Entity item : pq.asIterable()) {
            datastore.delete(item.getKey());
        }
        query = new Query("Tutorial_Video");
        pq = datastore.prepare(query);
        for (Entity item : pq.asIterable()) {
            datastore.delete(item.getKey());
        }
        query = new Query("Tutorial_Question");
        pq = datastore.prepare(query);
        for (Entity item : pq.asIterable()) {
            datastore.delete(item.getKey());
        }
        query = new Query("Tutorial_Code");
        pq = datastore.prepare(query);
        for (Entity item : pq.asIterable()) {
            datastore.delete(item.getKey());
        }

        searchController.clearDatabase();

        query = new Query("Progression");
        pq = datastore.prepare(query);
        for (Entity item : pq.asIterable()) {
            datastore.delete(item.getKey());
        }
        query = new Query("Teacher_Class");
        pq = datastore.prepare(query);
        for (Entity item : pq.asIterable()) {
            datastore.delete(item.getKey());
        }
        query = new Query("Student_Class");
        pq = datastore.prepare(query);
        for (Entity item : pq.asIterable()) {
            datastore.delete(item.getKey());
        }
        query = new Query("ID");
        pq = datastore.prepare(query);
        for (Entity item : pq.asIterable()) {
            datastore.delete(item.getKey());
        }
    }
}
