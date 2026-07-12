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
            return BCrypt.checkpw(password, hashed) ? "OK" : "Неверный пароль / Incorrect password";
        } catch (RuntimeException e) {
            return "Неверный пароль / Incorrect password";
        }
    }
}