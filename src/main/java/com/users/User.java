package com.users;

import java.io.Serializable;

/**
 * Created by Kieran on 4/6/2017.
 */
public class User implements Serializable {
    String firstName;
    String lastName;
    String email;

    public User() {
        firstName = "";
        lastName = "";
        email = "";
    }

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void clear() {
        this.firstName = null;
        this.lastName = null;
        this.email = null;
    }
}
