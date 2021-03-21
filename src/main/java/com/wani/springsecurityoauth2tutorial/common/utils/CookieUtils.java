package com.wani.springsecurityoauth2tutorial.common.utils;

import io.jsonwebtoken.lang.Assert;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CookieUtils {

    public static Cookie get(HttpServletRequest request, String name) {
        Assert.notNull(name, "name to get cookie be null");

        List<Cookie> cookies = List.of(request.getCookies());

        if (!cookies.isEmpty()) {
            return cookies.stream()
                    .filter(it -> name.equals(it.getName()))
                    .findFirst()
                    .orElse(null);
        }

        return null;
    }


    public static void put(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void delete(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie cookie = get(request, name);

        if (cookie != null) {
            cookie.setValue("");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

}
