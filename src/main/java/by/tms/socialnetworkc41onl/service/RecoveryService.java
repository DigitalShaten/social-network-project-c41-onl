package by.tms.socialnetworkc41onl.service;

import by.tms.socialnetworkc41onl.dao.TokenDao;
import by.tms.socialnetworkc41onl.dao.UserDao;
import by.tms.socialnetworkc41onl.model.Token;
import by.tms.socialnetworkc41onl.model.User;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Yushko Aliaksei on 28.07.2026
 */
public class RecoveryService {
    private final UserDao userDao;
    private final TokenDao tokenDao;

    private static final long TOKEN_EXPIRY_MINUTES = 15;

    public RecoveryService(UserDao userDao, TokenDao tokenDao) {
        this.userDao = userDao;
        this.tokenDao = tokenDao;
    }

    public String createRecoveryLink(String email, String baseUrl) {
        Optional<User> userOptional = userDao.findByEmail(email);

        if (userOptional.isEmpty()) {
            return null;
        }

        User user = userOptional.get();
        UUID tokenId = UUID.randomUUID();

        Token token = new Token();
        token.setId(tokenId);
        token.setType("RESET_PASSWORD");
        token.setActive(true);
        token.setUserId(user.getId());
        token.setCreatedDate(LocalDateTime.now());

        tokenDao.save(token);

        return baseUrl + "/recovery/reset?token=" + tokenId;
    }

    public boolean validateToken(String tokenStr) {
        if (tokenStr == null || tokenStr.trim().isEmpty()) {
            return false;
        }
        try {
            UUID tokenId = UUID.fromString(tokenStr);
            Optional<Token> tokenOptional = tokenDao.findById(tokenId);

            if (tokenOptional.isEmpty()) {
                return false;
            }

            Token token = tokenOptional.get();

            if (!token.isActive()) {
                return false;
            }

            LocalDateTime expiryTime = token.getCreatedDate().plusMinutes(TOKEN_EXPIRY_MINUTES);
            return LocalDateTime.now().isBefore(expiryTime);

        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean resetPassword(String tokenStr, String newPassword) {
        if (!validateToken(tokenStr)) {
            return false;
        }

        // Правило валидации пароля (не пустой и длина от 8 символов)
        if (newPassword == null || newPassword.trim().length() < 8) {
            return false;
        }

        UUID tokenId = UUID.fromString(tokenStr);
        Token token = tokenDao.findById(tokenId).get();

        Optional<User> userOptional = userDao.findById(token.getUserId());
        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();

        // Обновляем хэш пароля
        user.setPasswordHash(newPassword);

        userDao.update(user);
        tokenDao.deactivate(tokenId);
        return true;
    }

}
