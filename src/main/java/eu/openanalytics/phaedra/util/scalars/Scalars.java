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
package eu.openanalytics.phaedra.util.scalars;

import graphql.schema.GraphQLScalarType;

public class Scalars {
    public static GraphQLScalarType dateType() {
        return GraphQLScalarType.newScalar()
                .name("Date")
                .description("Java util Date as scalar.")
                .coercing(new DateScalar()).build();
    }

    public static GraphQLScalarType floatNaNType() {
        return GraphQLScalarType.newScalar()
                .name("FloatNaN")
                .description("Custom scalar for handling NaN values")
                .coercing(new FloatNaNScalar()).build();
    }
}
