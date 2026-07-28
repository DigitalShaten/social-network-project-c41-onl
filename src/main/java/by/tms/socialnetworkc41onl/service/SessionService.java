package by.tms.socialnetworkc41onl.service;

import by.tms.socialnetworkc41onl.dao.UserDao;
import by.tms.socialnetworkc41onl.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

public class SessionService {

    private static final String CURRENT_USER = "currentUser";
    private static final String COOKIE_NAME = "userIdCookie";

    private static final UserDao userDao = new UserDao();

    public static void setUser(HttpSession session, User user) {
        session.setAttribute(CURRENT_USER, user);
    }

    public static Long getUser(HttpSession session) {
        if (session == null) {
            return null;
        }
        Object user = session.getAttribute(CURRENT_USER);
        return user instanceof User ? ((User) user).getId() : null;
    }

    /** Текущий пользователь из сессии. */
    public Optional<User> currentUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return Optional.empty();
        }
        Object user = session.getAttribute(CURRENT_USER);
        return user instanceof User ? Optional.of((User) user) : Optional.empty();
    }

    public static void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        Cookie cookie = new Cookie(COOKIE_NAME, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    /** «Запомнить меня»: восстанавливает пользователя из cookie в сессию (грузит объект из БД). */
    public static void checkMeOut(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(CURRENT_USER) != null) {
            return; // уже в сессии
        }
        if (request.getCookies() == null) {
            return;
        }
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals(COOKIE_NAME)) {
                try {
                    long userId = Long.parseLong(cookie.getValue());
                    userDao.findById(userId).ifPresent(user ->
                            request.getSession().setAttribute(CURRENT_USER, user));
                } catch (NumberFormatException ignored) {
                    // битая cookie — игнорируем
                }
                return;
            }
        }
    }

    public static void rememberMe(HttpServletResponse response, Long userId) {
        Cookie cookie = new Cookie(COOKIE_NAME, userId.toString());
        cookie.setMaxAge(60 * 60 * 24 * 30); // 30 дней
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
}
