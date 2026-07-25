/**
 * Classname    ProfileService
 * @version     0.01
 * @author      Aleksei Borzetsov
 * date         25.07.2026
 */

package by.tms.socialnetworkc41onl.service;

import by.tms.socialnetworkc41onl.dao.PostDao;
import by.tms.socialnetworkc41onl.dao.SubscriptionDao;
import by.tms.socialnetworkc41onl.dao.UserDao;
import by.tms.socialnetworkc41onl.dto.ProfileDto;
import by.tms.socialnetworkc41onl.model.User;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Сервис для получения данных профиля
 */
public class ProfileService {

    /**
     * Возвращает объект с данными, необходимыми для страницы профиля
     * @param userId ID пользователя
     * @return ProfileDto - объект с данными для профиля
     * @throws SQLException ошибка, если пользователя с указанным ID не существует
     */
    public ProfileDto fetchProfileData(long userId, HttpServletRequest req) throws SQLException {
        /*Получить объект пользователя из БД*/
        UserDao userDao = new UserDao();
        Optional<User> user = userDao.findById(userId);
        /*Если пользователь существует*/
        if (user.isPresent()) {
            ProfileDto userData = new ProfileDto();
            /*Заполнить поля DTO*/
            userData.setUserName(user.get().getUserName());
            userData.setFirstName(user.get().getFirstName());
            userData.setLastName(user.get().getLastName());
            userData.setBirthDay(user.get().getBirthday());
            userData.setGender(user.get().getGender());
            userData.setAbout(user.get().getAbout());

            /*Получить фото пользователя*/
            /*ДОПИСАТЬ*/

            /*Получить количество постов текущего пользователя*/
            PostDao postDao = new PostDao();
            userData.setPostsCounter(postDao.findByAuthor(userId).size());

            /*Получить количество подписок*/
            SubscriptionDao subscriptionDao = new SubscriptionDao();
            userData.setSubscriptionsCounter(subscriptionDao.findSubscriptionUserIds(userId).size());
            /*Получить количество подписчиков*/
            userData.setFollowersCounter(subscriptionDao.findFollowersUserIds(userId).size());
            /*Определить, подписан ли пользователь сессии на текущего пользователя*/
            userData.setSubscribed(subscriptionDao.exists(userId, SessionService.getUser(req.getSession())));

            return userData;
        }
        /*Если пользователя с указанным ID не существует*/
        else throw new SQLException("Пользователь не найден");
    }
}
