package io.github.eaxdev.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.eaxdev.dto.criteria.Criteria;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public record DataModelRequest (

        @JsonProperty(value = "name", required = true)
        @Schema(description = "The name of Data Model")
        String name,

        @JsonProperty(value = "description")
        @Schema(description = "A Data Model description, which may be null")
        String description,

        @Min(1)
        @NotNull
        @JsonProperty("connectionId")
        @Schema(description = "Connection id ")
        int connectionId,

        @JsonProperty(value = "tableName", required = true)
        @Schema(description = "Base table name", example = "tbl")
        String tableName,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        @JsonProperty(value = "criteria")
        @Schema(description = "Base criteria information")
        Criteria criteria
){
}
