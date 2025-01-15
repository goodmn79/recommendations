package tgBot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BadMessageException extends RuntimeException {
    public BadMessageException(String message) {
        super(message);
    }
}
