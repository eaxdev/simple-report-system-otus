package io.github.eaxdev.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.eaxdev.dto.criteria.Criteria;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class ReportDto {
    @JsonProperty(value = "id")
    @Schema(description = "Id of report")
    private Integer id;

    @JsonProperty(value = "name", required = true)
    @Schema(description = "The name of report")
    private String name;

    @JsonProperty(value = "description")
    @Schema(description = "A report description, which may be null")
    private String description;

    @Min(1)
    @NotNull
    @JsonProperty("modelId")
    @Schema(description = "Model id ")
    private int modelId;

    @JsonProperty("outputs")
    @Schema(description = "Output field or functions")
    private List<String> outputs;

    @JsonProperty("groupBy")
    @Schema(description = "GroupBy field name")
    private String groupBy;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "criteria")
    @Schema(description = "Criteria information")
    private Criteria criteria;
}
