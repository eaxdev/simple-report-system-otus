package io.github.eaxdev.dto.criteria;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Getter;

import java.util.List;

@Getter
@JsonTypeName("and")
public class And extends GroupCriteria {

    public And(List<Criteria> criteria) {
        super(criteria);
    }

    @Override
    public GroupConditionalOperator getGroupConditionalOperator() {
        return GroupConditionalOperator.AND;
    }

    @Override
    public CriteriaType getCriteriaType() {
        return CriteriaType.GROUP;
    }
}
