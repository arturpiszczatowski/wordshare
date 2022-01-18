package com.pjatk.wordshare.service;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;


@Service
public class CommentService {

    private final EntityManager entityManager;


    public CommentService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
