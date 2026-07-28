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
import by.tms.socialnetworkc41onl.dao.UserPhotoDao;
import by.tms.socialnetworkc41onl.dto.ProfileDto;
import by.tms.socialnetworkc41onl.model.Post;
import by.tms.socialnetworkc41onl.model.User;
import jakarta.servlet.http.HttpServletRequest;

import java.sql.SQLException;
import java.util.List;
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
            userData.setUserId(userId);
            userData.setUserName(user.get().getUserName());
            userData.setFirstName(user.get().getFirstName());
            userData.setLastName(user.get().getLastName());
            userData.setBirthDay(user.get().getBirthday());
            userData.setGender(user.get().getGender());
            userData.setAbout(user.get().getAbout());

            /*Аватар: текущий file_id, если есть*/
            Long avatarId = new UserPhotoDao().getCurrentPhotoFileIdOrNull(userId);
            if (avatarId != null) {
                userData.setAvatarFileId(avatarId);
            }

            /*Кто смотрит профиль (для флагов в постах и признака подписки)*/
            Long me = SessionService.getUser(req.getSession());
            long viewerId = (me != null) ? me : userId;

            /*Посты пользователя + их количество (тем же ассемблером, что и лента)*/
            PostDao postDao = new PostDao();
            List<Post> authorPosts = postDao.findByAuthor(userId);
            userData.setPostsCounter(authorPosts.size());
            userData.setPosts(new PostDTOAssembler().assemble(authorPosts, viewerId));

            SubscriptionDao subscriptionDao = new SubscriptionDao();
            /*Following — на скольких подписан пользователь*/
            userData.setSubscriptionsCounter((int) subscriptionDao.countFollowing(userId));
            /*Followers — сколько подписано на пользователя*/
            userData.setFollowersCounter((int) subscriptionDao.countFollowers(userId));
            /*Подписан ли текущий пользователь сессии на этого (me -> profile)*/
            userData.setSubscribed(me != null && subscriptionDao.exists(me, userId));

            return userData;
        }
        /*Если пользователя с указанным ID не существует*/
        else throw new SQLException("Пользователь не найден");
    }
}
