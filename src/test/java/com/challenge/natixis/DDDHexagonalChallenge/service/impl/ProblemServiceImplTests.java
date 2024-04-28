package com.challenge.natixis.DDDHexagonalChallenge.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.challenge.natixis.DDDHexagonalChallenge.application.service.impl.ProblemServiceImpl;
import com.challenge.natixis.DDDHexagonalChallenge.domain.model.Comment;
import com.challenge.natixis.DDDHexagonalChallenge.domain.model.Committee;
import com.challenge.natixis.DDDHexagonalChallenge.domain.model.Problem;
import com.challenge.natixis.DDDHexagonalChallenge.domain.model.ProblemStatus;
import com.challenge.natixis.DDDHexagonalChallenge.domain.repository.CommitteeRepository;
import com.challenge.natixis.DDDHexagonalChallenge.domain.repository.ProblemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProblemServiceImplTests {

    @Mock
    private ProblemRepository problemRepository;

    @InjectMocks
    private ProblemServiceImpl problemService;

    @Mock
    private CommitteeRepository committeeRepository;

    @Test
    public void whenOpenProblem_thenProblemSaved() {
        String name = "New Problem";
        problemService.openProblem(name);

        ArgumentCaptor<Problem> problemCaptor = ArgumentCaptor.forClass(Problem.class);
        verify(problemRepository).save(problemCaptor.capture());
        assertEquals(name, problemCaptor.getValue().getName());
        assertEquals(ProblemStatus.OPENED, problemCaptor.getValue().getStatus());
    }

    @Test
    public void whenAddComment_thenCommentAdded() {
        String name = "Existing Problem";
        Problem problem = new Problem(name);
        when(problemRepository.findByName(name)).thenReturn(Optional.of(problem));

        String commentContent = "New Comment";
        problemService.addComment(name, commentContent);

        verify(problemRepository).save(problem);
        assertTrue(problem.getComments().stream().anyMatch(c -> c.getContent().equals(commentContent)));
    }

    @Test
    public void whenAddProblemToCommittee_thenProblemAdded() {
        String problemName = "Existing Problem";
        LocalDate date = LocalDate.now().plusDays(1);
        Problem problem = new Problem(problemName);
        problem.addComment(new Comment("Initial Comment")); // To satisfy the READY status requirement
        when(problemRepository.findByName(problemName)).thenReturn(Optional.of(problem));

        Committee committee = new Committee(date);
        when(committeeRepository.findByDate(date)).thenReturn(Optional.of(committee));

        problemService.addProblemToCommittee(problemName, date);

        verify(committeeRepository).save(committee);
        assertTrue(committee.getProblems().contains(problem));
    }

}
