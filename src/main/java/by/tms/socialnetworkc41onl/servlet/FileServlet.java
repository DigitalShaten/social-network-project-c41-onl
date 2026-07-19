package by.tms.socialnetworkc41onl.servlet;

import by.tms.socialnetworkc41onl.dao.FileDao;
import by.tms.socialnetworkc41onl.model.File;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

/** Отдаёт картинку из БД по id*/
@WebServlet("/files/*")
public class FileServlet extends HttpServlet {

    private final FileDao fileDao = new FileDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        long id;

        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.length() < 2) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            id = Long.parseLong(pathInfo.substring(1));
        } catch (NumberFormatException error) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Optional<File> file = fileDao.findById(id);
        if (file.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        File found = file.get();
        response.setContentType(guessContentType(found.getFileName()));
        byte[] data = found.getData();

        if (data != null) {
            response.setContentLength(data.length);
            try (OutputStream out = response.getOutputStream()) {
                out.write(data);
            }
        }
    }

    private String guessContentType(String fileName) {

        if (fileName == null) {
            return "application/octet-stream";
        }

        String lower = fileName.toLowerCase();

        if (lower.endsWith(".png")) return "image/png";
        if (lower.endsWith(".gif")) return "image/gif";
        if (lower.endsWith(".webp")) return "image/webp";
        if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) return "image/jpeg";

        return "application/octet-stream";
    }
}
