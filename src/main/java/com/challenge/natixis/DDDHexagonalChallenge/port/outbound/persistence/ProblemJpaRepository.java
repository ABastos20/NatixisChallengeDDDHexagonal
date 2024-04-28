package com.challenge.natixis.DDDHexagonalChallenge.port.outbound.persistence;

import com.challenge.natixis.DDDHexagonalChallenge.adapter.model.ProblemJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProblemJpaRepository extends JpaRepository<ProblemJpa, Long> {
    Optional<ProblemJpa> findByName(String name);
}
