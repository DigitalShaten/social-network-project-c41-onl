package by.tms.socialnetworkc41onl.servlet;

import by.tms.socialnetworkc41onl.dao.UserDao;
import by.tms.socialnetworkc41onl.model.User;
import by.tms.socialnetworkc41onl.service.AuthService;
import by.tms.socialnetworkc41onl.service.SessionService;
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
        if (SessionService.getUser(req.getSession()) != null) {
            resp.sendRedirect(req.getContextPath() + "/feed");
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
            SessionService.setUser(session, user);

            // "Запомнить меня" — cookie с id, как ждёт AuthFilter/SessionService
            if ("true".equals(rememberMe) || "on".equals(rememberMe)) {
                SessionService.rememberMe(resp, user.getId());
            }

            resp.sendRedirect(req.getContextPath() + "/feed");

        } catch (AuthenticationException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/views/authentication/login.jsp").forward(req, resp);
        }
    }
}