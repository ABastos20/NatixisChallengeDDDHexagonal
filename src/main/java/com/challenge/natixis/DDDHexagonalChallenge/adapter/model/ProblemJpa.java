package com.challenge.natixis.DDDHexagonalChallenge.adapter.model;

import com.challenge.natixis.DDDHexagonalChallenge.domain.model.ProblemStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProblemJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private ProblemStatus status;

    @ElementCollection
    private List<CommentJpa> comments = new ArrayList<>();

}
