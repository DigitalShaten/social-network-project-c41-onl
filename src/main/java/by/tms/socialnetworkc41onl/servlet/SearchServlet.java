package by.tms.socialnetworkc41onl.servlet;

import by.tms.socialnetworkc41onl.model.UserCardView;
import by.tms.socialnetworkc41onl.service.SessionService;
import by.tms.socialnetworkc41onl.service.UserSearchService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * @author Ирина Мизгир
 * @date 26.07.2026 18:08
 */
@WebServlet("/users")
public class SearchServlet extends HttpServlet {

    private final UserSearchService userSearchService = new UserSearchService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Long userId = SessionService.getUser(req.getSession());
        if (userId == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        String query = req.getParameter("q");
        req.setAttribute("users", getUserCardViews(query, userId));
        req.getRequestDispatcher("/WEB-INF/views/search/search.jsp").forward(req, resp);
    }

    private List<UserCardView> getUserCardViews(String query, long userId) {
        return StringUtils.isBlank(query)
                ? userSearchService.getAllUsersWithExclude(userId)
                : userSearchService.getUsersWithExclude(query, userId);

    }
}
