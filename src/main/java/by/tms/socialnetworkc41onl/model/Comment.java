/**
 * @author Pleshakov Vladimir
 * @date 10/07/2026
 */
package by.tms.socialnetworkc41onl.model;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Comment {
    private long id;
    private String commentText;
    private long userId;
    private long postId;
    private LocalDateTime createdTime;

}



