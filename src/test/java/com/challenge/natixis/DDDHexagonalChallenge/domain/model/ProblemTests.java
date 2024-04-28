package com.challenge.natixis.DDDHexagonalChallenge.domain.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProblemTests {

    @Test
    public void givenNewProblem_whenAddComment_thenStatusIsReady() {
        Problem problem = new Problem("Test Problem");
        problem.addComment(new Comment("Initial Comment"));

        assertEquals(ProblemStatus.READY, problem.getStatus());
    }

    @Test
    public void givenReadyProblem_whenClose_thenStatusIsClosed() {
        Problem problem = new Problem("Test Problem");
        problem.addComment(new Comment("Initial Comment"));
        problem.close();

        assertEquals(ProblemStatus.CLOSED, problem.getStatus());
    }
}