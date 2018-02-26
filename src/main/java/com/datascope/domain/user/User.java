package com.datascope.domain.user;



import com.datascope.domain.core.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import static org.apache.commons.lang3.StringUtils.*;


public class User extends Model {

    @JsonProperty("Id")
    private int Id;

    @JsonProperty("FullName")
    private String FullName;

    @JsonProperty("Token")
    private String Token;

    @JsonProperty("IsAdmin")
    private boolean IsAdmin;

    @JsonProperty("CompanyId")
    private int CompanyId;

    public int getId() {
        return this.Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }


    public String getFullName() {
        return this.FullName;
    }

    public void setFullName(String FullName) {
        this.FullName = FullName;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public boolean isAdmin() {
        return IsAdmin;
    }

    public void setAdmin(boolean admin) {
        IsAdmin = admin;
    }


    public boolean getIsAdmin() {
        return this.IsAdmin;
    }

    public void setIsAdmin(boolean IsAdmin) {
        this.IsAdmin = IsAdmin;
    }



    public int getCompanyId() {
        return this.CompanyId;
    }

    public void setCompanyId(int CompanyId) {
        this.CompanyId = CompanyId;
    }


    // TODO: Come up with abstraction or generic
    public static class List extends ArrayList<User> {
        public static User.List empty() {
            return new User.List();
        }

        public User.List findByNamePattern(String pattern) {
            User.List usersMatchedPattern = empty();
            this.stream().filter(user -> user.fullNameContains(pattern)).forEach(usersMatchedPattern::add);

            return usersMatchedPattern;
        }
    }

    private boolean fullNameContains(String pattern) {
        return containsIgnoreCase(getFullName(),pattern);
    }

}
