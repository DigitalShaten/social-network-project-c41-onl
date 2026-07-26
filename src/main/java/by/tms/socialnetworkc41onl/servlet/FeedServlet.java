package by.tms.socialnetworkc41onl.servlet;

import by.tms.socialnetworkc41onl.model.User;
import by.tms.socialnetworkc41onl.service.FeedService;
import by.tms.socialnetworkc41onl.service.SessionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/feed")
public class FeedServlet extends HttpServlet {

    private final FeedService feedService = new FeedService();
    private final SessionService sessionService = new SessionService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User current = sessionService.currentUser(request).orElseThrow();
        request.setAttribute("posts", feedService.feed(current.getId()));
        request.getRequestDispatcher("/WEB-INF/views/feed.jsp").forward(request, response);
    }
}
