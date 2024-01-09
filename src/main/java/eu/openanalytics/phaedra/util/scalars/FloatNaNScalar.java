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

import graphql.language.FloatValue;
import graphql.language.IntValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;

public class FloatNaNScalar implements Coercing<Float, Float> {

    @Override
    public Float serialize(Object input) throws CoercingSerializeException {
        if (input instanceof Float) {
            return (Float) input;
        } else if (input instanceof Double) {
            return ((Double) input).floatValue();
        }
        throw new CoercingSerializeException("Invalid value: " + input);
    }

    @Override
    public Float parseValue(Object input) throws CoercingParseValueException {
        if (input instanceof Float) {
            return (Float) input;
        } else if (input instanceof Double) {
            return ((Double) input).floatValue();
        }
        throw new CoercingParseValueException("Invalid input: " + input);
    }

    @Override
    public Float parseLiteral(Object input) throws CoercingParseLiteralException {
        if (input instanceof FloatValue || input instanceof IntValue) {
            return Float.parseFloat(input.toString());
        }
        throw new CoercingParseLiteralException("Invalid input: " + input);
    }
}
