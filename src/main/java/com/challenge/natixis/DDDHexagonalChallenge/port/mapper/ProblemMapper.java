package com.challenge.natixis.DDDHexagonalChallenge.port.mapper;

import com.challenge.natixis.DDDHexagonalChallenge.application.dto.ProblemDto;
import com.challenge.natixis.DDDHexagonalChallenge.domain.model.Problem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProblemMapper {

    public ProblemDto toDto(Problem problem) {
        List<String> comments = problem.getComments().stream().map(comment -> comment.getContent()).collect(Collectors.toList());
        return new ProblemDto(problem.getName(), problem.getStatus().toString(), comments);
    }

    public List<ProblemDto> toDtoList(List<Problem> problems) {
        return problems.stream().map(this::toDto).collect(Collectors.toList());
    }


    /* Allows for something like this, also used String in Dto to showcase why use Mapper Dto and not just the domain class
    public ProblemDto toDto(Problem problem) {
        String descriptiveStatus = getDescriptiveStatus(problem.getStatus());
        return new ProblemDto(problem.getName(), descriptiveStatus);
    }
    private String getDescriptiveStatus(ProblemStatus status) {
        switch (status) {
            case OPENED:
                return "Open for discussion";
            case READY:
                return "Ready to be addressed in committee";
            case CLOSED:
                return "Issue has been resolved";
            default:
                return "Unknown status";
        }
    }
     */
}