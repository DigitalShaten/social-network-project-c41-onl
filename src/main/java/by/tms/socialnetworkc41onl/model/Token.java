package by.tms.socialnetworkc41onl.model;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/** переменные столбцов с таблицы TOKENS.
 * param type - включает себя "регистрацию" или "сброс пароля"*/

@Data
@NoArgsConstructor
public class Token {

    private UUID id;
    private String type;
    private boolean isActive;
    private long userId;
    private LocalDateTime createdDate;

}
