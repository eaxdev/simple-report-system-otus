package io.github.eaxdev.dto.criteria;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonTypeName("ge")
@NoArgsConstructor
public class Ge extends SimpleCriteria {

    public Ge(String fieldName, String value) {
        super(fieldName, value);
    }

    @Override
    public SimpleConditionalOperator getSimpleConditionalOperator() {
        return SimpleConditionalOperator.GREATER_THAN_EQUALS;
    }

    @Override
    public CriteriaType getCriteriaType() {
        return CriteriaType.SIMPLE;
    }

}
