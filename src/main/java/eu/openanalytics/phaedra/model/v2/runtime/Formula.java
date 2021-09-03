package eu.openanalytics.phaedra.model.v2.runtime;

import eu.openanalytics.phaedra.model.v2.enumeration.CalculationScope;
import eu.openanalytics.phaedra.model.v2.enumeration.Category;
import eu.openanalytics.phaedra.model.v2.enumeration.ScriptLanguage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Value
@With
@Builder(toBuilder = true)
@AllArgsConstructor
public class Formula {
    @Id
    Long id;

    @NotNull
    String name;

    @NotNull
    String description;

    @NotNull
    Category category;

    @NotNull
    String formula;

    @NotNull
    ScriptLanguage language;

    @NotNull
    CalculationScope scope;

    @NotNull
    String createdBy;

    @NotNull
    LocalDateTime createdOn;

    String updatedBy;

    LocalDateTime updatedOn;
}
