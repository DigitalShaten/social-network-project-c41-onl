package by.tms.socialnetworkc41onl.service;

/** Понятная пользователю ошибка бизнес-логики (показывается в форме). */
public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }
}
