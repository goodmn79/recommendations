package pro.sky.recommendations.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SaveDataException extends RuntimeException {
    public SaveDataException() {
    }
}