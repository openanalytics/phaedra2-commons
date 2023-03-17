/**
 * Phaedra II
 *
 * Copyright (C) 2016-2023 Open Analytics
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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

public interface MethodArgumentTypeMismatchExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    default Map<String, Object> handleValidationExceptions(MethodArgumentTypeMismatchException ex) {
    	Map<String, Object> exceptionInfo = new HashMap<>();
    	exceptionInfo.put("status", "error");
    	exceptionInfo.put("error", "Validation error");
    	
    	Map<String, Object> malformedFields = new HashMap<>();
        malformedFields.put(ex.getName(), String.format("Invalid value (\"%s\") provided", ex.getValue()));
        exceptionInfo.put("malformed_fields", malformedFields);

        return exceptionInfo;
    }

}
