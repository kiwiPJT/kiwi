package com.ssafy.kiwi.model.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssafy.kiwi.model.domain.entity.Post;

public interface CommunityRepository extends JpaRepository<Post,Integer>{

	// 전체 BEST 글 목록 가져오기
	Page<Post> getPostByAccessAndLikesGreaterThan(int access, int criterion, Pageable pageable);
	
	Page<Post> getPostByAccess(int access, Pageable pageable);
	Page<Post> getPostByCategoryAndSubCategoryAndAccess(int category, int subCategory, int access, Pageable pageable);
	Page<Post> getPostByCategoryAndAccessAndLikesGreaterThan(int category, int access, int criterion, Pageable pageable);
	
	long countByCategoryAndUserId(int category, int userId);
	long countByCategoryAndUserIdAndSubCategory(int category, int userId, int subCategory);
	
	@Query(value = "select max(p.likes) from Post p where p.category = :category and p.userId = :userId")
	int getMaxLikeByCategoryAndUserId(int category, int userId);
	@Query(value = "select sum(p.likes) from Post p where p.category = :category and p.userId = :userId")
	int getSumLikeByCategoryAndUserId(int category, int userId);
	
	@Query(value = "select * from post p WHERE p.title LIKE %:word% OR p.content LIKE %:word% and access = :access", nativeQuery = true)
	List<Post> getAllPostByWordAndAccess(String word, int access);
	
	//스트랩한 게시글 목록 가져오기
	Page<Post> getPostByIdIn(List<Integer> id, Pageable pageable);

	// 전체 글 중 단어를 제목or내용or해시태그에 포함하는 글 목록 반환하기
	@Query(value = "SELECT * from post p WHERE (p.title LIKE %:word%) OR (p.content LIKE %:word%) "
			+ "OR (p.id in (SELECT post_id FROM post_hashtag ph JOIN hashtag h ON ph.hashtag_id = h.id WHERE h.word LIKE %:word%))"
			+ "AND access = :access", nativeQuery = true)
	Page<Post> getAllPostByWordAndAccess(String word, int access, Pageable pageable);
	
	// 베스트 글 중 단어를 제목or내용or해시태그에 포함하는 글 목록 반환하기
	@Query(value = "SELECT * from post p WHERE ((p.title LIKE %:word%) OR (p.content LIKE %:word%) "
			+ "OR (p.id in (SELECT post_id FROM post_hashtag ph JOIN hashtag h ON ph.hashtag_id = h.id WHERE h.word LIKE %:word%)))"
			+ "AND access = :access AND category = :category AND likes > :CRITERION", nativeQuery = true)
	Page<Post> getBestCategoryPostByWord(int category, int access, int CRITERION, String word, Pageable pageable);
	
	// 카테고리 글 중 단어를 제목or내용or해시태그에 포함하는 글 목록 반환하기
	@Query(value = "SELECT * from post p WHERE ((p.title LIKE %:word%) OR (p.content LIKE %:word%) "
			+ "OR (p.id in (SELECT post_id FROM post_hashtag ph JOIN hashtag h ON ph.hashtag_id = h.id WHERE h.word LIKE %:word%)))"
			+ "AND access = :access AND category = :category AND sub_category = :subCategory", nativeQuery = true)
	Page<Post> getCategoryPostByWord(int category, int subCategory, int access, String word, Pageable pageable);

}
