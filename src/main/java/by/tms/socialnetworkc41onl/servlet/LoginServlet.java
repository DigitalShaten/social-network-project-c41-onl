package by.tms.socialnetworkc41onl.servlet;

import by.tms.socialnetworkc41onl.dao.UserDao;
import by.tms.socialnetworkc41onl.model.User;
import by.tms.socialnetworkc41onl.service.AuthService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import javax.naming.AuthenticationException;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private AuthService authService;

    @Override
    public void init() throws ServletException {
        UserDao userDao = new UserDao();
        this.authService = new AuthService(userDao);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("currentUser") != null) {
            resp.sendRedirect(req.getContextPath() + "/hello-servlet");
            return;
        }
        req.getRequestDispatcher("/WEB-INF/views/authentication/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String rememberMe = req.getParameter("rememberMe");

        try {
            // Бизнес-проверка через AuthService (проверяет email, пароль, status)
            User user = authService.authenticate(email, password);

            HttpSession session = req.getSession();
            session.setAttribute("currentUser", user);

            // "Запомнить меня"
            if ("true".equals(rememberMe)) {
                Cookie rememberCookie = new Cookie("remember_me", email);
                rememberCookie.setMaxAge(60 * 60 * 24 * 30); // 30 дней
                rememberCookie.setPath(req.getContextPath());
                resp.addCookie(rememberCookie);
            }

            resp.sendRedirect(req.getContextPath() + "/hello-servlet");

        } catch (AuthenticationException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/authentication/login.jsp").forward(req, resp);
        }
    }
}