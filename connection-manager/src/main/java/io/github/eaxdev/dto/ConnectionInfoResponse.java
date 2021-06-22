package io.github.eaxdev.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "Common info about connection")
public record ConnectionInfoResponse(

        @NotNull
        @JsonProperty(value = "connectionId", index = 0)
        int connectionId,

        @NotNull
        @NotBlank
        @JsonProperty(value = "connectionName", index = 1)
        String connectionName,

        @JsonProperty(value = "database", index = 2)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String database,

        @NotNull
        @NotBlank
        @JsonProperty(value = "description", index = 3)
        String description,

        @JsonProperty(value = "active", required = true, index = 4)
        boolean active
) {

}
