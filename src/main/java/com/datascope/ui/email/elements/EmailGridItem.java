package com.datascope.ui.email.elements;

import com.datascope.bounded.contexts.email.domain.EmailTemplate;

import java.util.ArrayList;

public class EmailGridItem {


    private int id;
    private String email;
    private final String fullName;

    public EmailGridItem(int id, String email, String fullName) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public static EmailGridItem fromEmail(EmailTemplate email){
        return new EmailGridItem(
                email.getId(),
                email.getEmail(),
                email.getFullName());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static class List extends ArrayList<EmailGridItem>{}
}
