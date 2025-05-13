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

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public interface HttpMessageNotReadableExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    default Map<String, Object> handleValidationExceptions(HttpMessageNotReadableException ex) {
    	Map<String, Object> exceptionInfo = new HashMap<>();
    	exceptionInfo.put("status", "error");
    	exceptionInfo.put("error", "Validation error");
    	
        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException cause = (InvalidFormatException) ex.getCause();
            String fieldName = cause.getPath().get(cause.getPath().size() - 1).getFieldName();

            Map<String, Object> malformedFields = new HashMap<>();
            malformedFields.put(fieldName, String.format("Invalid value (\"%s\") provided", cause.getValue()));
            exceptionInfo.put("malformed_fields", malformedFields);
        }
        
        return exceptionInfo;
    }

}
