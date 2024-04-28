package com.challenge.natixis.DDDHexagonalChallenge.adapter.mapper;

import com.challenge.natixis.DDDHexagonalChallenge.adapter.model.CommentJpa;
import com.challenge.natixis.DDDHexagonalChallenge.domain.model.Comment;

import java.util.List;
import java.util.stream.Collectors;

public class CommentJpaMapper {

    public static List<CommentJpa> toEntityList(List<Comment> comments) {
        return comments.stream().map(CommentJpaMapper::toEntity).collect(Collectors.toList());
    }

    public static CommentJpa toEntity(Comment comment) {
        CommentJpa entity = new CommentJpa();
        entity.setContent(comment.getContent());
        return entity;
    }

    public static List<Comment> toDomainList(List<CommentJpa> entities) {
        return entities.stream().map(CommentJpaMapper::toDomainObject).collect(Collectors.toList());
    }

    public static Comment toDomainObject(CommentJpa entity) {
        return new Comment(entity.getContent());
    }
}

