package com.challenge.natixis.DDDHexagonalChallenge.application.service.impl;

import com.challenge.natixis.DDDHexagonalChallenge.adapter.outbound.persistence.adapter.CommitteeJpaRepositoryAdapter;
import com.challenge.natixis.DDDHexagonalChallenge.application.service.CommitteeService;
import com.challenge.natixis.DDDHexagonalChallenge.domain.model.Committee;
import com.challenge.natixis.DDDHexagonalChallenge.domain.repository.CommitteeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class CommitteeServiceImpl implements CommitteeService {

    private final CommitteeRepository committeeRepository;

    @Autowired
    public CommitteeServiceImpl(CommitteeRepository committeeRepository) {
        this.committeeRepository = committeeRepository;
    }

    @Override
    @Transactional
    public void scheduleCommittee(LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new RuntimeException("Cannot schedule a committee in the past.");
        }
        Committee committee = new Committee(date);
        committeeRepository.save(committee);
    }


    @Override
    @Transactional
    public void startCommittee(LocalDate date) {
        Committee committee = committeeRepository.findByDate(date)
                .orElseThrow(() -> new RuntimeException("Committee not found on date: " + date));
        committee.start();
        committeeRepository.save(committee);
    }

    @Override
    @Transactional
    public void closeCommittee(LocalDate date) {
        Committee committee = committeeRepository.findByDate(date)
                .orElseThrow(() -> new RuntimeException("Committee not found on date: " + date));
        committee.close();
        committeeRepository.save(committee);
    }

    @Override
    public List<Committee> findAllCommittees() {
        List<Committee> committeeList = committeeRepository.findAll();
        if(committeeList==null){
            throw new RuntimeException("No Committees found!");
        }
        return committeeList;
    }
}

