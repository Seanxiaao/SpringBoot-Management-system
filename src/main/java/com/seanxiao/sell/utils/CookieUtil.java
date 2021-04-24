package com.seanxiao.sell.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookieUtil {

    // set cookie
    public static void set(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static Cookie get(HttpServletRequest request, String name) {
        Map<String,Cookie> map = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for(Cookie cookie :cookies) {
                map.put(cookie.getName(), cookie);
            }
        }
        return map.getOrDefault(name, null);
    }


}
