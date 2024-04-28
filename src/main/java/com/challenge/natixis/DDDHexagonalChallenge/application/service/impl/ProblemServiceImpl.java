package com.challenge.natixis.DDDHexagonalChallenge.application.service.impl;

import com.challenge.natixis.DDDHexagonalChallenge.application.service.ProblemService;
import com.challenge.natixis.DDDHexagonalChallenge.domain.model.*;
import com.challenge.natixis.DDDHexagonalChallenge.domain.repository.CommitteeRepository;
import com.challenge.natixis.DDDHexagonalChallenge.domain.repository.ProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProblemServiceImpl implements ProblemService {

    private final ProblemRepository problemRepository;
    private final CommitteeRepository committeeRepository;

    @Autowired
    public ProblemServiceImpl(ProblemRepository problemRepository, CommitteeRepository committeeRepository) {
        this.problemRepository = problemRepository;
        this.committeeRepository = committeeRepository;
    }

    @Override
    @Transactional
    public void openProblem(String name) {
        Problem problem = new Problem(name);
        problemRepository.save(problem);
    }

    @Override
    @Transactional
    public void addComment(String problemName, String commentContent) {
        Problem problem = problemRepository.findByName(problemName)
                .orElseThrow(() -> new RuntimeException("Problem not found with name: " + problemName));
        problem.addComment(new Comment(commentContent));
        problemRepository.save(problem);
    }

    @Override
    @Transactional
    public void addProblemToCommittee(String problemName, LocalDate committeeDate) {
        Problem problem = problemRepository.findByName(problemName)
                .orElseThrow(() -> new RuntimeException("Problem not found with name: " + problemName));
        if (problem.getStatus() != ProblemStatus.READY) {
            throw new RuntimeException("Problem is not ready to be added to a committee.");
        }
        Committee committee = committeeRepository.findByDate(committeeDate)
                .orElseThrow(() -> new RuntimeException("Committee not found on date: " + committeeDate));
        if(committee.getStatus()== CommitteeStatus.CLOSED){
            throw new IllegalStateException("Committee is already closed.");
        }
        if(committee.getProblems().contains(problem)){
            throw new IllegalStateException("Problem is already in the committee.");
        }
        committee.addProblem(problem);
        committeeRepository.save(committee);
    }

    @Override
    public List<Problem> findAllProblems() {
        List<Problem> problemList = problemRepository.findAll();
        if(problemList==null){
            throw new RuntimeException("No Problems found!");
        }
        return problemList;
    }

}