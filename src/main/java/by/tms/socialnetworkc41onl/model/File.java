package by.tms.socialnetworkc41onl.model;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class File {
    private long id;
    private String fileName;
    private byte [] data;
    private LocalDateTime createdDate;
}
