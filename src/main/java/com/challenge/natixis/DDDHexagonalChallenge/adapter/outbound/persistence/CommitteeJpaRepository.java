package com.challenge.natixis.DDDHexagonalChallenge.adapter.outbound.persistence;

import com.challenge.natixis.DDDHexagonalChallenge.adapter.model.CommitteeJpa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CommitteeJpaRepository extends JpaRepository<CommitteeJpa, Long> {
    Optional<CommitteeJpa> findByDate(LocalDate date);

}
