package io.github.eaxdev.dto.criteria;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonTypeName("gt")
@NoArgsConstructor
public class Gt extends SimpleCriteria {

    public Gt(String fieldName, String value) {
        super(fieldName, value);
    }

    @Override
    public SimpleConditionalOperator getSimpleConditionalOperator() {
        return SimpleConditionalOperator.GREATER_THAN;
    }

    @Override
    public CriteriaType getCriteriaType() {
        return CriteriaType.SIMPLE;
    }

}
