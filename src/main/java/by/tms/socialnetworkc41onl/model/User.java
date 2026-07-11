package by.tms.socialnetworkc41onl.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

/** переменные столбцов с таблицы USERS. */

@Data
@NoArgsConstructor
public class User {

    private long id;
    private String userName;
    private String email;
    private String passwordHash;
    private boolean status;
    private String firstName;
    private String lastName;
    private LocalDate birthday;
    private String gender;
    private String about;
    private LocalDateTime createdDate;

}
