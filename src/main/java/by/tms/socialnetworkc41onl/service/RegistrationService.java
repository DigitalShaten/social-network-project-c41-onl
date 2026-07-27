package by.tms.socialnetworkc41onl.service;

import by.tms.socialnetworkc41onl.dao.TokenDao;
import by.tms.socialnetworkc41onl.dao.UserDao;
import by.tms.socialnetworkc41onl.model.Token;
import by.tms.socialnetworkc41onl.model.User;
import by.tms.socialnetworkc41onl.util.PasswordUtil;
import by.tms.socialnetworkc41onl.util.PasswordValidator;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationService {

    private final UserDao userDao = new UserDao();
    private final TokenDao tokenDao = new TokenDao();
    private final PasswordValidator passwordValidator = new PasswordValidator();

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

    public boolean isUserNameUnique(String username){
        return userDao.findByUsername(username).isEmpty();
    }

    public boolean isEmailUnique(String email){
        return userDao.findByEmail(email).isEmpty();
    }
    public boolean isEmailValid(String email){
        if(email == null){
            return false;
        }
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isPasswordValid(String password){
        return passwordValidator.isValid(password);
    }

    public String hashPassword(String password){
        return PasswordUtil.hash(password);
    }

    public Token createToken(User user){
        UUID uuidToken = UUID.randomUUID();
        Token token = new Token();
        token.setId(uuidToken);
        token.setType("registration");
        token.setActive(true);
        token.setUserId(user.getId());
        token.setCreatedDate(LocalDateTime.now());
        tokenDao.save(token);
        return token;
    }

    public User createUser(User user, String password){
        user.setPasswordHash(hashPassword(password));
        user.setStatus(false);
        return userDao.save(user);
    }

    public Token register(User user, String password){
        User savedUser = createUser(user, password);
        return createToken(savedUser);
    }

    public boolean activateUser(UUID tokenId){
        Optional<Token> tokenOptional = tokenDao.findById(tokenId);
        if(tokenOptional.isEmpty() || !tokenOptional.get().isActive()){
            return false;
        }
        Token token = tokenOptional.get();
        Optional<User> userOptional = userDao.findById(token.getUserId());
        if(userOptional.isEmpty()){
            return false;
        }
        User user = userOptional.get();
        user.setStatus(true);
        userDao.update(user);
        tokenDao.deactivate(tokenId);
        return true;
    }
}
