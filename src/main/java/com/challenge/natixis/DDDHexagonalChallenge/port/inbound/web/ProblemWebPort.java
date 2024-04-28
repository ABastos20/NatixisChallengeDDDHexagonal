package com.challenge.natixis.DDDHexagonalChallenge.port.inbound.web;

import com.challenge.natixis.DDDHexagonalChallenge.application.dto.CommitteeDto;
import com.challenge.natixis.DDDHexagonalChallenge.application.dto.ProblemDto;
import com.challenge.natixis.DDDHexagonalChallenge.application.service.ProblemService;
import com.challenge.natixis.DDDHexagonalChallenge.domain.model.Problem;
import com.challenge.natixis.DDDHexagonalChallenge.port.mapper.ProblemMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import io.swagger.v3.oas.annotations.tags.Tag;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/problems")
@Tag(name = "Problem Management", description = "API for managing problems related to committees")
public class ProblemWebPort {

    private final ProblemService problemService;
    private final ProblemMapper problemMapper;

    public ProblemWebPort(ProblemService problemService, ProblemMapper problemMapper) {
        this.problemService = problemService;
        this.problemMapper = problemMapper;
    }

    @PostMapping
    @Operation(summary = "Open a new problem",
            description = "Create a new problem with the specified name.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Problem opened successfully"),
                    @ApiResponse(responseCode = "409", description = "Problem already created"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            })
    public ResponseEntity<ProblemDto> openProblem(@RequestBody ProblemDto problemDto) {
        problemService.openProblem(problemDto.getName());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{problemName}/comments")
    @Operation(summary = "Add comment to a problem",
            description = "Create a new comment attached with the specified problem.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Comment added successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data")
            })
    public ResponseEntity<Void> addComment(@PathVariable String problemName, @RequestBody String comment) {
        problemService.addComment(problemName, comment);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{problemName}/committees/{committeeDate}")
    @Operation(summary = "Add a problem to a committee",
            description = "Add a ready problem to the specified committee.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Problem attached successfully"),
                    @ApiResponse(responseCode = "409", description = "Committee is already closed"),
                    @ApiResponse(responseCode = "400", description = "Invalid request data or problem not ready to be added to a committee or committee not found")
            })
    public ResponseEntity<Void> addProblemToCommittee(@PathVariable String problemName, @PathVariable String committeeDate) {
        LocalDate date = LocalDate.parse(committeeDate);
        problemService.addProblemToCommittee(problemName, date);
        return ResponseEntity.ok().build();
    }

    //accessibility purpose
    @Operation(summary = "List all problems",
            description = "Retrieve a list of all opened, ready, closed problems.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List retrieved successfully",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProblemDto.class))})
            })
    @GetMapping
    public ResponseEntity<List<ProblemDto>> findAllProblems() {
        List<Problem> problems = problemService.findAllProblems();
        List<ProblemDto> problemDtos = problemMapper.toDtoList(problems);

        return ResponseEntity.ok(problemDtos);
    }

}






