package com.database;

import com.data.Tutorial;
import com.google.appengine.api.search.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the controller for the Search API for the database. This will help with searching for tutorials.
 * Here, we will only store tutorials, and it will only be the basic information for the tutorial. Thus, we
 * will only store the tutorial id, name, programming language, creator, and class code.
 *
 * Created by Matthew on 5/13/2017.
 */
class SearchController {
    // no-arg constructor
    SearchController() {}

    /**
     * This method creates a Document version of a Tutorial in the Index. Note that this version
     * is strictly for searching. It adds the id, name, language, creator, and class code, and
     * also includes a trick to get partial strings from the database. This is synchronized so
     * that it can only be run once at a time.
     *
     * @param tutorial - the tutorial object to be made into a Document
     */
    synchronized void createTutorial(Tutorial tutorial) {
        // first, we need to build the substrings for the name, and combine them with a space
        List<String> substrings = buildSubstrings(tutorial.getName());
        String combinedString = stringify(substrings);

        // create the document
        Document doc = Document.newBuilder()
                .setId(tutorial.getId())
                .addField(Field.newBuilder().setName("Name").setText(tutorial.getName()).build())
                .addField(Field.newBuilder().setName("SearchName").setText(combinedString).build())
                .addField(Field.newBuilder().setName("Language").setText(tutorial.getLanguage()).build())
                .addField(Field.newBuilder().setName("Creator").setText(tutorial.getCreator()).build())
                .addField(Field.newBuilder().setName("ClassCode").setText(tutorial.getClassCode()).build())
                .build();

        // get the index, and put the document in the index
        Index index = getIndex();
        index.put(doc);

        System.out.println("New Search Document Created: " + doc.getId());
    }

    /**
     * This method deletes a Document version of a tutorial. It takes the tutorial id, and deletes it
     * from the index, and prints the id number. This method is synchronized so that it can only be run
     * once at a time.
     *
     * @param id - the tutorial id (from the Document) to be deleted
     */
    synchronized void deleteTutorial(String id) {
        Index index = getIndex();
        String sId = index.get(id).getId();
        index.delete(id);
        System.out.println("Deleted Search Document: " + sId);
    }

    /**
     * This method searches for tutorials based on the name parameter and returns a list of the
     * tutorial id's. Note that since the Search API for GAE does not allow partial strings, we
     * used a neat trick to satisfy partial strings. This isi synchronized so that it can only
     * be run once at any given time.
     *
     * @param name - the name the user queried.
     * @return the list of tutorial id's
     */
    synchronized ArrayList<String> searchTutorials(String name) {
        // search for the name
        ArrayList<String> list = new ArrayList<String>();
        Results<ScoredDocument> results = getIndex().search(name);

        // for each result, put the id in the list, and then return the list
        for (ScoredDocument doc : results) {
            System.out.println("Got tutorial: " + doc.getId() + " " + doc.getOnlyField("Name").getText());
            list.add(doc.getId());
        }

        return list;
    }

    /**
     * This method clears the search database. Used when the main database is deleted. This method is synchronized
     * so it can only be run once at a time.
     */
    synchronized void clearDatabase() {
        ArrayList<String> tutorials = searchTutorials("");
        for (String id : tutorials) {
            getIndex().delete(id);
            System.out.println("Deleted id: " + id);
        }
    }

    /**
     * This helper method gets the currently used index, so we can append documents and search in this
     * application.
     *
     * @return the index
     */
    private synchronized Index getIndex() {
        IndexSpec indexSpec = IndexSpec.newBuilder().setName("searchIndex").build();
        return SearchServiceFactory.getSearchService().getIndex(indexSpec);
    }

    /**
     * This helper method creates a list of substrings based on the name given (the tutorial name).
     * For example, if the name was "abcd", the list returned would be [a, b, c, d, ab, abc, abcd,
     * bc, bcd, cd]. This method is synchronized so that it can only be run once at a time.
     *
     * @param name - the name of the tutorial
     * @return the list of substrings
     */
    private synchronized List<String> buildSubstrings(String name) {
        List<String> list = new ArrayList<String>();
        String[] words = name.split(" ");

        // for each word, fetch all combinations
        for (String word : words) {
            int size = 1;

            while (size != word.length()) {
                for (int i = 0; i < word.length() - size + 1; i++) {
                    String sub = word.substring(i, i + size);
                    list.add(sub);
                }

                size++;
            }
        }

        // return the list of strings
        return list;
    }

    /**
     * This helper method retrieves the list of strings and combines them, separated by a space.
     * This is how we are able to use partial matching for searching. This method is synchronized
     * so that it can be run only once at any given time.
     *
     * @param list - the list of strings
     * @return the elongated string
     */
    private synchronized String stringify(List<String> list) {
        String str = "";

        // if there is something in the list, then we add to the string
        if (list.size() > 0) {
            str = list.get(0);

            for (int i = 1; i < list.size(); i++) {
                str += " " + list.get(i);
            }
        }

        // finally, return the string
        return str;
    }
}
