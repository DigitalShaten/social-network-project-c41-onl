package util;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    private PasswordUtil() {
    }

    /**
     * Хэширует исходный пароль с использованием алгоритма BCrypt.
     */
    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Проверяет, соответствует ли введенный пароль сохраненному хэшу.
     */
    public static String verify(String password, String hashed) {
        try {
            boolean isCorrect = BCrypt.checkpw(password, hashed);
            if (isCorrect) {
                return "OK";
            } else {
                return "Неверный пароль / Incorrect password";
            }
        } catch (IllegalArgumentException | NullPointerException e) {
            return "Неверный пароль / Incorrect password";
        }
    }
}