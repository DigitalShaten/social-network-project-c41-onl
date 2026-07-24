package by.tms.socialnetworkc41onl.service;

import by.tms.socialnetworkc41onl.dao.PostDao;

import java.util.List;

/** Лента: все посты, новые сверху, с автором, фото, реакциями и комментариями. */
public class FeedService {

    private final PostDao postDao = new PostDao();

    //TODO расскоментить, как появиться метод
    //private final PostViewAssembler assembler = new PostViewAssembler();

    //public List<PostView> feed(long viewerId) {
    //return assembler.assemble(postDao.findFeed(), viewerId);}
}
