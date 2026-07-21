package by.tms.socialnetworkc41onl.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SessionService {

    private static final String CURRENT_USER_ID_NAME = "userId";
    private static final String COOKIE_NAME = "userIdCookie";

    public static void setUser(HttpSession session, Long userId) {
        session.setAttribute(CURRENT_USER_ID_NAME, userId);

    }

    public static Long getUser(HttpSession session) {
        return session != null ? (Long) session.getAttribute(CURRENT_USER_ID_NAME) : null;
    }

    public static void logout(HttpServletRequest request, HttpServletResponse response) {

        //Берем текущую сессию, если её нет, НЕ создаем новую
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); //удаляем сессию
        }

        //Удаляем cookie c именем userIdCookie
        Cookie cookie = new Cookie(COOKIE_NAME, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

    }

    public static void checkMeOut(HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(CURRENT_USER_ID_NAME) != null) {
            return; //Пользователь уже зарегистрирован
        }

        // Cookies были удалены
        if (request.getCookies() == null) return;

        // Передаем значение id из Cookie в существующую сессию
        // или если session = null, то создаем НОВУЮ сессию и передаем значение id из Cookie
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(COOKIE_NAME)) {
                Long userId = Long.valueOf(cookie.getValue());
                request.getSession().setAttribute(CURRENT_USER_ID_NAME, userId);
                return;
            }
        }
    }

    public void rememberMe(HttpServletResponse response, Long userId) {
        //Записываем id текущего пользователя в Cookie браузера
        Cookie cookie = new Cookie(COOKIE_NAME, userId.toString());
        cookie.setMaxAge(60 * 60 * 24 * 30); //Сохраним на 30 дней
        cookie.setPath("/"); //отправляем нашу Cookie с каждым запросом
        response.addCookie(cookie);
    }
}