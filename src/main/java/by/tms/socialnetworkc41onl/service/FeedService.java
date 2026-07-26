package by.tms.socialnetworkc41onl.service;

import by.tms.socialnetworkc41onl.dao.PostDao;
import by.tms.socialnetworkc41onl.dto.PostDTO;

import java.util.List;

/** Лента: все посты, новые сверху, с автором, фото, реакциями и комментариями. */
public class FeedService {

    private final PostDao postDao = new PostDao();
    private final PostDTOAssembler assembler = new PostDTOAssembler();

    public List<PostDTO> feed(long viewerId) {
        return assembler.assemble(postDao.findFeed(), viewerId);
    }
}
