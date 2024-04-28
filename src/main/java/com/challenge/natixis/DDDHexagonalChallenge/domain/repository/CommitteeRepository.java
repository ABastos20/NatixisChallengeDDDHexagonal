package com.challenge.natixis.DDDHexagonalChallenge.domain.repository;

import com.challenge.natixis.DDDHexagonalChallenge.domain.model.Committee;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface CommitteeRepository {
    Optional<Committee> findByDate(LocalDate date);
    Committee save(Committee committee);
    List<Committee> findAll();
}
