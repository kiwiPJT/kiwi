package com.ssafy.kiwi.model.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.kiwi.model.domain.entity.WithChallengeHashtag;


public interface WithChallengeHashtagRepository extends JpaRepository<WithChallengeHashtag, Integer>{
	
	@Query(value = "select * from with_challenge_hashtag wch left outer join challenge_hashtag ch on wch.challenge_hashtag_id = ch.id where wch.with_challenge_id = :withChallengeId", nativeQuery = true)
	List<WithChallengeHashtag> getAllWithChallengeHashtag(int withChallengeId);
	
}
