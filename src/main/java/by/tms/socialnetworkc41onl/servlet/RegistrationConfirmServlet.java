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
        if (tokenUuidParam == null || tokenUuidParam.isBlank()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        try {
            UUID tokenUuid = UUID.fromString(tokenUuidParam);
            boolean isUserActive = registrationService.activateUser(tokenUuid);
            if (isUserActive) {
                req.setAttribute("message", "Регистрация прошла успешно! Теперь можно войти.");
            } else {
                req.setAttribute("error", "Ссылка недействительна или уже использована.");
            }
        } catch (IllegalArgumentException e) {
            req.setAttribute("error", "Ссылка недействительна.");
        }
        req.getRequestDispatcher("/WEB-INF/views/authentication/login.jsp").forward(req, resp);
    }

}
