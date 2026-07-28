package by.tms.socialnetworkc41onl.servlet;

import by.tms.socialnetworkc41onl.service.SessionService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    private final SessionService sessionService = new SessionService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        SessionService.logout(req, resp);
        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
