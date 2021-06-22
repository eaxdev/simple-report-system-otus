package io.github.eaxdev.controller;

import io.github.eaxdev.dto.DataModelResponse;
import io.github.eaxdev.dto.ReportDto;
import io.github.eaxdev.mapper.ReportMapper;
import io.github.eaxdev.service.ReportService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
public class ReportManagerController {

    private final ReportMapper reportMapper;

    private final ReportService reportService;

    @PostMapping("/reports")
    @Operation(summary = "Create new report", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ReportDto.class))
            }),
    })
    public ResponseEntity<ReportDto> create(@Valid @RequestBody ReportDto request) {
        return ResponseEntity.ok(reportMapper.toDto(
                reportService.createOrUpdate(
                        reportMapper.toReport(request)
                )));
    }

    @PageableAsQueryParam
    @GetMapping(value = "/reports", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "List of reports", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = ReportDto.class)))
            })
    })
    public ResponseEntity<Page<ReportDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(reportService.findAll(pageable).map(reportMapper::toDto));
    }

    @GetMapping(value = "/reports/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get report by given id", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ReportDto.class))
            })
    })
    public ResponseEntity<ReportDto> getReportById(@PathVariable("id") @Positive int reportId) {
        return ResponseEntity.ok(reportMapper.toDto(reportService.findById(reportId)));
    }

    @DeleteMapping(value = "/reports/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Delete report by given id", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = DataModelResponse.class))
            })
    })
    public ResponseEntity<Void> deleteById(@PathVariable("id") @Positive int reportId) {
        reportService.deleteById(reportId);
        return ResponseEntity.noContent().build();
    }

}
