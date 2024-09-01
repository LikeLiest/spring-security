package ru.zed.app.controller.session;

import jakarta.servlet.http.Cookie;
import lombok.Data;

@Data
public class CookieWrapper {
    private String name;
    private String value;
    private int maxAge;
    private boolean httpOnly;
    private String path;

    public CookieWrapper(Cookie cookie) {
        this.name = cookie.getName();
        this.value = cookie.getValue();
        this.maxAge = cookie.getMaxAge();
        this.httpOnly = cookie.isHttpOnly();
        this.path = cookie.getPath();
    }
}
