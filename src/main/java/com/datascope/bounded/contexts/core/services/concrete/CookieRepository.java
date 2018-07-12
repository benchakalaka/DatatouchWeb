package com.datascope.bounded.contexts.core.services.concrete;

import com.datascope.bounded.contexts.core.services.RequestInfo;
import com.vaadin.server.VaadinService;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;


import javax.servlet.http.Cookie;


import static org.springframework.web.context.WebApplicationContext.SCOPE_REQUEST;

@Component
@Scope(value = SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CookieRepository implements RequestInfo {

    private Integer siteId = null;
    private String database = null;
    private String rootUrl = null;

    private Integer userId = null;
    private String token = null;


    public CookieRepository() {
    }

    private String getCookieValue(String name) {
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }

    private void setCookieValue(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (value == null) {
            cookie.setMaxAge(0);
        }
        VaadinService.getCurrentResponse().addCookie(cookie);
    }


    public void setLoginInfo(int userId, String token) {
        this.userId = userId;
        this.token =  token;
        setCookieValue(RepositoryAttributeNames.USER_ID_NAME, String.valueOf(userId));
        setCookieValue(RepositoryAttributeNames.TOKEN_NAME, token);
    }

    public int getUserId() {
        if (userId == null) {
            String value = getCookieValue(RepositoryAttributeNames.USER_ID_NAME);
            userId = value.isEmpty() ? 0 : Integer.valueOf(value);
        }
        return userId;
    }

    public boolean hasUser() {
        return getUserId() > 0;
    }

    public void clearLoginInfo() {
        this.userId = null;
        this.token = null;
        setCookieValue(RepositoryAttributeNames.USER_ID_NAME, null);
        setCookieValue(RepositoryAttributeNames.TOKEN_NAME, null);
    }

    @Override
    public String toString() {
        return String.format("[SiteId:%s\nDatabase:%s\nToken:%s]", getSiteId(), getDatabase(), getToken());
    }

    @Override
    public int getSiteId() {
        if (siteId == null) {
            String value = getCookieValue(RepositoryAttributeNames.SITE_ID_NAME);
            siteId = value.isEmpty() ? 0 : Integer.valueOf(value);
        }
        return siteId;
    }

    @Override
    public String getDatabase() {
        if (database == null) { ;
            database = getCookieValue(RepositoryAttributeNames.DATABASE_NAME);;
        }
        return database;
    }

    @Override
    public String getToken() {
        if (token == null) {
            token = getCookieValue(RepositoryAttributeNames.TOKEN_NAME);;
        }
        return token;
    }

    @Override
    public String getRootUrl() {
        if (rootUrl == null) {
            rootUrl = getCookieValue(RepositoryAttributeNames.ROOT_URL_NAME);;
        }
        return rootUrl;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
        setCookieValue(RepositoryAttributeNames.SITE_ID_NAME, String.valueOf(siteId));
    }

    public void setDatabase(String database) {
        this.database = database;
        setCookieValue(RepositoryAttributeNames.DATABASE_NAME, database);
    }

    public void setToken(String token) {
        this.token = token;
        setCookieValue(RepositoryAttributeNames.TOKEN_NAME, token);
    }

    public void setRootUrl(String rootUrl) {
        this.rootUrl = rootUrl;
        setCookieValue(RepositoryAttributeNames.ROOT_URL_NAME, rootUrl);
    }

    public void setupInitialValuesIfNotExists() {

        if (getSiteId() == 0) {
            setSiteId(2);
        }

        if (getDatabase().isEmpty()) {
            setDatabase("quilt_development");
        }

        if (getToken().isEmpty()) {
            setToken("c48e9f37-ac90-427a-9101-119c096593de");
        }

        if (getRootUrl().isEmpty()) {
            setRootUrl("http://www.datascopesystem.com/datatouch_dev/");
        }
    }
}
