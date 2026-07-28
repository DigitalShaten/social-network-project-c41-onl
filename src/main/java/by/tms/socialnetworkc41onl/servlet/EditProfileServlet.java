package by.tms.socialnetworkc41onl.servlet;

import by.tms.socialnetworkc41onl.constant.Gender;
import by.tms.socialnetworkc41onl.constant.Status;
import by.tms.socialnetworkc41onl.model.FileData;
import by.tms.socialnetworkc41onl.model.dto.EditProfileDto;
import by.tms.socialnetworkc41onl.model.dto.UserProfileDto;
import by.tms.socialnetworkc41onl.service.ProfileUserService;
import by.tms.socialnetworkc41onl.service.SessionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.time.LocalDate;

/**
 * @author Ирина Мизгир
 * @date 25.07.2026 20:21
 */
@WebServlet("/profile/edit")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,  // 2 МБ (после этого пишется на диск)
        maxFileSize = 1024 * 1024 * 10,       // 10 МБ (макс. размер одного файла)
        maxRequestSize = 1024 * 1024 * 50     // 50 МБ (макс. размер всего запроса)
)
public class EditProfileServlet extends HttpServlet {

    private final ProfileUserService profileUserService = new ProfileUserService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Long userId = SessionService.getUser(req.getSession());
        if (userId == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        redirectToEditProfile(req, resp, userId);
    }

    private void redirectToEditProfile(HttpServletRequest req, HttpServletResponse resp, Long userId) throws ServletException, IOException {
        UserProfileDto userProfileDto = profileUserService.getUserProfileDtoByUserId(userId);

        req.setAttribute("userId", userProfileDto.userId());
        req.setAttribute("firstName", userProfileDto.firstName());
        req.setAttribute("lastName", userProfileDto.lastName());
        req.setAttribute("gender", userProfileDto.gender());
        req.setAttribute("birthday", userProfileDto.birthday());
        req.setAttribute("about", userProfileDto.about());
        req.setAttribute("currentFileId", userProfileDto.currentFileId());
        req.getRequestDispatcher("/WEB-INF/views/profile/edit-profile.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Long userId = SessionService.getUser(req.getSession());
        if (userId == null) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }
        String firstName = req.getParameter("first-name");
        String lastName = req.getParameter("last-name");
        Gender gender = Gender.valueOf(req.getParameter("gender"));
        
        
        if (StringUtils.isAnyBlank(firstName, lastName)) {
            req.setAttribute("status", Status.ERROR);
            req.setAttribute("message", "Enter required first and last name");
            redirectToEditProfile(req, resp, userId);
            return;
        }
        LocalDate birthday = LocalDate.parse(req.getParameter("date-of-birth"));
        String about = req.getParameter("about");
        Part filePart = req.getPart("user-photo");        
        FileData userPhoto = getPhotoOrNull(filePart);
        var editProfileDto = new EditProfileDto(userId,firstName,lastName,gender,birthday,about,userPhoto);
        profileUserService.editProfile(editProfileDto);

        req.setAttribute("status", Status.SUCCESS);
        req.setAttribute("message", "Changes saved successfully");
        redirectToEditProfile(req, resp, userId);
    }

    private FileData getPhotoOrNull(Part filePart) {
        if (filePart == null || filePart.getSize() == 0) {
            return null;
        }
        String fileName = filePart.getSubmittedFileName();
        if (StringUtils.isEmpty(fileName)) {
            return null;
        }

        try(InputStream inputStream = filePart.getInputStream()) {
            return new FileData(fileName, inputStream.readAllBytes());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
