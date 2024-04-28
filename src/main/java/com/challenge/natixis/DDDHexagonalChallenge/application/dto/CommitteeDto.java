package com.challenge.natixis.DDDHexagonalChallenge.application.dto;

import com.challenge.natixis.DDDHexagonalChallenge.domain.model.CommitteeStatus;
import com.challenge.natixis.DDDHexagonalChallenge.domain.model.Problem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
public class CommitteeDto {
    @Getter
    @Setter
    private LocalDate date;
    @Schema(hidden = true)
    @Getter
    private String status;
    @Schema(hidden = true)
    @Getter
    private List<String> problems;

    public CommitteeDto(LocalDate date) {
        this.date = date;
    }

    public CommitteeDto(LocalDate date, String status, List<String> problems) {
        this.date = date;
        this.status = status;
        this.problems = problems;
    }

}