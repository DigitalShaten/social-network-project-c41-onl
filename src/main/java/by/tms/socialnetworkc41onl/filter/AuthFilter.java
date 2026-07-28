package by.tms.socialnetworkc41onl.filter;

import by.tms.socialnetworkc41onl.service.SessionService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@WebFilter("/*")
public class AuthFilter extends HttpFilter {

    private static final Set<String> PUBLIC_PATHS = Set.of(
            "/login", "/logout",
            "/registration", "/registration/confirm",
            "/recovery", "/recovery/reset",
            "/hello-servlet"
    );

    private static final Set<String> PUBLIC_PREFIXES = Set.of("/files/", "/resources/");

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        req.setCharacterEncoding("UTF-8");
        SessionService.checkMeOut(req); // попытка восстановить пользователя по cookie

        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (isPublic(path) || SessionService.getUser(req.getSession()) != null) {
            chain.doFilter(req, res);
            return;
        }
        res.sendRedirect(req.getContextPath() + "/login");
    }

    private boolean isPublic(String path) {
        if (PUBLIC_PATHS.contains(path)) {
            return true;
        }
        for (String prefix : PUBLIC_PREFIXES) {
            if (path.startsWith(prefix)) {
                return true;
            }
        }
        return path.endsWith(".css") || path.endsWith(".js")
                || path.endsWith(".png") || path.endsWith(".ico");
    }
}
