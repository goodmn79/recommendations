/*
Powered by ©AYE.team
 */

package pro.sky.recommendations.recommendation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidQueryDataException extends RuntimeException {
    public InvalidQueryDataException() {
    }
}
