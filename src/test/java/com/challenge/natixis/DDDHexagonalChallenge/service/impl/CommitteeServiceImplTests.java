package com.challenge.natixis.DDDHexagonalChallenge.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.challenge.natixis.DDDHexagonalChallenge.application.service.impl.CommitteeServiceImpl;
import com.challenge.natixis.DDDHexagonalChallenge.domain.model.Committee;
import com.challenge.natixis.DDDHexagonalChallenge.domain.model.CommitteeStatus;
import com.challenge.natixis.DDDHexagonalChallenge.domain.repository.CommitteeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CommitteeServiceImplTests {

    @Mock
    private CommitteeRepository committeeRepository;

    @InjectMocks
    private CommitteeServiceImpl committeeService;

    @Test
    public void whenScheduleCommittee_thenSuccess() {
        LocalDate date = LocalDate.now().plusDays(1);
        Committee committee = new Committee(date);

        committeeService.scheduleCommittee(date);

        ArgumentCaptor<Committee> committeeCaptor = ArgumentCaptor.forClass(Committee.class);
        verify(committeeRepository).save(committeeCaptor.capture());
        assertEquals(date, committeeCaptor.getValue().getDate());
        assertEquals(CommitteeStatus.SCHEDULED, committeeCaptor.getValue().getStatus());
    }

    @Test
    public void whenStartCommittee_thenStatusInProgress() {
        LocalDate date = LocalDate.now().plusDays(1);
        Committee committee = new Committee(date);
        when(committeeRepository.findByDate(date)).thenReturn(Optional.of(committee));

        committeeService.startCommittee(date);

        assertEquals(CommitteeStatus.IN_PROGRESS, committee.getStatus());
        verify(committeeRepository).save(committee);
    }

    @Test
    public void whenCloseCommittee_thenStatusClosed() {
        LocalDate date = LocalDate.now().plusDays(1);
        Committee committee = new Committee(date);
        committee.start(); // Set status to IN_PROGRESS
        when(committeeRepository.findByDate(date)).thenReturn(Optional.of(committee));

        committeeService.closeCommittee(date);

        assertEquals(CommitteeStatus.CLOSED, committee.getStatus());
        verify(committeeRepository).save(committee);
    }

}
