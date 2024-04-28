package com.challenge.natixis.DDDHexagonalChallenge.adapter.model;

import com.challenge.natixis.DDDHexagonalChallenge.domain.model.CommitteeStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CommitteeJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private LocalDate date;

    private CommitteeStatus status;

    @OneToMany()
    @JoinColumn(name = "problem_id", referencedColumnName = "id")
    private List<ProblemJpa> problems = new ArrayList<>();


}
