package io.github.eaxdev.dto.criteria;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.WRAPPER_OBJECT)
@JsonSubTypes({
        @JsonSubTypes.Type(value = And.class),
        @JsonSubTypes.Type(value = Or.class)})
@Getter
@AllArgsConstructor
@JsonDeserialize(using = GroupCriteriaDeserializer.class)
@JsonSerialize(using = GroupCriteriaSerializer.class)
public abstract class GroupCriteria implements Criteria {

    protected final List<Criteria> criteria;

    public abstract GroupConditionalOperator getGroupConditionalOperator();

}
