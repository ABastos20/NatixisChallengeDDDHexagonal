package com.challenge.natixis.DDDHexagonalChallenge.port.inbound.web;

import com.challenge.natixis.DDDHexagonalChallenge.application.dto.CommitteeDto;
import com.challenge.natixis.DDDHexagonalChallenge.application.service.CommitteeService;
import com.challenge.natixis.DDDHexagonalChallenge.domain.model.Committee;
import com.challenge.natixis.DDDHexagonalChallenge.port.mapper.CommitteeMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/committees")
@Tag(name = "Committee Management", description = "API for managing committees")
public class CommitteeWebPort {

    private final CommitteeService committeeService;
    private final CommitteeMapper committeeMapper;


    public CommitteeWebPort(CommitteeService committeeService, CommitteeMapper committeeMapper) {
        this.committeeService = committeeService;
        this.committeeMapper = committeeMapper;
    }



    @PostMapping
    @Operation(summary = "Schedule a new committee",
            description = "Schedule a new committee for a given date.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Committee scheduled successfully"),
                    @ApiResponse(responseCode = "409", description = "Committee already created"),
                    @ApiResponse(responseCode = "400", description = "Invalid date provided or in the past")
            })
    public ResponseEntity<Void> scheduleCommittee(@RequestBody CommitteeDto committeeDto) {
        committeeService.scheduleCommittee(committeeDto.getDate());
        URI location = URI.create(String.format("/api/committees/%s", committeeDto.getDate()));
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/start")
    @Operation(summary = "Start a committee",
            description = "Start the committee scheduled for the given date.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Committee started successfully"),
                    @ApiResponse(responseCode = "409", description = "Committee is already in progress or closed"),
                    @ApiResponse(responseCode = "400", description = "Committee not found or invalid date provided")
            })
    public ResponseEntity<Void> startCommittee(@RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        committeeService.startCommittee(localDate);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/close")
    @Operation(summary = "Close a committee",
            description = "Close the committee that is in progress.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Committee closed successfully"),
                    @ApiResponse(responseCode = "409", description = "Committee is already closed"),
                    @ApiResponse(responseCode = "400", description = "Committee not found or invalid date provided")
            })
    public ResponseEntity<Void> closeCommittee(@RequestParam String date) {
        LocalDate localDate = LocalDate.parse(date);
        committeeService.closeCommittee(localDate);
        return ResponseEntity.ok().build();
    }


    //accessibility purpose
    @GetMapping
    @Operation(summary = "List all committees",
            description = "Retrieve a list of all scheduled, ongoing, or closed committees.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List retrieved successfully",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommitteeDto.class))})
            })
    public ResponseEntity<List<CommitteeDto>> getAllCommittees() {
        List<Committee> committees = committeeService.findAllCommittees();
        List<CommitteeDto> committeeDtos = committeeMapper.toDtoList(committees);

        return ResponseEntity.ok(committeeDtos);
    }

}

