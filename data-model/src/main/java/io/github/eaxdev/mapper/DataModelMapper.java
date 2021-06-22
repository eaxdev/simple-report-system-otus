package io.github.eaxdev.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.eaxdev.dto.DataModelRequest;
import io.github.eaxdev.dto.DataModelResponse;
import io.github.eaxdev.dto.criteria.Criteria;
import io.github.eaxdev.model.DataModel;
import io.github.exception.DataModelException;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class DataModelMapper {

    @Autowired
    private ObjectMapper objectMapper;

    @Mapping(target = "criteria", source = "source", qualifiedByName = "toCriteria")
    public abstract DataModelResponse toDto(DataModel source);

    @Mapping(target = "jsonCriteria", source = "source", qualifiedByName = "toCriteriaJson")
    public abstract DataModel toDataModel(DataModelRequest source);

    @Named("toCriteria")
    protected Criteria toCriteria(DataModel source) {
        String criteria = source.getJsonCriteria();
        if (criteria == null) {
            return null;
        }
        try {
            return objectMapper.readValue(criteria, Criteria.class);
        } catch (JsonProcessingException e) {
            throw new DataModelException(e);
        }
    }

    @Named("toCriteriaJson")
    protected String toCriteriaJson(DataModelRequest request) {
        Criteria criteria = request.criteria();
        if (criteria == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(criteria);
        } catch (JsonProcessingException e) {
            throw new DataModelException(e);
        }
    }

}
