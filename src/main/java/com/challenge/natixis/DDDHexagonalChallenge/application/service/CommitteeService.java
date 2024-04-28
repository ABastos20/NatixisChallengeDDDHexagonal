package com.challenge.natixis.DDDHexagonalChallenge.application.service;

import com.challenge.natixis.DDDHexagonalChallenge.domain.model.Committee;

import java.time.LocalDate;
import java.util.List;

public interface CommitteeService {
    void scheduleCommittee(LocalDate date);
    void startCommittee(LocalDate date);
    void closeCommittee(LocalDate date);
    List<Committee> findAllCommittees();

}