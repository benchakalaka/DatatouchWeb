package com.datascope.ui.email.elements;

import java.util.ArrayList;

public class EmailGroupGridItem {

    private String name;
    private int id;
    private EmailGridItem.List emailGridItems;

    public EmailGroupGridItem(int id, String name, EmailGridItem.List emailGridItems) {
        this.name = name;
        this.id = id;
        this.emailGridItems = emailGridItems;
    }

    public EmailGroupGridItem(int id, String name) {
        this(id,name, new EmailGridItem.List());
    }

    public EmailGridItem.List getEmailGridItems() {
        return emailGridItems;
    }

    public void setEmailGridItems(EmailGridItem.List emailGridItems) {
        this.emailGridItems = emailGridItems;
    }

    private ArrayList<EmailGridItem> emails;

    public ArrayList<EmailGridItem> getEmails() {
        return emails;
    }

    public void setEmails(ArrayList<EmailGridItem> emails) {
        this.emails = emails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public boolean containsEmailTemplate(int emailId) {
        return emailGridItems.stream().anyMatch(item -> item.getId() == emailId);
    }


    public static class List extends ArrayList<EmailGroupGridItem> {
    }
}
