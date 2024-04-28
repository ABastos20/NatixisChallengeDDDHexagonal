package com.challenge.natixis.DDDHexagonalChallenge.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Committee {
    private LocalDate date;
    private CommitteeStatus status;
    private List<Problem> problems = new ArrayList<>();

    public Committee(LocalDate date) {
        this.date = date;
        this.status = CommitteeStatus.SCHEDULED;
    }

    public Committee(LocalDate date, CommitteeStatus status, List<Problem> problems) {
        this.date = date;
        this.status = status;
        this.problems = problems;
    }

    public void addProblem(Problem problem) {
        if(this.status==CommitteeStatus.CLOSED){
            throw new IllegalStateException("Committee is already closed.");
        }
        problems.add(problem);
    }

    public void start() {
        if (this.status == CommitteeStatus.SCHEDULED) {
            this.status = CommitteeStatus.IN_PROGRESS;
        } else if (this.status == CommitteeStatus.IN_PROGRESS) {
            throw new IllegalStateException("Committee is already in progress.");
        } else {
            throw new IllegalStateException("Committee is already closed.");
        }
    }

    public void close() {
        if (this.status == CommitteeStatus.IN_PROGRESS) {
            this.status = CommitteeStatus.CLOSED;
            for (Problem problem : problems) {
                problem.close();
            }
        } else {
            throw new IllegalStateException("Committee is not in progress.");
        }
    }

}
