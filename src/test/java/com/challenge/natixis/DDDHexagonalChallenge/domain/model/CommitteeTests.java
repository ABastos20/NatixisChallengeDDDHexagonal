package com.challenge.natixis.DDDHexagonalChallenge.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

public class CommitteeTests {

    @Test
    public void givenScheduledCommittee_whenStart_thenStatusIsInProgress() {
        LocalDate date = LocalDate.now().plusDays(1);
        Committee committee = new Committee(date);
        committee.start();

        assertEquals(CommitteeStatus.IN_PROGRESS, committee.getStatus());
    }

    @Test
    public void givenInProgressCommittee_whenClose_thenStatusIsClosed() {
        LocalDate date = LocalDate.now().plusDays(1);
        Committee committee = new Committee(date);
        committee.start();
        committee.close();

        assertEquals(CommitteeStatus.CLOSED, committee.getStatus());
    }

    @Test
    public void givenClosedCommittee_whenStart_thenThrowException() {
        LocalDate date = LocalDate.now().plusDays(1);
        Committee committee = new Committee(date);
        committee.start();
        committee.close();

        assertThrows(IllegalStateException.class, committee::start);
    }

}
