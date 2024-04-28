package com.challenge.natixis.DDDHexagonalChallenge.adapter.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class CommentJpa{

    private String content;

}
