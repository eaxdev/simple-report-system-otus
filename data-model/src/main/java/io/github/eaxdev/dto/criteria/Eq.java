package io.github.eaxdev.dto.criteria;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonTypeName("eq")
@NoArgsConstructor
public class Eq extends SimpleCriteria {

    public Eq(String fieldName, String value) {
        super(fieldName, value);
    }

    @Override
    public SimpleConditionalOperator getSimpleConditionalOperator() {
        return SimpleConditionalOperator.EQUALS_TO;
    }

    @Override
    public CriteriaType getCriteriaType() {
        return CriteriaType.SIMPLE;
    }
}
