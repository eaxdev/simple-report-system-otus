package io.github.eaxdev.dto.criteria;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SimpleCriteria.class),
        @JsonSubTypes.Type(value = GroupCriteria.class)
})
public interface Criteria {

    @JsonIgnore
    CriteriaType getCriteriaType();

}
