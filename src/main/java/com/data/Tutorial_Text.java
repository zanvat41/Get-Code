package com.data;

import java.io.Serializable;

/**
 * This is an object that stores string content from a tutorial.
 * Created by Matthew on 4/5/2017.
 */
public class Tutorial_Text implements Serializable {
    private String content;     // the content of the text (text presented on the page)
    private String order;       // the order number in the tutorial, starting with "1" (for presenting to the page)

    // constructors
    public Tutorial_Text() {}
    public Tutorial_Text(String content, String order) {
        this.content = content;
        this.order = order;
    }

    // basic accessor/mutator methods
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getOrder() { return order; }
    public void setOrder(String order) { this.order = order; }
}
