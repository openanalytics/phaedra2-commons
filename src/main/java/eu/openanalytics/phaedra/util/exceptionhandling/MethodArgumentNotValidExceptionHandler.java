/**
 * Phaedra II
 *
 * Copyright (C) 2016-2025 Open Analytics
 *
 * ===========================================================================
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Apache License as published by
 * The Apache Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Apache License for more details.
 *
 * You should have received a copy of the Apache License
 * along with this program.  If not, see <http://www.apache.org/licenses/>
 */
package eu.openanalytics.phaedra.util.exceptionhandling;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface MethodArgumentNotValidExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    default Map<String, Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
    	Map<String, Object> exceptionInfo = new HashMap<>();
    	exceptionInfo.put("status", "error");
    	exceptionInfo.put("error", "Validation error");
    	exceptionInfo.put("malformed_fields", ex.getBindingResult().getAllErrors().stream()
    			.collect(Collectors.toMap(
                        error -> ((FieldError) error).getField(),
                        error -> Optional.ofNullable(error.getDefaultMessage()).orElse("Field is invalid"))
                )
        );
    	return exceptionInfo;
    }

}
