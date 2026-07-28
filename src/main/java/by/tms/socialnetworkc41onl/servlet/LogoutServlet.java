package by.tms.socialnetworkc41onl.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        Cookie cookie = new Cookie("remember_me", "");
        cookie.setMaxAge(0);
        cookie.setPath(req.getContextPath());
        resp.addCookie(cookie);

        resp.sendRedirect(req.getContextPath() + "/login");
    }
}
