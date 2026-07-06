/*Удаление таблиц*/
DROP TABLE IF EXISTS TOKENS CASCADE;
DROP TABLE IF EXISTS POST_REACTIONS CASCADE;
DROP TABLE IF EXISTS COMMENTS CASCADE;
DROP TABLE IF EXISTS POST_PHOTOS CASCADE;
DROP TABLE IF EXISTS USER_PHOTOS CASCADE;
DROP TABLE IF EXISTS SUBSCRIPTIONS CASCADE;
DROP TABLE IF EXISTS POSTS CASCADE;
DROP TABLE IF EXISTS FILES CASCADE;
DROP TABLE IF EXISTS USERS CASCADE;

/*Таблица пользователей*/
CREATE TABLE USERS
(
    /*Идентификатор -- первичный ключ*/
    ID            bigserial not null
        constraint users_pk
            primary key,
    /*Имя пользователя -- уникальное*/
    USER_NAME     varchar   not null
        constraint users_pk_2
            unique,
    /*email -- уникальное*/
    EMAIL         varchar   not null
        constraint users_pk_3
            unique,
    /*Hash-пароля*/
    PASSWORD_HASH text      not null,
    /*Статус пользователя (active or inactive)*/
    STATUS        bool      not null,
    /*Имя*/
    FIRST_NAME    text      not null,
    /*Фамилия*/
    LAST_NAME     text,
    /*Дата рождения*/
    BIRTHDAY      date,
    /*Пол*/
    GENDER        text,
    /*О себе*/
    ABOUT         text,
    /*Дата создания*/
    CREATED_DATE  timestamp not null default now()
);

comment on table users is 'Пользователи';
comment on column users.ID is 'Идентификатор';
comment on column users.USER_NAME is 'Имя пользователя';
comment on column users.EMAIL is 'Email';
comment on column users.PASSWORD_HASH is 'Hash-пароля';
comment on column users.STATUS is 'Статус пользователя (active or inactive)';
comment on column users.FIRST_NAME is 'Имя';
comment on column users.LAST_NAME is 'Фамилия';
comment on column users.BIRTHDAY is 'Дата рождения';
comment on column users.GENDER is 'Пол';
comment on column users.ABOUT is 'О себе';
comment on column users.CREATED_DATE is 'Дата создания';

/*Таблица файлов*/
create table FILES
(
    /*Идентификатор -- первичный ключ*/
    ID           bigserial not null
        constraint files_pk
            primary key,
    /*Имя файла*/
    FILE_NAME    varchar   not null,
    /*Данные файла*/
    DATA         bytea     not null,
    /*Дата создания*/
    CREATED_DATE timestamp not null default now()
);

comment on table files is 'Файлы';
comment on column files.ID is 'Идентификатор';
comment on column files.FILE_NAME is 'Имя файла';
comment on column files.DATA is 'Данные файла';
comment on column files.CREATED_DATE is 'Дата создания';

/*Таблица постов*/
create table POSTS
(
    /*Идентификатор -- первичный ключ*/
    ID           bigserial not null
        constraint posts_pk
            primary key,
    /*Текст*/
    POST_TEXT    text,
    /*ID пользователя -- внешний ключ (USERS)*/
    USER_ID      bigint    not null
        constraint posts_users_id_fk
            references USERS on delete cascade,
    /*Дата создания*/
    CREATED_DATE timestamp not null default now()
);

comment on table posts is 'Посты';
comment on column posts.ID is 'Идентификатор';
comment on column posts.POST_TEXT is 'Текст';
comment on column posts.USER_ID is 'ID пользователя';
comment on column posts.CREATED_DATE is 'Дата создания';

/*Таблица подписок*/
create table SUBSCRIPTIONS
(
    /*Идентификатор -- первичный ключ*/
    ID                   bigserial not null
        constraint subscriptions_pk
            primary key,
    /*ID пользователя -- внешний ключ (USERS)*/
    USER_ID              bigint    not null
        constraint subscriptions_users_id_fk
            references USERS on delete cascade,
    /*ID подписки -- внешний ключ (USERS)*/
    SUBSCRIPTION_USER_ID bigint    not null
        /*Подписаться можно только на пользователя*/
        constraint subscriptions_users_id_fk_2
            references USERS on delete cascade
        /*Нельзя подписаться на себя*/
        constraint subscriptions_check_name
            check (SUBSCRIPTION_USER_ID <> USER_ID),
    /*Дата создания*/
    CREATED_DATE         timestamp not null default now(),
    /* Составное ограничение уникальности: пользователь + подписка */
    constraint subscriptions_user_subscription_user_uc
        unique (USER_ID, SUBSCRIPTION_USER_ID)
);

comment on table SUBSCRIPTIONS is 'Подписки';
comment on column SUBSCRIPTIONS.ID is 'Идентификатор';
comment on column SUBSCRIPTIONS.USER_ID is 'ID пользователя';
comment on column SUBSCRIPTIONS.SUBSCRIPTION_USER_ID is 'ID подписки';
comment on column SUBSCRIPTIONS.CREATED_DATE is 'Дата создания';

/*Таблица токенов*/
create table TOKENS
(
    /*Идентификатор -- первичный ключ*/
    ID           uuid      not null
        constraint tokens_pk
            primary key,
    /*Тип токена (registration, reset_password)*/
    TYPE         text      not null,
    /*Использован*/
    IS_ACTIVE    bool      not null,
    /*ID пользователя -- внешний ключ (USERS)*/
    USER_ID      bigint    not null
        constraint tokens_users_id_fk
            references USERS on delete cascade,
    /*Дата создания*/
    CREATED_DATE timestamp not null default now()
);

comment on table TOKENS is 'Токены (регистрация, восстановление пароля)';
comment on column TOKENS.ID is 'Токен';
comment on column TOKENS.TYPE is 'Тип токена (registration, reset_password)';
comment on column TOKENS.IS_ACTIVE is 'Использован';
comment on column TOKENS.USER_ID is 'ID пользователя';
comment on column TOKENS.CREATED_DATE is 'Дата создания';

/*Таблица пользовательских фото*/
create table USER_PHOTOS
(
    /*Идентификатор -- первичный ключ*/
    ID           bigserial not null
        constraint user_photos_pk
            primary key,
    /*Текущее активное фото*/
    CURRENT      bool      not null,
    /*Идентификатор файла -- внешний ключ (FILES)*/
    FILE_ID      bigint    not null
        constraint post_photos_files_id_fk
            references FILES on delete cascade
        /*Уникальный*/
        constraint user_photos_pk_2
            unique,
    /*ID пользователя -- внешний ключ (USERS)*/
    USER_ID      bigint    not null
        constraint user_photos_users_id_fk
            references USERS on delete cascade,
    /*Дата создания*/
    CREATED_DATE timestamp not null default now()
);

comment on table USER_PHOTOS is 'Фото пользователя';
comment on column USER_PHOTOS.ID is 'Идентификатор';
comment on column USER_PHOTOS.CURRENT is 'Текущее активное фото';
comment on column USER_PHOTOS.FILE_ID is 'Идентификатор файла';
comment on column USER_PHOTOS.USER_ID is 'ID пользователя';
comment on column USER_PHOTOS.CREATED_DATE is 'Дата создания';

/*Таблица приложенных к посту фото*/
create table POST_PHOTOS
(
    /*Идентификатор -- первичный ключ*/
    ID           bigserial not null
        constraint post_photos_pk
            primary key,
    /*Идентификатор файла -- внешний ключ (FILES)*/
    FILE_ID      bigint    not null
        constraint post_photos_files_id_fk
            references FILES on delete cascade
        /*Уникальный*/
        constraint post_photos_pk_2
            unique,
    /*ID поста -- внешний ключ (POSTS)*/
    POST_ID      bigint    not null
        constraint post_photos_posts_id_fk
            references POSTS on delete cascade,
    /*Дата создания*/
    CREATED_DATE timestamp not null default now()
);

comment on table POST_PHOTOS is 'Фото поста';
comment on column POST_PHOTOS.ID is 'Идентификатор';
comment on column POST_PHOTOS.FILE_ID is 'Идентификатор файла';
comment on column POST_PHOTOS.POST_ID is 'ID поста';
comment on column POST_PHOTOS.CREATED_DATE is 'Дата создания';

/*Таблица комментариев к постам*/
create table COMMENTS
(
    /*Идентификатор -- первичный ключ*/
    ID           bigserial not null
        constraint comments_pk
            primary key,
    /*Текст*/
    COMMENT_TEXT text,
    /*ID пользователя -- внешний ключ (USERS)*/
    USER_ID      bigint    not null
        constraint comments_users_id_fk
            references USERS on delete cascade,
    /*ID поста -- внешний ключ (POSTS)*/
    POST_ID      bigint    not null
        constraint comments_posts_id_fk
            references POSTS on delete cascade,
    /*Дата создания*/
    CREATED_DATE timestamp not null default now()
);

comment on table COMMENTS is 'Комментарии';
comment on column COMMENTS.ID is 'Идентификатор';
comment on column COMMENTS.COMMENT_TEXT is 'Текст';
comment on column COMMENTS.USER_ID is 'ID пользователя';
comment on column COMMENTS.POST_ID is 'ID поста';
comment on column COMMENTS.CREATED_DATE is 'Дата создания';

/*Таблица реакций на посты*/
create table POST_REACTIONS
(
    /*Идентификатор -- первичный ключ*/
    ID            bigserial not null
        constraint post_reactions_pk
            primary key,
    /*Реакция (Лайк)*/
    REACTION_TYPE varchar   not null
        /*Допустимы только 2 значения*/
        check (REACTION_TYPE in ('LIKE', 'DISLIKE')),
    /*ID пользователя -- внешний ключ (USERS)*/
    USER_ID       bigint    not null
        constraint post_reactions_users_id_fk
            references USERS on delete cascade,
    /*ID поста -- внешний ключ (POSTS)*/
    POST_ID       bigint    not null
        constraint post_reactions_posts_id_fk
            references POSTS on delete cascade,
    /*Дата создания*/
    CREATED_DATE  timestamp not null default now(),
    /* Составное ограничение уникальности: пользователь + пост */
    constraint post_reactions_user_post_uc
        unique (USER_ID, POST_ID)
);

comment on table POST_REACTIONS is 'Реакция на пост';
comment on column POST_REACTIONS.ID is 'Идентификатор';
comment on column POST_REACTIONS.REACTION_TYPE is 'Реакция (Лайк)';
comment on column POST_REACTIONS.USER_ID is 'ID пользователя';
comment on column POST_REACTIONS.POST_ID is 'ID поста';
comment on column POST_REACTIONS.CREATED_DATE is 'Дата создания';
