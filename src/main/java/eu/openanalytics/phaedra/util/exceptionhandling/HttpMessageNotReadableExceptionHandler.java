package eu.openanalytics.phaedra.util.exceptionhandling;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;

public interface HttpMessageNotReadableExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    default HashMap<String, Object> handleValidationExceptions(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException cause = (InvalidFormatException) ex.getCause();
            String fieldName = cause.getPath().get(cause.getPath().size() - 1).getFieldName();

            return new HashMap<String, Object>() {{
                put("status", "error");
                put("error", "Validation error");
                put("malformed_fields", new HashMap<String, String>() {{
                    put(fieldName, String.format("Invalid value (\"%s\") provided", cause.getValue()));
                }});
            }};
        }
        return new HashMap<String, Object>() {{
            put("status", "error");
            put("error", "Validation error");
        }};
    }

}
