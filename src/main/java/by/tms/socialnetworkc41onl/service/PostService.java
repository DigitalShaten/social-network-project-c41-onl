package by.tms.socialnetworkc41onl.service;

import by.tms.socialnetworkc41onl.dao.FileDao;
import by.tms.socialnetworkc41onl.dao.PostDao;
import by.tms.socialnetworkc41onl.dao.PostPhotoDao;
import by.tms.socialnetworkc41onl.model.File;
import by.tms.socialnetworkc41onl.model.Post;
import by.tms.socialnetworkc41onl.model.PostPhoto;
import by.tms.socialnetworkc41onl.util.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Создание поста вместе с фотографиями одной записью.
 */
public class PostService {

    private final PostDao postDao = new PostDao();
    private final FileDao fileDao = new FileDao();
    private final PostPhotoDao postPhotoDao = new PostPhotoDao();

    public record PhotoData(String fileName, byte[] data) { }

    /**
     * Создаёт пост. Текст или хотя бы одно фото обязательны.
     * Если сохранение любого фото падает — откатываем весь пост (никаких «половинок»).
     */
    public Post create(long userId, String text, List<PhotoData> photos) {
        boolean hasText = text != null && !text.isBlank();
        boolean hasPhotos = photos != null && !photos.isEmpty();
        if (!hasText && !hasPhotos) {
            throw new ServiceException("Пост не может быть пустым.");
        }
        if (hasPhotos && photos.size() > 5) {
            throw new ServiceException("Не больше 5 фотографий на пост.");
        }

        try (Connection connection = ConnectionManager.getConnection()) {
            connection.setAutoCommit(false);
            try {
                Post post = new Post();
                post.setUserId(userId);
                post.setPostText(hasText ? text.trim() : null);
                postDao.save(connection, post);

                if (hasPhotos) {
                    for (PhotoData photo : photos) {
                        File file = new File();
                        file.setFileName(photo.fileName());
                        file.setData(photo.data());
                        fileDao.save(connection, file);

                        PostPhoto postPhoto = new PostPhoto();
                        postPhoto.setFileId(file.getId());
                        postPhoto.setPostId(post.getId());
                        postPhotoDao.save(connection, postPhoto);
                    }
                }

                connection.commit();
                return post;
            } catch (RuntimeException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка создания поста", e);
        }
    }
}
