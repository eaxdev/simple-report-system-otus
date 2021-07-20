package io.github.eaxdev.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "Contain id and status of connection")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public record ConnectionPropertyRequest(

        @JsonProperty("id")
        Integer id,

        @NotNull
        @NotBlank
        @JsonProperty("name")
        String name,

        @NotNull
        @JsonProperty("value")
        String value

        ) {

}
