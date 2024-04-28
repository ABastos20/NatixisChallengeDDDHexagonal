package com.challenge.natixis.DDDHexagonalChallenge.domain.repository;

import com.challenge.natixis.DDDHexagonalChallenge.domain.model.Problem;

import java.util.List;
import java.util.Optional;


public interface ProblemRepository {

    Optional<Problem> findByName(String name);
    Problem save(Problem problem);
    List<Problem> findAll();
}
