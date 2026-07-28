package by.tms.socialnetworkc41onl.servlet;

import by.tms.socialnetworkc41onl.model.User;
import by.tms.socialnetworkc41onl.service.SessionService;
import by.tms.socialnetworkc41onl.service.SubscriptionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/subscription")
public class SubscriptionServlet extends HttpServlet {

    private final SubscriptionService subscriptionService = new SubscriptionService();
    private final SessionService sessionService = new SessionService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User current = sessionService.currentUser(request).orElseThrow();
        try {
            long targetUserId = Long.parseLong(request.getParameter("targetUserId"));
            String action = request.getParameter("action");
            if ("unsubscribe".equals(action)) {
                subscriptionService.unsubscribe(current.getId(), targetUserId);
            } else {
                subscriptionService.subscribe(current.getId(), targetUserId);
            }
        } catch (NumberFormatException ignored) {
            // игнорим
        }
        String referer = request.getHeader("Referer");
        response.sendRedirect(referer != null ? referer : request.getContextPath() + "/feed");
    }
}
