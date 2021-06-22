package io.github.eaxdev.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "Request for create or update connection")
public record ConnectionInfoRequest(

        @JsonProperty("connectionId")
        String connectionId,

        @NotNull
        @NotBlank
        @JsonProperty("connectionName")
        String connectionName,

        @JsonProperty("description")
        String description,

        @NotNull
        @NotBlank
        @JsonProperty("host")
        String host,

        @Min(1)
        @Max(65535)
        @JsonProperty("port")
        int port,

        @JsonProperty("database")
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String database,

        @JsonProperty("userName")
        String userName,

        @JsonProperty("password")
        String plainPassword,

        @Valid
        List<ConnectionPropertyRequest> connectionProperties

) {
}
