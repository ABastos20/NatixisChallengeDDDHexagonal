package com.challenge.natixis.DDDHexagonalChallenge.adapter.mapper;

import com.challenge.natixis.DDDHexagonalChallenge.adapter.model.CommitteeJpa;
import com.challenge.natixis.DDDHexagonalChallenge.domain.model.Committee;

public class CommitteeJpaMapper {

    public static CommitteeJpa toEntity(Committee committee) {
        CommitteeJpa entity = new CommitteeJpa();
        entity.setDate(committee.getDate());
        entity.setStatus(committee.getStatus());
        entity.setProblems(ProblemJpaMapper.toEntityList(committee.getProblems()));
        return entity;
    }

    public static Committee toDomainObject(CommitteeJpa entity) {
        return new Committee(entity.getDate(), entity.getStatus(), ProblemJpaMapper.toDomainList(entity.getProblems()));
    }
}
