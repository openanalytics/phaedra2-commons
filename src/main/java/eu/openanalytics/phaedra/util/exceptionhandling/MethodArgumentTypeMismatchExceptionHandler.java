package eu.openanalytics.phaedra.util.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;

public interface MethodArgumentTypeMismatchExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    default HashMap<String, Object> handleValidationExceptions(MethodArgumentTypeMismatchException ex) {
        return new HashMap<String, Object>() {{
            put("status", "error");
            put("error", "Validation error");
            put("malformed_fields", new HashMap<String, String>() {{
                put(ex.getName(), String.format("Invalid value (\"%s\") provided", ex.getValue()));
            }});
        }};
    }

}
