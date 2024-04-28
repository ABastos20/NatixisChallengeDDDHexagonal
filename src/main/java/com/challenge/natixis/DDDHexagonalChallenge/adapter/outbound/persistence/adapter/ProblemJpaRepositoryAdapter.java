package com.challenge.natixis.DDDHexagonalChallenge.adapter.outbound.persistence.adapter;

import com.challenge.natixis.DDDHexagonalChallenge.adapter.mapper.CommentJpaMapper;
import com.challenge.natixis.DDDHexagonalChallenge.adapter.mapper.ProblemJpaMapper;
import com.challenge.natixis.DDDHexagonalChallenge.adapter.model.ProblemJpa;
import com.challenge.natixis.DDDHexagonalChallenge.domain.model.Problem;
import com.challenge.natixis.DDDHexagonalChallenge.domain.repository.ProblemRepository;
import com.challenge.natixis.DDDHexagonalChallenge.port.outbound.persistence.ProblemJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProblemJpaRepositoryAdapter implements ProblemRepository {

    @Autowired
    private ProblemJpaRepository problemJpaRepository;

    public List<Problem> findAll() {
        return problemJpaRepository.findAll().stream()
                .map(ProblemJpaMapper::toDomainObject)
                .collect(Collectors.toList());
    }

    public Problem save(Problem problem) {
        Optional<ProblemJpa> existingProblemJpaOpt = problemJpaRepository.findByName(problem.getName());

        ProblemJpa problemJpa = existingProblemJpaOpt.orElseGet(() -> ProblemJpaMapper.toEntity(problem));
        if (existingProblemJpaOpt.isPresent()) {
            ProblemJpa existingProblemJpa = existingProblemJpaOpt.get();
            if (existingProblemJpa.getComments() == null || existingProblemJpa.getComments().containsAll(problem.getComments())) {
                throw new RuntimeException("Problem already created");
            }
            problemJpa.setComments(CommentJpaMapper.toEntityList(problem.getComments()));
            existingProblemJpa.setStatus(problem.getStatus());
            problemJpa=existingProblemJpa;
        }
        ProblemJpa savedProblemJpa = problemJpaRepository.save(problemJpa);
        return ProblemJpaMapper.toDomainObject(savedProblemJpa);
    }


    @Override
    public Optional<Problem> findByName(String name) {
        return problemJpaRepository.findByName(name)
                .map(ProblemJpaMapper::toDomainObject);
    }

}
