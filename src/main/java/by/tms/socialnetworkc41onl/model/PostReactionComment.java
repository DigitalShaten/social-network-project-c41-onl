/**
 * @author Pleshakov Vladimir
 * @since 2026-10-07
 */
package by.tms.socialnetworkc41onl.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data


public class PostReactionComment {
 long id;
 String commentText;
 long userId;
 long postId;
 LocalDateTime createdTime;



}
