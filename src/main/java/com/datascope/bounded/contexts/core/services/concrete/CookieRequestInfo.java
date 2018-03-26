package com.datascope.bounded.contexts.core.services.concrete;

import com.datascope.bounded.contexts.core.services.RequestInfo;
import com.vaadin.server.VaadinService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import java.util.Arrays;
import java.util.Optional;

import static org.springframework.beans.factory.config.BeanDefinition.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class CookieRequestInfo implements RequestInfo {

    private static final String SITE_ID_NAME = "SiteId";
    private static final String DATABASE_NAME = "Database";
    private static final String TOKEN_NAME = "Token";
    private static final String ROOT_URL_NAME = "RootUrl";

    private Cookie getCookieByName(String name) {
        Optional<Cookie> res = Arrays.stream(VaadinService.getCurrentRequest()
                .getCookies())
                .filter(i -> name.equals(i.getName()))
                .findFirst();

        return res.orElse(null);
    }

    @Override
    public String toString() {
        return String.format("[SiteId:%s\nDatabase:%s\nToken:%s]", getSiteId(), getDatabase(), getToken());
    }

    @Override
    public int getSiteId() {
        return Integer.parseInt(getCookieByName(SITE_ID_NAME).getValue());
    }

    @Override
    public String getDatabase() {
        return getCookieByName(DATABASE_NAME).getValue();
    }

    @Override
    public String getToken() {
        return getCookieByName(TOKEN_NAME).getValue();
    }

    @Override
    public String getRootUrl() {
        return getCookieByName(ROOT_URL_NAME).getValue();
    }

    public static void setInfo() {
        Cookie siteId = new Cookie(SITE_ID_NAME, "2");
        Cookie database = new Cookie(DATABASE_NAME, "quilt_development");
        Cookie token = new Cookie(TOKEN_NAME, "c48e9f37-ac90-427a-9101-119c096593de");
        Cookie rootUrl = new Cookie(ROOT_URL_NAME, "http://www.datascopesystem.com/datatouch_dev/");

        String path = VaadinService.getCurrentRequest().getContextPath();

        siteId.setPath(path);
        database.setPath(path);
        token.setPath(path);
        rootUrl.setPath(path);

        VaadinService.getCurrentResponse().addCookie(siteId);
        VaadinService.getCurrentResponse().addCookie(database);
        VaadinService.getCurrentResponse().addCookie(token);
        VaadinService.getCurrentResponse().addCookie(rootUrl);
    }
}
