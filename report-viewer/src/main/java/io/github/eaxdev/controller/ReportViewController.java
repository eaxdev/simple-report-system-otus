package io.github.eaxdev.controller;

import io.github.eaxdev.service.ReportViewService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReportViewController {

    private final ReportViewService reportViewService;

    @PageableAsQueryParam
    @GetMapping(value = "/data/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Data of report", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = Page.class)))
            })
    })
    public Page<Map<String, Object>> getData(Pageable pageable, @PathVariable("id") @Positive int reportId) {
        return reportViewService.getReportData(reportId, pageable);
    }

}
