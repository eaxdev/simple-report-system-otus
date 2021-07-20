package io.github.eaxdev.controller;

import io.github.eaxdev.dto.DataModelRequest;
import io.github.eaxdev.dto.DataModelResponse;
import io.github.eaxdev.mapper.DataModelMapper;
import io.github.eaxdev.service.DataModelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Validated
@RestController
@RequiredArgsConstructor
public class DataModelController {

    private final DataModelService dataModelService;

    private final DataModelMapper dataModelMapper;

    @PageableAsQueryParam
    @GetMapping(value = "/models", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List of data models", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = DataModelResponse.class)))
            })
    })
    public ResponseEntity<Page<DataModelResponse>> getAll(Pageable pageable) {
        return ResponseEntity.ok(dataModelService.findAll(pageable).map(dataModelMapper::toDto));
    }

    @GetMapping(value = "/models/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get data model by given id", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DataModelResponse.class))
            })
    })
    public ResponseEntity<DataModelResponse> getDataModelById(@PathVariable("id") @Positive int dataModelId) {
        return ResponseEntity.ok(dataModelMapper.toDto(dataModelService.findById(dataModelId)));
    }

    @PostMapping("/models")
    @Operation(summary = "Create new data model", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DataModelResponse.class))
            }),
    })
    public ResponseEntity<DataModelResponse> create(@Valid @RequestBody DataModelRequest request) {
        return ResponseEntity.ok(dataModelMapper.toDto(
                dataModelService.createOrUpdate(
                        dataModelMapper.toDataModel(request)
                )));
    }

    @DeleteMapping(value = "/models/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete data model by given id", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DataModelResponse.class))
            })
    })
    public ResponseEntity<Void> deleteById(@PathVariable("id") @Positive int dataModelId) {
        dataModelService.deleteById(dataModelId);
        return ResponseEntity.noContent().build();
    }

}
