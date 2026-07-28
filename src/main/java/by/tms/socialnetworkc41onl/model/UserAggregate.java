package by.tms.socialnetworkc41onl.model;

/**
 * @author Ирина Мизгир
 * @date 26.07.2026 19:22
 */
public record UserAggregate(
        long userId,
        String firstName,
        String lastName,
        Long fileId,
        boolean subscribed
) {
}
