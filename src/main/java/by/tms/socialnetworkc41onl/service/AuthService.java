package by.tms.socialnetworkc41onl.service;

import by.tms.socialnetworkc41onl.dao.UserDao;
import by.tms.socialnetworkc41onl.model.User;
import javax.naming.AuthenticationException;
import java.util.Optional;

/**
 * Created by Yushko Aliaksei on 28.07.2026
 */
public class AuthService {
    private final UserDao userDao;

    public AuthService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User authenticate(String email, String password) throws AuthenticationException {
        // 1. Ищем пользователя по email
        Optional<User> userOptional = userDao.findByEmail(email);

        // Если пользователя нет, выбрасываем общую ошибку
        if (userOptional.isEmpty()) {
            throw new AuthenticationException("Incorrect email or password!");
        }

        User user = userOptional.get();

        // 2. Сверяем хэш пароля
        if (!user.getPasswordHash().equals(password)) {
            throw new AuthenticationException("Incorrect email or password!!");
        }

        // 3. Критерий: Неподтверждённый аккаунт (status=false) -> вход отклонён
        if (!user.isStatus()) {
            throw new AuthenticationException("Incorrect email or password!!!");
        }

        // Все проверки пройдены — возвращаем пользователя сервлет
        return user;
    }
}
