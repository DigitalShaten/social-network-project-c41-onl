/**
 * Classname    ProfileServlet
 * @version     0.01
 * @author      Aleksei Borzetsov
 * date         25.07.2026
 */

package by.tms.socialnetworkc41onl.servlet;

import by.tms.socialnetworkc41onl.dao.SubscriptionDao;
import by.tms.socialnetworkc41onl.dto.ProfileDto;
import by.tms.socialnetworkc41onl.model.Subscription;
import by.tms.socialnetworkc41onl.service.ProfileService;
import by.tms.socialnetworkc41onl.service.SessionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqUserId = req.getParameter("userId");
        long userID;
        ProfileDto profileDto = new ProfileDto();
        ProfileService profileService = new ProfileService();

        /*Определить userID*/
        if (reqUserId == null) {
            /*Текущий пользователь*/
            userID = SessionService.getUser(req.getSession());
            req.setAttribute("currentUser", true);
        }
        else {
            /*Конкретный пользователь*/
            userID = Long.parseLong(reqUserId);
            if (userID == SessionService.getUser(req.getSession())) req.setAttribute("currentUser", true);
            else req.setAttribute("currentUser", false);
        }
        /*Запрос DTO*/
        try {
            profileDto = profileService.fetchProfileData(userID, req);
        } catch (SQLException e) {
            getServletContext().getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(req, resp);
            return;
        }

        /*Объект DTO получен*/
        req.setAttribute("profileDto", profileDto);
        getServletContext().getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Получить параметры запроса*/
        String reqSubscriptionId = req.getParameter("userId");  /*ID пользователя*/
        String reqSubscribe = req.getParameter("subscribe");  /*Подписаться (true) или отписаться (false)*/

        if (reqSubscribe == null) {
            /*Ошибка запроса*/
            getServletContext().getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(req, resp);
            return;
        }

        if (reqSubscriptionId == null) {
            /*Ошибка запроса -- на себя подписаться нельзя*/
            getServletContext().getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(req, resp);
            return;
        }

        long subscriptionID = Long.parseLong(reqSubscriptionId);

        Subscription subscription = new Subscription();
        SubscriptionDao subscriptionDao = new SubscriptionDao();

        if (reqSubscribe.equals("true")) {
            /*Подписаться*/
            subscription.setUserId(subscriptionID);
            subscription.setSubscriptionUserId(SessionService.getUser(req.getSession()));
            try {
                subscriptionDao.save(subscription);
            }
            catch (Exception e) {
                getServletContext().getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(req, resp);
                return;
            }
            resp.sendRedirect("profile?userId=" + subscriptionID);
        }
        else if (reqSubscribe.equals("false")) {
            /*Отписаться*/
            try {
                subscriptionDao.delete(subscriptionID, SessionService.getUser(req.getSession()));
            }
            catch (Exception e) {
                getServletContext().getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(req, resp);
                return;
            }
            resp.sendRedirect("profile?userId=" + subscriptionID);
        }
        else {
            /*Ошибка аргумента*/
            getServletContext().getRequestDispatcher("/WEB-INF/views/error/404.jsp").forward(req, resp);
        }
    }
}
