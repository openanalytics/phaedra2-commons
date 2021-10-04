package eu.openanalytics.phaedra.util.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;

public interface UserVisibleExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    default HashMap<String, Object> handleValidationExceptions(EntityNotFoundException ex) {
        return handleValidationExceptions((UserVisibleException) ex);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserVisibleException.class)
    default HashMap<String, Object> handleValidationExceptions(UserVisibleException ex) {
        return new HashMap<String, Object>() {{
            put("status", "error");
            put("error", ex.getMessage());
        }};
    }

}
