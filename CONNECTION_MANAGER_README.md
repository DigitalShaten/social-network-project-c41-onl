## Настройка базы данных

Приложение берет настройки из файла:

```text
src/main/resources/application.properties
```

Минимальный набор свойств:

```properties
db.url=jdbc:postgresql://localhost:5432/postgres
db.user=your_user
db.password=your_password
db.pool.maximumPoolSize=10
```

Что означают параметры:

- `db.url` - JDBC URL подключения к PostgreSQL.
- `db.user` - пользователь базы данных.
- `db.password` - пароль пользователя базы данных.
- `db.pool.maximumPoolSize` - максимальное количество соединений в пуле HikariCP.

## Подключение к базе в коде

Весь код приложения должен получать соединения через:

```java
Connection connection = ConnectionManager.getConnection();
```

`ConnectionManager` один раз читает `application.properties`, создает один общий пул HikariCP и дальше выдает соединения из этого пула.

Пример использования:

```java
try (Connection connection = ConnectionManager.getConnection()) {
    // SQL-запросы
}
```

После выхода из `try-with-resources` вызывается `connection.close()`. Для HikariCP это означает не закрытие физического подключения навсегда, а возврат соединения обратно в пул.

## Загрузка настроек

За чтение `application.properties` отвечает класс:

```text
src/main/java//by/tms/socialnetworkc41onl/util/ApplicationProperties.java
```

Он загружает файл один раз и позволяет получать значения по ключу:

```java
ApplicationProperties.loadProperties("db.url");
```

Если обязательного свойства нет или оно пустое, приложение выбросит понятную ошибку.
