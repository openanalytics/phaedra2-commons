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
