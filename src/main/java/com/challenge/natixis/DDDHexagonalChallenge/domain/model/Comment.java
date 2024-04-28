package com.challenge.natixis.DDDHexagonalChallenge.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment {

    private String content;

    public Comment(String content) {
        this.content = content;
    }
}
