package com.ssafy.kiwi.model.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringTokenizer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ssafy.kiwi.authentication.jwt.JwtFilter;
import com.ssafy.kiwi.model.domain.authentication.entity.Member;
import com.ssafy.kiwi.model.domain.entity.Badge;
import com.ssafy.kiwi.model.domain.entity.Hashtag;
import com.ssafy.kiwi.model.domain.entity.Post;
import com.ssafy.kiwi.model.domain.entity.PostHashtag;
import com.ssafy.kiwi.model.domain.entity.UserBadge;
import com.ssafy.kiwi.model.domain.repository.CommunityRepository;
import com.ssafy.kiwi.model.domain.repository.PostRepository;
import com.ssafy.kiwi.model.domain.repository.FollowRepository;
import com.ssafy.kiwi.model.domain.repository.HashtagRepository;
import com.ssafy.kiwi.model.domain.repository.PostHashtagRepository;
import com.ssafy.kiwi.model.domain.repository.ScrapRepository;
import com.ssafy.kiwi.model.domain.repository.UserBadgeRepository;
import com.ssafy.kiwi.model.domain.repository.UserRepository;
import com.ssafy.kiwi.model.dto.PostIp;
import com.ssafy.kiwi.model.dto.PostSimpleOp;
import com.ssafy.kiwi.model.dto.UserFollowOp;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FeedServiceImpl implements FeedService {

	final private PostRepository postRepository;
	final private FollowRepository followRepository;
	final private UserRepository userRepository;
	final private UserBadgeRepository userBadgeRepository;
	final private HashtagRepository hashtagRepository;
	final private PostHashtagRepository postHashtagRepository;
	final private ScrapRepository scrapRepository;
	final private CommunityRepository communityRepository;
	
	// ??? ??????
	@Override
	public void post(PostIp postIp) {
		int postId = postRepository.save(postIp.getPost()).getId();

		// hashtag??? ?????? ?????? hashtag ???????????? insert
		if (postIp.getHashtag() != null) {
			String words = postIp.getHashtag().getWord();
			StringTokenizer st = new StringTokenizer(words, " ");

			int hashtagId;
			while (st.hasMoreTokens()) {
				String word = st.nextToken().replace("#", "");
				Optional<Hashtag> checkHashtag = hashtagRepository.getHashtagByWord(word);
				if (!checkHashtag.isPresent()) {
					Hashtag hashtag = new Hashtag(word);
					hashtagId = hashtagRepository.save(hashtag).getId();
				} else {
					hashtagId = checkHashtag.get().getId();
				}

				// post_hashtag ???????????? ??? id ??? insert
				PostHashtag postHashtag = PostHashtag.builder().postId(postId).hashtagId(hashtagId).build();
				postHashtagRepository.save(postHashtag);
			}
		}
	}

	// ??? ??????
	@Transactional
	@Override
	public boolean delete(int postId, int userId) {
		boolean isPossible = false;
		int checkId = postRepository.getById(postId).getUserId();
		if (checkId == userId) {
			postHashtagRepository.deleteAllByPostId(postId);
			postRepository.deleteById(postId);
			isPossible = true;
		}
		return isPossible;
	}
	
	// ??? ??????
	@Transactional
	@Override
	public boolean update(int postId, PostIp postIp) {
		Post post = postRepository.getById(postId);
		boolean state = true;
		boolean post_update = false;
		
		//??? ?????? ????????? ??????
		if(postIp.getPost() != null) {
			Post newPost = postIp.getPost();			
			if(StringUtils.hasLength(newPost.getImage())) {
				post.setImage(newPost.getImage());
				post_update = true;
			}
			if(StringUtils.hasLength(newPost.getTitle())) {
				post.setTitle(newPost.getTitle());
				post_update = true;
			}
			if(post.getCategory() != newPost.getCategory()) {
				post.setCategory(newPost.getCategory());
				post_update = true;
			}
			if(post.getSubCategory() != newPost.getSubCategory()) {
				post.setSubCategory(newPost.getSubCategory());
				post_update = true;
			}
			if(post.getAccess() != newPost.getAccess()) {
				post.setAccess(newPost.getAccess());
				post_update = true;
			}
			if(StringUtils.hasLength(newPost.getContent())) {
				post.setContent(newPost.getContent());
				post_update = true;
			}
			if(post.isAnonymous() != newPost.isAnonymous()) {
				post.setAnonymous(newPost.isAnonymous());
				post_update = true;
			}
			state = true;
		}
		
		boolean hash_update = false;
		//???????????? ????????? ??????
		if (postIp.getHashtag() != null) {
			//?????? ???????????? ?????? ??????
			postHashtagRepository.deleteAllByPostId(postId);
			//??? ???????????? ??????
			String words = postIp.getHashtag().getWord();
			StringTokenizer st = new StringTokenizer(words, " ");
			int hashtagId;
			while (st.hasMoreTokens()) {
				String word = st.nextToken().replace("#", "");
				Optional<Hashtag> checkHashtag = hashtagRepository.getHashtagByWord(word);
				if (!checkHashtag.isPresent()) {
					Hashtag hashtag = new Hashtag(word);
					hashtagId = hashtagRepository.save(hashtag).getId();
				} 
				else {
					hashtagId = checkHashtag.get().getId();
				}

				// post_hashtag ???????????? ??? id ??? insert
				PostHashtag postHashtag = PostHashtag.builder().postId(postId).hashtagId(hashtagId).build();
				postHashtagRepository.save(postHashtag);
			}
			hash_update = true;
			state = true;
		}
		
		if(post_update || hash_update) {
			Date updateDate = new Date();
			post.setUpdatedAt(updateDate);
			postRepository.save(post);
		}
		return state;
	}
	
	// ????????? ????????? ?????? ?????? ???????????? Post ????????? ?????????(?????? ????????? ????????? ???????????? ??? + ?????? ???????????? ?????? ?????? ?????? ?????? ?????? Post??????)
	@Override
	public List<Post> getPostList(int userId, int page) {
		
		// ?????? ????????? ?????? ?????? ????????? id ??????
		List<Integer> onewayfollowIdList = followRepository.getOnewayFollowIdsByUserId(userId);
		
		// ?????? ?????? ?????? ????????? id ??????
		List<Integer> followForFollowIdList = followRepository.getFollowForFollowByUserId(userId);
		
		// userId ??? ????????? ????????? ?????? ?????? ????????????
		Page<Post> postPage = postRepository.getByFollowAndAccess(onewayfollowIdList, followForFollowIdList, PageRequest.of(page, 10, Sort.by("created_at").descending()));
		List<Post> postList = postPage.getContent();
		return postList;
	}

	
	// ????????? ?????? ?????? ?????? ?????? ????????????(??????)
	@Override
	public Map<String, Object> getMyFeed(int userId) {
		Map<String, Object> map = getFeedInfo(userId);
		return map;
	}

	// ????????? ?????? ?????? ?????? ?????? ????????????(??????)
	@Override
	public Map<String, Object> getUserFeed(int userId, int myId) {
		int num = followRepository.countFollowState(userId, myId);
		if(num==2) num = 1; //????????? ??????
		else num = 0; //??? ??? ??????
		Map<String, Object> map = getFeedInfo(userId);
		return map;
	}
	
	// ????????? ?????? ?????? ?????? ?????? ????????????
	public Map<String, Object> getFeedInfo(int userId){
		Member user = userRepository.getById(userId);
		Map<String, Object> map = new HashMap<>();
		map.put("id", user.getId());
		map.put("identity", user.getIdentity());
		map.put("image", user.getImage());
		map.put("name", user.getName());
		map.put("exp", user.getExp());
		map.put("introduction", user.getIntroduction());
		//?????? ??????
		List<Badge> selectedBadge = new ArrayList<>();
		for(UserBadge ub : userBadgeRepository.getBySelectedAndUserId(userId)) {
			selectedBadge.add(ub.getBadge());
		}
		map.put("badge", selectedBadge);		
		//?????????, ????????? ???
		int following = followRepository.countByUserId(userId);
		int follower = followRepository.countByFollowId(userId);
		map.put("following", following);
		map.put("follower", follower);
		return map;
	}
	
	// ?????? ?????? ????????? ????????????(?????? ??????)
	@Override
	public List<PostSimpleOp> getMyPost(int userId, int category, int page) {
		
		List<PostSimpleOp> list = new ArrayList<>();

		Page<Post> postPage = postRepository.getByUserIdAndCategory(userId, category, PageRequest.of(page, 30, Sort.by("createdAt").descending()));
		List<Post> postList = postPage.getContent();
		
		for (Post p : postList) {
			list.add(new PostSimpleOp(p.getId(), p.getImage()));
		}
		return list;
	}
	
	// ?????? ?????? ????????? ????????????(?????? ??????)
	@Override
	public List<PostSimpleOp> getUserPost(int userId, int myId, int category, int page) {
		
		List<PostSimpleOp> list = new ArrayList<>();

		int num = followRepository.countFollowState(userId, myId);
		if(num==2) num = 1; //????????? ??????
		else num = 0; //??? ??? ??????
		Page<Post> postPage = postRepository.getLimitByUserIdAndCategory(userId, num, category, PageRequest.of(page, 30, Sort.by("createdAt").descending()));
		List<Post> postList = postPage.getContent();
		
		for (Post p : postList) {
			list.add(new PostSimpleOp(p.getId(), p.getImage()));
		}
		return list;
	}
	
	
	//????????? ??????
	@Override
	public List<UserFollowOp> getFollower(int userId, int myId, int page) {
		Page<Integer> followerPage = followRepository.getFollowerIdsByUserId(userId, PageRequest.of(page, 15)); //id
		List<Integer> follower = followerPage.getContent();
		List<UserFollowOp> followers = userRepository.getMemberFollowOpByIds(follower); //?????? ??????
		for(UserFollowOp ufo : followers) { //????????? ?????? ??????
			int id = ufo.getId();
			if(followRepository.countByFollowIdAndUserId(id, myId)==1) ufo.setFollow(true);
		}
		return followers;
	}

	//????????? ??????
	@Override
	public List<UserFollowOp> getFollowing(int userId, int myId, int page) {
		Page<Integer> followingPage = followRepository.getFollowingIdsByUserId(userId, PageRequest.of(page, 15)); //id
		List<Integer> following = followingPage.getContent();
		List<UserFollowOp> followings = userRepository.getMemberFollowOpByIds(following); //?????? ??????
		for(UserFollowOp ufo : followings) { //????????? ?????? ??????
			int id = ufo.getId();
			if(followRepository.countByFollowIdAndUserId(id, myId)==1) ufo.setFollow(true);
		}
		return followings;
	}

	//???????????? ????????? ??????
	@Override
	public Object getScrapList(int userId, int page) {
		List<Integer> scrapNumberList = scrapRepository.getPostIdByUserId(userId);
		Page<Post> scrapPage = communityRepository.getPostByIdIn(scrapNumberList, PageRequest.of(page, 10, Sort.by("createdAt").descending()));
		List<Post> scrapList = scrapPage.getContent();
		return scrapList;
	}

	@Override
	public Object getPostHashtags(int postId) {
		List<Integer> hashtagIds = postHashtagRepository.getHashtagIdsByPostId(postId);
		List<String> res = new ArrayList<String>();
		for (int i = 0, size=hashtagIds.size(); i < size; i++) {
			res.add(hashtagRepository.getHashtagByHashtagId(hashtagIds.get(i)));
		}
		return res;
	}
	
}
