package by.tms.socialnetworkc41onl.servlet;

import by.tms.socialnetworkc41onl.model.User;
import by.tms.socialnetworkc41onl.service.CommentService;
import by.tms.socialnetworkc41onl.service.SessionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/comment")
public class CommentServlet extends HttpServlet {

    private final CommentService commentService = new CommentService();
    private final SessionService sessionService = new SessionService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        User current = sessionService.currentUser(request).orElseThrow();
        try {
            long postId = Long.parseLong(request.getParameter("postId"));
            commentService.add(current.getId(), postId, request.getParameter("text"));
        } catch (NumberFormatException ignored) {
            // игнорим
        }
        String referer = request.getHeader("Referer");
        response.sendRedirect(referer != null ? referer : request.getContextPath() + "/feed");
    }
}
