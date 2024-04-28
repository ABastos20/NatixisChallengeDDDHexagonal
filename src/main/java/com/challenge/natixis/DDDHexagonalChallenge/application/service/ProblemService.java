package com.challenge.natixis.DDDHexagonalChallenge.application.service;

import com.challenge.natixis.DDDHexagonalChallenge.domain.model.Problem;

import java.time.LocalDate;
import java.util.List;

public interface ProblemService {
    void openProblem(String name);
    void addComment(String problemName, String commentContent);
    void addProblemToCommittee(String problemName, LocalDate committeeDate);
    List<Problem> findAllProblems();

}
