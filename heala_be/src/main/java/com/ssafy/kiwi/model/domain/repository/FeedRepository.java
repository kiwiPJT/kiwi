package com.ssafy.kiwi.model.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.kiwi.model.domain.entity.Post;

public interface FeedRepository extends JpaRepository<Post,Integer>{

}