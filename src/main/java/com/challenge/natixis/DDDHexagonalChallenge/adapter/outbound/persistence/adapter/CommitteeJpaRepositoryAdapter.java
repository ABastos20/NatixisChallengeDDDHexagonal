package com.challenge.natixis.DDDHexagonalChallenge.adapter.outbound.persistence.adapter;

import com.challenge.natixis.DDDHexagonalChallenge.adapter.mapper.CommentJpaMapper;
import com.challenge.natixis.DDDHexagonalChallenge.adapter.mapper.CommitteeJpaMapper;
import com.challenge.natixis.DDDHexagonalChallenge.adapter.mapper.ProblemJpaMapper;
import com.challenge.natixis.DDDHexagonalChallenge.adapter.model.CommitteeJpa;
import com.challenge.natixis.DDDHexagonalChallenge.adapter.model.ProblemJpa;
import com.challenge.natixis.DDDHexagonalChallenge.adapter.outbound.persistence.CommitteeJpaRepository;
import com.challenge.natixis.DDDHexagonalChallenge.adapter.outbound.persistence.ProblemJpaRepository;
import com.challenge.natixis.DDDHexagonalChallenge.domain.model.Committee;
import com.challenge.natixis.DDDHexagonalChallenge.domain.model.CommitteeStatus;
import com.challenge.natixis.DDDHexagonalChallenge.domain.model.ProblemStatus;
import com.challenge.natixis.DDDHexagonalChallenge.domain.repository.CommitteeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CommitteeJpaRepositoryAdapter implements CommitteeRepository {

    @Autowired
    CommitteeJpaRepository committeeJpaRepository;

    @Autowired
    ProblemJpaRepository problemJpaRepository;

    @Override
    public Optional<Committee> findByDate(LocalDate date) {
        return committeeJpaRepository.findByDate(date)
                .map(CommitteeJpaMapper::toDomainObject); // Assuming there is a method `findByDate` in CommitteeJpaRepository
    }

    public List<Committee> findAll() {
        return committeeJpaRepository.findAll().stream()
                .map(CommitteeJpaMapper::toDomainObject)
                .collect(Collectors.toList());
    }

    public Committee save(Committee committee) {
        Optional<CommitteeJpa> existingCommitteeJpaOpt = committeeJpaRepository.findByDate(committee.getDate());

        CommitteeJpa committeeJpa = existingCommitteeJpaOpt.orElseGet(() -> CommitteeJpaMapper.toEntity(committee));

        if (existingCommitteeJpaOpt.isPresent()) {
            CommitteeJpa existingCommitteeJpa = existingCommitteeJpaOpt.get();
            if (existingCommitteeJpa.getProblems() == null || existingCommitteeJpa.getProblems().containsAll(committee.getProblems())) {
                throw new RuntimeException("Committee already scheduled");
            }
        }
        // No need to check for existing problems by ID (as there's none)
        List<ProblemJpa> problemsJpa = committee.getProblems().stream()
                .map(problem -> problemJpaRepository.findByName(problem.getName())
                        .orElseGet(() -> ProblemJpaMapper.toEntity(problem)))
                .collect(Collectors.toList());

        if(committee.getStatus()== CommitteeStatus.CLOSED){
            problemsJpa.forEach(problemJpa -> problemJpa.setStatus(ProblemStatus.CLOSED));
        }
        committeeJpa.setStatus(committee.getStatus());
        committeeJpa.setProblems(problemsJpa);

        CommitteeJpa savedCommitteeJpa = committeeJpaRepository.save(committeeJpa);
        return CommitteeJpaMapper.toDomainObject(savedCommitteeJpa);
    }
}
