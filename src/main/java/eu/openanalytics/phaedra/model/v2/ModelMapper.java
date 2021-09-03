package eu.openanalytics.phaedra.model.v2;

import eu.openanalytics.phaedra.model.v2.dto.CalculationInputValueDTO;
import eu.openanalytics.phaedra.model.v2.dto.ErrorDTO;
import eu.openanalytics.phaedra.model.v2.dto.FeatureDTO;
import eu.openanalytics.phaedra.model.v2.dto.FormulaDTO;
import eu.openanalytics.phaedra.model.v2.dto.ProtocolDTO;
import eu.openanalytics.phaedra.model.v2.dto.ResultDataDTO;
import eu.openanalytics.phaedra.model.v2.dto.ResultSetDTO;
import eu.openanalytics.phaedra.model.v2.runtime.CalculationInputValue;
import eu.openanalytics.phaedra.model.v2.runtime.Feature;
import eu.openanalytics.phaedra.model.v2.runtime.Formula;
import eu.openanalytics.phaedra.model.v2.runtime.Protocol;
import eu.openanalytics.phaedra.model.v2.runtime.ResultData;
import eu.openanalytics.phaedra.model.v2.runtime.ResultSet;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.NameTransformers;
import org.modelmapper.convention.NamingConventions;

import java.util.List;

public class ModelMapper {

    private final org.modelmapper.ModelMapper modelMapper = new org.modelmapper.ModelMapper();

    public ModelMapper() {
        Configuration builderConfiguration = modelMapper.getConfiguration().copy()
                .setDestinationNameTransformer(NameTransformers.builder())
                .setDestinationNamingConvention(NamingConventions.builder());

        modelMapper.createTypeMap(FormulaDTO.class, Formula.FormulaBuilder.class, builderConfiguration)
                .setPropertyCondition(Conditions.isNotNull());

        modelMapper.createTypeMap(CalculationInputValueDTO.class, CalculationInputValue.CalculationInputValueBuilder.class, builderConfiguration)
                .setPropertyCondition(Conditions.isNotNull());

        modelMapper.createTypeMap(FeatureDTO.class, Feature.FeatureBuilder.class, builderConfiguration)
                .setPropertyCondition(Conditions.isNotNull())
                .addMappings(mapper -> mapper.skip(Feature.FeatureBuilder::formula))
                .addMappings(mapper -> mapper.skip(Feature.FeatureBuilder::calculationInputValues));

        modelMapper.createTypeMap(ResultDataDTO.class, ResultData.ResultDataBuilder.class, builderConfiguration)
                .setPropertyCondition(Conditions.isNotNull());

        modelMapper.createTypeMap(ResultData.class, ResultDataDTO.ResultDataDTOBuilder.class, builderConfiguration)
                .setPropertyCondition(Conditions.isNotNull());

        modelMapper.createTypeMap(ResultSetDTO.class, ResultSet.ResultSetBuilder.class, builderConfiguration)
                .setPropertyCondition(Conditions.isNotNull())
                .addMappings(mapper -> mapper.using((Converter<List<ErrorDTO>, ResultSet.ErrorHolder>) context -> {
                    if (context.getSource() != null) {
                        return new ResultSet.ErrorHolder(context.getSource());
                    }
                    return null;
                }).map(ResultSetDTO::getErrors, ResultSet.ResultSetBuilder::errors));

        modelMapper.createTypeMap(ResultSet.class, ResultSetDTO.ResultSetDTOBuilder.class, builderConfiguration)
                .setPropertyCondition(Conditions.isNotNull());

        modelMapper.validate(); // ensure that objects can be mapped
    }

    /**
     * Returns a Builder that contains the properties of {@link Formula}, which are updated with the
     * values of a {@link FormulaDTO} while ignore properties in the {@link FormulaDTO} that are null.
     * The return value can be further customized by calling the builder methods.
     * This function should be used for PUT requests.
     */
    public Formula.FormulaBuilder map(FormulaDTO formulaDTO, Formula formula) {
        Formula.FormulaBuilder builder = formula.toBuilder();
        modelMapper.map(formulaDTO, builder);
        return builder;
    }

    /**
     * Maps a {@link FormulaDTO} to a {@link Formula.FormulaBuilder}.
     * The return value can be further customized by calling the builder methods.
     */
    public Formula.FormulaBuilder map(FormulaDTO formulaDTO) {
        Formula.FormulaBuilder builder = Formula.builder();
        modelMapper.map(formulaDTO, builder);
        return builder;
    }

    /**
     * Maps a {@link Formula} to a {@link FormulaDTO.FormulaDTOBuilder}.
     * The return value can be further customized by calling the builder methods.
     */
    public FormulaDTO.FormulaDTOBuilder map(Formula formula) {
        FormulaDTO.FormulaDTOBuilder builder = FormulaDTO.builder();
        modelMapper.map(formula, builder);
        return builder;
    }

    /**
     * Returns a Builder that contains the properties of {@link CalculationInputValue}, which are updated with the
     * values of a {@link CalculationInputValueDTO} while ignore properties in the {@link CalculationInputValueDTO} that are null.
     * The return value can be further customized by calling the builder methods.
     * This function should be used for PUT requests.
     */
    public CalculationInputValue.CalculationInputValueBuilder map(CalculationInputValueDTO calculationInputValueDTO, CalculationInputValue calculationInputValue) {
        CalculationInputValue.CalculationInputValueBuilder builder = calculationInputValue.toBuilder();
        modelMapper.map(calculationInputValueDTO, builder);
        return builder;
    }

    /**
     * Maps a {@link CalculationInputValueDTO} to a {@link CalculationInputValue.CalculationInputValueBuilder}.
     * The return value can be further customized by calling the builder methods.
     */
    public CalculationInputValue.CalculationInputValueBuilder map(CalculationInputValueDTO calculationInputValueDTO) {
        return modelMapper.map(calculationInputValueDTO, CalculationInputValue.CalculationInputValueBuilder.class);
    }

    /**
     * Maps a {@link ProtocolDTO} to a {@link Protocol.ProtocolBuilder}.
     * The return value can be further customized by calling the builder methods.
     */
    public Protocol.ProtocolBuilder map(ProtocolDTO protocolDTO) {
        Protocol.ProtocolBuilder builder = Protocol.builder();
        modelMapper.map(protocolDTO, builder);
        return builder;
    }

    /**
     * Maps a {@link FeatureDTO} to a {@link Feature.FeatureBuilder}.
     * The return value can be further customized by calling the builder methods.
     */
    public Feature.FeatureBuilder map(FeatureDTO featureDTO) {
        Feature.FeatureBuilder builder = Feature.builder();
        modelMapper.map(featureDTO, builder);
        return builder;
    }

    /**
     * Maps a {@link ResultDataDTO} to a {@link ResultData.ResultDataBuilder}.
     * The return value can be further customized by calling the builder methods.
     */
    public ResultData.ResultDataBuilder map(ResultDataDTO resultDataDTO) {
        ResultData.ResultDataBuilder builder = ResultData.builder();
        modelMapper.map(resultDataDTO, builder);
        return builder;
    }

    /**
     * Maps a {@link ResultData} to a {@link ResultDataDTO.ResultDataDTOBuilder}.
     * The return value can be further customized by calling the builder methods.
     */
    public ResultDataDTO.ResultDataDTOBuilder map(ResultData resultData) {
        ResultDataDTO.ResultDataDTOBuilder builder = ResultDataDTO.builder();
        modelMapper.map(resultData, builder);
        return builder;
    }

    /**
     * Maps a {@link ResultSetDTO} to a {@link ResultSet.ResultSetBuilder}.
     * The return value can be further customized by calling the builder methods.
     */
    public ResultSet.ResultSetBuilder map(ResultSetDTO resultSetDTO) {
        ResultSet.ResultSetBuilder builder = ResultSet.builder();
        modelMapper.map(resultSetDTO, builder);
        return builder;
    }

    /**
     * Maps a {@link ResultSet} to a {@link ResultSetDTO.ResultSetDTOBuilder}.
     * The return value can be further customized by calling the builder methods.
     */
    public ResultSetDTO.ResultSetDTOBuilder map(ResultSet resultSet) {
        ResultSetDTO.ResultSetDTOBuilder builder = ResultSetDTO.builder();
        modelMapper.map(resultSet, builder);
        return builder;
    }

    /**
     * Returns a Builder that contains the properties of {@link ResultSet}, which are updated with the
     * values of a {@link ResultSetDTO} while ignore properties in the {@link ResultSetDTO} that are null.
     * The return value can be further customized by calling the builder methods.
     * This function should be used for PUT requests.
     */
    public ResultSet.ResultSetBuilder map(ResultSetDTO resultSetDTO, ResultSet resultSet) {
        ResultSet.ResultSetBuilder builder = resultSet.toBuilder();
        modelMapper.map(resultSetDTO, builder);
        return builder;
    }

}
