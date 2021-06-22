package io.github.eaxdev.controller;

import io.github.eaxdev.dto.ConnectionInfoRequest;
import io.github.eaxdev.dto.ConnectionInfoResponse;
import io.github.eaxdev.mapper.ConnectionInfoMapper;
import io.github.eaxdev.service.ConnectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionService connectionService;
    private final ConnectionInfoMapper connectionInfoMapper;

    @PageableAsQueryParam
    @GetMapping(value = "/connections")
    @Operation(summary = "Return all registered DB connections", responses = {
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Page.class))))})
    public ResponseEntity<Page<ConnectionInfoResponse>> getAllConnections(@Parameter(hidden = true) Pageable pageable) {
        return ResponseEntity.ok(connectionService.getAllConnections(pageable));
    }

    @PostMapping("/connections")
    @Operation(summary = "Add new connection", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content =
                    {@Content(mediaType = "application/json", schema = @Schema(implementation = ConnectionInfoResponse.class))}),
    })
    public ResponseEntity<ConnectionInfoResponse> addConnection(@Valid @RequestBody ConnectionInfoRequest request) {
        return ResponseEntity.ok(connectionInfoMapper.toConnectionInfoResponse(
                connectionService.createNewConnection(
                        connectionInfoMapper.toConnectionInfo(request)
                )));
    }

}
