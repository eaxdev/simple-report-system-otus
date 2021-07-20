package io.github.eaxdev.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.eaxdev.dto.criteria.Criteria;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Base Data Model info")
public record DataModelResponse(

        @JsonProperty(value = "id")
        @Schema(description = "The ID of Data Model")
        int id,

        @JsonProperty(value = "name", required = true)
        @Schema(description = "The name of Data Model")
        String name,

        @JsonProperty(value = "description")
        @Schema(description = "A Data Model description, which may be null")
        String description,

        @JsonProperty(value = "tableName", required = true)
        @Schema(description = "Base table name", example = "tbl")
        String tableName,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty(value = "criteria")
        @Schema(description = "Base criteria information")
        Criteria criteria
) {
}