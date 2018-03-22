package com.datascope.ui.email.elements;

import com.datascope.bounded.contexts.email.domain.EmailTemplate;

import java.util.ArrayList;

public class EmailGridItem {

    private String lastName;
    private int id;
    private String email;
    private String name;
    private boolean isActive;

    public EmailGridItem(int id, String email, String name, String lastName) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public static EmailGridItem fromEmail(EmailTemplate email) {
        return new EmailGridItem(
                email.getId(),
                email.getEmail(),
                email.getFirstName(),
                email.getLastName());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class List extends ArrayList<EmailGridItem> {
    }
}
