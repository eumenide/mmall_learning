package com.mmall.util;

import com.mmall.common.Const;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: eumes
 * @date: 2019/12/6
 **/

@Slf4j
public class CookieUtil {

    private final static String COOKIE_DOMAIN = ".happymmall.com";
    private final static String COOKIE_NAME = "mmall_login_token";

    public static String readLoginToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie :
                    cookies) {
                    log.info("read cookieName:{}, cookieValue:{}", cookie.getName(), cookie.getValue());
                if (StringUtils.equals(cookie.getName(), COOKIE_NAME)) {
                    log.info("return cookieName:{}, cookieValue:{}", cookie.getName(), cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public static void writeLoginToken(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(COOKIE_NAME, token);
        cookie.setDomain(COOKIE_DOMAIN);
        // 代表设置在根目录
        cookie.setPath("/");
        // 一定程度防止通过脚本攻击带来的泄露
        cookie.setHttpOnly(true);
        // 单位为秒；如果是-1，表示永久
        // 如果maxAge不设置，cookie就不会写入硬盘，而是写在内存。只在当前页面有效。
        cookie.setMaxAge(Const.ONE_YEAR_SECONDS);
        response.addCookie(cookie);

        log.info("write cookieName:{}, cookieValue:{}", cookie.getName(), cookie.getValue());
    }

    public static void delLoginToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie:
                 cookies) {
                if (StringUtils.equals(cookie.getName(), COOKIE_NAME)) {
//                    cookie.setDomain(COOKIE_DOMAIN);
//                    cookie.setPath("/");
                    // 将maxAge设置为0，表示删除
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);

                    log.info("delete cookieName:{}, cookieValue:{}", cookie.getName(), cookie.getValue());
                    return;
                }
            }
        }
    }

}
