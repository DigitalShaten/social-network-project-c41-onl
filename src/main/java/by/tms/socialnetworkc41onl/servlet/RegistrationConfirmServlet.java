package by.tms.socialnetworkc41onl.servlet;

import by.tms.socialnetworkc41onl.service.RegistrationService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.UUID;

@WebServlet("/registration/confirm")
public class RegistrationConfirmServlet extends HttpServlet {

    private final RegistrationService registrationService = new RegistrationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String tokenUuidParam = req.getParameter("token");
       if(tokenUuidParam.isEmpty()){
           resp.sendRedirect("/WEB-INF/views/error/404.jsp");
       }
        UUID tokenUuid = UUID.fromString(tokenUuidParam);

        boolean isUserActive = registrationService.activateUser(tokenUuid);
        if(isUserActive){
            req.setAttribute("welcomeMessage", "Регистрация прошла успешно!");
            req.getRequestDispatcher("/WEB-INF/views/common/login.jsp").forward(req, resp);
        }
    }

}
