package by.tms.socialnetworkc41onl.servlet;

import by.tms.socialnetworkc41onl.model.User;
import by.tms.socialnetworkc41onl.service.PostService;
import by.tms.socialnetworkc41onl.service.ServiceException;
import by.tms.socialnetworkc41onl.service.SessionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/posts")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024, maxRequestSize = 25 * 1024 * 1024)
public class NewPostServlet extends HttpServlet {

    private final PostService postService = new PostService();
    private final SessionService sessionService = new SessionService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/new-post.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User current = sessionService.currentUser(request).orElseThrow();
        String text = request.getParameter("text");

        List<PostService.PhotoData> photos = new ArrayList<>();
        for (Part part : request.getParts()) {
            if ("photos".equals(part.getName()) && part.getSize() > 0) {
                try (InputStream in = part.getInputStream()) {
                    String name = part.getSubmittedFileName();
                    photos.add(new PostService.PhotoData(
                            name == null || name.isBlank() ? "photo" : name,
                            in.readAllBytes()));
                }
            }
        }
        try {
            postService.create(current.getId(), text, photos);
            response.sendRedirect(request.getContextPath() + "/feed");
        } catch (ServiceException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/new-post.jsp").forward(request, response);
        }
    }
}
