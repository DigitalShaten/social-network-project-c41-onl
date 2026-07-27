package by.tms.socialnetworkc41onl.servlet;

import by.tms.socialnetworkc41onl.model.ReactionType;
import by.tms.socialnetworkc41onl.model.User;
import by.tms.socialnetworkc41onl.service.ReactionService;
import by.tms.socialnetworkc41onl.service.SessionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/reaction")
public class ReactionServlet extends HttpServlet {

    private final ReactionService reactionService = new ReactionService();
    private final SessionService sessionService = new SessionService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User current = sessionService.currentUser(request).orElseThrow();
        try {
            long postId = Long.parseLong(request.getParameter("postId"));
            ReactionType type = ReactionType.valueOf(request.getParameter("type"));
            reactionService.react(current.getId(), postId, type);
        } catch (IllegalArgumentException ignored) {
            // игнорим
        }
        redirectBack(request, response);
    }

    private void redirectBack(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String referer = request.getHeader("Referer");
        response.sendRedirect(referer != null ? referer : request.getContextPath() + "/feed");
    }
}
