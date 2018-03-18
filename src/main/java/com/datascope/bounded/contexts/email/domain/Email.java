package com.datascope.bounded.contexts.email.domain;

import com.datascope.bounded.contexts.core.domain.Model;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Email extends Model {

    @JsonProperty("Id")
    private int id ;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("FullName")
    private String FullName ;

    @JsonProperty("FirstName")
    private String FirstName;

    @JsonProperty("LastName")
    private String LastName ;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public static class List extends ArrayList<Email>{}
}
