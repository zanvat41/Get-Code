package com.data;

import java.io.Serializable;

/**
 * This is an object that stores picture links from a tutorial.
 * Created by Matthew on 4/5/2017.
 */
public class Tutorial_Picture implements Serializable {
    private String link;            // the picture link, as a string
    private String order;           // the order number in the tutorial, starting with "1" (for presenting to the page)

    // constructors
    public Tutorial_Picture() {}
    public Tutorial_Picture(String link, String order) {
        this.link = link;
        this.order = order;
    }

    // basic accessor/mutator methods
    public String getLink() { return link; }
    public void setLink(String link) { this.link = link; }
    public String getOrder() { return order; }
    public void setOrder(String order) { this.order = order; }
}
