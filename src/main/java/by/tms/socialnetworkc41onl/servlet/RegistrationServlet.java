package by.tms.socialnetworkc41onl.servlet;

import by.tms.socialnetworkc41onl.model.Token;
import by.tms.socialnetworkc41onl.model.User;
import by.tms.socialnetworkc41onl.service.RegistrationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;

@WebServlet(value = "/registration")
public class RegistrationServlet extends HttpServlet {

    private final RegistrationService registrationService = new RegistrationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/views/auth/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
           String firstName = req.getParameter("firstname");
           String lastName = req.getParameter("lastname");
           String username = req.getParameter("username");
           String gender = req.getParameter("gender");
           LocalDate birthday = LocalDate.parse(req.getParameter("birthday"));
           String about = req.getParameter("about");
           String email = req.getParameter("email");
           String password = req.getParameter("password");

           User user = new User();
           user.setFirstName(firstName);
           user.setLastName(lastName);
           user.setUserName(username);
           user.setGender(gender);
           user.setBirthday(birthday);
           user.setAbout(about);
           user.setEmail(email);

           HashMap<String, String> errors = new HashMap<>();

           if (!registrationService.isEmailUnique(email)) {
               errors.put("email", "Пользователь с такой электронной почтой уже существует.");
           }

           if (!registrationService.isEmailValid(email)){
               errors.put("email", "Невалидная запись электронной почты.");
           }

           if (!registrationService.isUserNameUnique(username)) {
               errors.put("username", "Пользователь с таким username уже существует.");
           }

           if (!registrationService.isPasswordValid(password)) {
               errors.put("password", "Невалидная запись пароля.");
           }

           if (!errors.isEmpty()) {
               req.setAttribute("user", user);
               req.setAttribute("errors", errors);
               req.getRequestDispatcher("/WEB-INF/views/auth/registration.jsp").forward(req, resp);
           } else {
               try {
                   Token token = registrationService.register(user, password);
                   req.setAttribute("tokenUuid", token.getId());
                   req.getRequestDispatcher("/WEB-INF/views/auth/registration-success.jsp").forward(req, resp);
               } catch (RuntimeException e) {
                   req.getRequestDispatcher("/WEB-INF/views/auth/registration.jsp").forward(req, resp);
               }
           }
    }
}
