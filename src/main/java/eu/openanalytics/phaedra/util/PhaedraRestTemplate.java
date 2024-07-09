/**
 * Phaedra II
 *
 * Copyright (C) 2016-2024 Open Analytics
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
package eu.openanalytics.phaedra.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class PhaedraRestTemplate extends RestTemplate {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Add interceptor that logs outgoing HTTP requests.
     */
    @PostConstruct
    public void init() {
        getInterceptors().add((request, body, execution) -> {
            long start = System.currentTimeMillis();
            ClientHttpResponse response = execution.execute(request, body);
            long stop = System.currentTimeMillis();

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("HTTP Request");
            if (response.getStatusCode().isError()) {
                rightPad(stringBuilder, "Error", 4);
            } else {
                rightPad(stringBuilder, "Ok", 4);
            }
            rightPad(stringBuilder, String.valueOf(response.getStatusCode().value()), 3);
            rightPad(stringBuilder, stop - start + "ms", 6);
            rightPad(stringBuilder, request.getMethod().name(), 4);
            rightPad(stringBuilder, request.getURI().toString(), -1);
            logger.info(stringBuilder.toString());

            return response;
        });
    }

    /**
     * Utility function to add a rightPadded value to a StringBuilder
     * @param stringBuilder the StringBuilder to add the value to
     * @param value the value to add, will be surrounded with []
     * @param padding the minimum length of the string (without [])
     */
    private void rightPad(StringBuilder stringBuilder, String value, int padding) {
        stringBuilder.append(" [");
        stringBuilder.append(value);
        stringBuilder.append("]");

        int length = value.length();
        while (length < padding) {
            stringBuilder.append(" ");
            length++;
        }
    }

    // copy of postForObject but for put
    @Nullable
    public <T> T putForObject(String url, @Nullable Object request, Class<T> responseType,
                              Object... uriVariables) throws RestClientException {

        RequestCallback requestCallback = httpEntityCallback(request, responseType);
        HttpMessageConverterExtractor<T> responseExtractor = new HttpMessageConverterExtractor<>(responseType, getMessageConverters());
        return execute(url, HttpMethod.PUT, requestCallback, responseExtractor, uriVariables);
    }

    /**
     * Copy of getForObject but for a ParameterizedTypeReference, allowing to fetch a Generic type.
     * E.g. {@code List<Object> }
     */
    @Nullable
    public <T> T getForObject(String url, ParameterizedTypeReference<T> type, Object... uriVariables) {
        Class<? extends Type> clazz = ((ParameterizedType) type.getType()).getRawType().getClass();
        RequestCallback requestCallback = acceptHeaderRequestCallback(clazz);
        HttpMessageConverterExtractor<T> responseExtractor = new HttpMessageConverterExtractor<>(type.getType(), getMessageConverters());
        return execute(url, HttpMethod.GET, requestCallback, responseExtractor, uriVariables);
    }
}
