package eu.openanalytics.phaedra.util.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

public interface MethodArgumentNotValidExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    default HashMap<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return new HashMap<String, Object>() {{
            put("status", "error");
            put("error", "Validation error");
            put("malformed_fields", ex.getBindingResult()
                    .getAllErrors()
                    .stream().
                    collect(Collectors.toMap(
                            error -> ((FieldError) error).getField(),
                            error -> Optional.ofNullable(error.getDefaultMessage()).orElse("Field is invalid"))
                    )
            );
        }};
    }

}
