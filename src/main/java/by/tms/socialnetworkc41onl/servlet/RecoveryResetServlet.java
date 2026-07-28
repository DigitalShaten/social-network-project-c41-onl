package by.tms.socialnetworkc41onl.servlet;

import by.tms.socialnetworkc41onl.dao.TokenDao;
import by.tms.socialnetworkc41onl.dao.UserDao;
import by.tms.socialnetworkc41onl.service.RecoveryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/recovery/reset")
public class RecoveryResetServlet extends HttpServlet {
    private RecoveryService recoveryService;

    @Override
    public void init() throws ServletException {
        UserDao userDao = new UserDao();
        TokenDao tokenDao = new TokenDao();
        this.recoveryService = new RecoveryService(userDao, tokenDao);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");

        if (!recoveryService.validateToken(token)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "The password reset link is invalid, has expired, or has already been used.");
            return;
        }

        req.setAttribute("token", token);
        req.getRequestDispatcher("/WEB-INF/views/authentication/recovery-reset.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        String password = req.getParameter("password");

        boolean isResetSuccessful = recoveryService.resetPassword(token, password);

        if (isResetSuccessful) {
            resp.sendRedirect(req.getContextPath() + "/login?resetSuccess=true");
        } else {
            req.setAttribute("error", "Password does not meet validation rules (minimum 8 characters) or link became invalid.");
            req.setAttribute("token", token); // Возвращаем токен в hidden поле jsp
            req.getRequestDispatcher("/WEB-INF/views/authentication/recovery-reset.jsp").forward(req, resp);
        }
    }
}
