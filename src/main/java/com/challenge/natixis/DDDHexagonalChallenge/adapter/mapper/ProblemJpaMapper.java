package com.challenge.natixis.DDDHexagonalChallenge.adapter.mapper;

import com.challenge.natixis.DDDHexagonalChallenge.adapter.model.ProblemJpa;
import com.challenge.natixis.DDDHexagonalChallenge.domain.model.Problem;

import java.util.List;
import java.util.stream.Collectors;

public class ProblemJpaMapper {


    public static List<ProblemJpa> toEntityList(List<Problem> problems) {
        return problems.stream().map(ProblemJpaMapper::toEntity).collect(Collectors.toList());
    }

    public static ProblemJpa toEntity(Problem problem) {
        ProblemJpa entity = new ProblemJpa();
        entity.setName(problem.getName());
        entity.setStatus(problem.getStatus());
        entity.setComments(CommentJpaMapper.toEntityList(problem.getComments()));
        return entity;
    }

    public static List<Problem> toDomainList(List<ProblemJpa> problems) {
        return problems.stream().map(ProblemJpaMapper::toDomainObject).collect(Collectors.toList());
    }

    public static Problem toDomainObject(ProblemJpa entity) {
        return new Problem(entity.getName(), entity.getStatus(), CommentJpaMapper.toDomainList(entity.getComments()));
    }
}
