package com.challenge.natixis.DDDHexagonalChallenge.port.mapper;

import com.challenge.natixis.DDDHexagonalChallenge.application.dto.CommitteeDto;
import com.challenge.natixis.DDDHexagonalChallenge.domain.model.Committee;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommitteeMapper {

    public CommitteeDto toDto(Committee committee) {
        List<String> problemsName = committee.getProblems().stream().map(problem -> problem.getName()).collect(Collectors.toList());
        return new CommitteeDto(committee.getDate(), committee.getStatus().toString(), problemsName);
    }

    public List<CommitteeDto> toDtoList(List<Committee> committees) {
        return committees.stream().map(this::toDto).collect(Collectors.toList());
    }
}