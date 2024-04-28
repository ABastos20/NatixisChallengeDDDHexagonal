package com.challenge.natixis.DDDHexagonalChallenge.application.dto;

import com.challenge.natixis.DDDHexagonalChallenge.domain.model.ProblemStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@NoArgsConstructor
public class ProblemDto {
    @Getter
    @Setter
    private String name;
    @Schema(hidden = true)
    @Getter
    private String status;
    @Schema(hidden = true)
    @Getter
    private List<String> comments;

    public ProblemDto(String name) {
        this.name = name;
    }

    public ProblemDto(String name, String status, List<String> comments) {
        this.name = name;
        this.status = status;
        this.comments = comments;
    }




}
