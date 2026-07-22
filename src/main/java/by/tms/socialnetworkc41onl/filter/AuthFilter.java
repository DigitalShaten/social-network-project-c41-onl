/*
package by.tms.socialnetworkc41onl.filter;

import by.tms.socialnetworkc41onl.service.SessionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;

@WebFilter("/*")
public class AuthFilter extends HttpFilter {

    Set<String> publicUrls = Set.of(
            "/login",
            "/logout",
            "/registration",
            "/registration/confirm",
            "/recovery",
            "/recovery/reset",
            "/files/",
            "/resources/",
            "/error/"

    );

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {

        SessionService.checkMeOut(req); // пытаемся восстановить пользователя по Cookie

        // Берём актуальную сессию (она могла появиться в checkMeOut), если её нет – создаем
        HttpSession session = req.getSession();

        Long userId = SessionService.getUser(session);

        String path = req.getServletPath();

        //Проверяем начинается ли наш путь запроса со списком разрешенных url
        boolean isPublic = publicUrls.stream()
                .anyMatch(path::startsWith);

        // Пропускаем, если пути публичные
        if (isPublic) {
            chain.doFilter(req, res);
            return;
        }

        //Если пользователь не зарегистрирован – перенаправляем на регистрацию
        if (userId == null) {
            res.sendRedirect("/login");
            return;
        }

        // Все проверки прошли – пропускаем запрос
        chain.doFilter(req, res);

    }
}
*/

