package by.tms.socialnetworkc41onl.servlet;

import by.tms.socialnetworkc41onl.dao.TokenDao;
import by.tms.socialnetworkc41onl.dao.UserDao;
import by.tms.socialnetworkc41onl.service.RecoveryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/recovery")
public class RecoveryServlet extends HttpServlet {
    private RecoveryService recoveryService;

    @Override
    public void init() throws ServletException {
        UserDao userDao = new UserDao();
        TokenDao tokenDao = new TokenDao();
        this.recoveryService = new RecoveryService(userDao, tokenDao);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/authentication/recovery-request.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String baseUrl = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
        String resetLink = recoveryService.createRecoveryLink(email, baseUrl);

        if (resetLink != null) {
            req.setAttribute("resetLink", resetLink);
        }

        req.getRequestDispatcher("/WEB-INF/views/authentication/recovery-link.jsp").forward(req, resp);
    }
}