package com.challenge.natixis.DDDHexagonalChallenge.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Problem {
    private String name;
    private ProblemStatus status;
    private List<Comment> comments = new ArrayList<>();

    public Problem(String name) {
        this.name = name;
        this.status = ProblemStatus.OPENED;
    }

    public Problem(String name, ProblemStatus status, List<Comment> comments){
        this.name = name;
        this.status = status;
        this.comments = comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        if (status == ProblemStatus.OPENED) {
            status = ProblemStatus.READY;
        }
    }

    public void close() {
        status = ProblemStatus.CLOSED;
    }

}
