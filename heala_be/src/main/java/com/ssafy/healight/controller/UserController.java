package com.ssafy.healight.controller;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ssafy.healight.domain.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.healight.domain.entity.Profile;
import com.ssafy.healight.domain.entity.User;
import com.ssafy.healight.domain.entity.UserBadge;
import com.ssafy.healight.domain.entity.Follow;
import com.ssafy.healight.domain.repository.BadgeRepository;
import com.ssafy.healight.domain.repository.UserBadgeRepository;
import com.ssafy.healight.domain.repository.UserRepository;

import io.swagger.annotations.ApiOperation;


@CrossOrigin("*")
@RequestMapping("/user")
@RestController
public class UserController {

	@Autowired
    UserRepository userRepository;
	
	@Autowired
    BadgeRepository badgeRepository;
	
	@Autowired
    UserBadgeRepository userbadgeRepository;

	@Autowired
	FollowRepository followRepository;

	@ApiOperation(value = "아이디 중복 검사하기.")
	@GetMapping("/checkidentity/{identity}")
	public Object checkId(@PathVariable String identity) {
		Optional<User> userOpt = userRepository.getUserByIdentity(identity);
		if (!userOpt.isPresent()) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "이메일 중복 검사.")
	@GetMapping("/checkemail/{email}")
	public Object checkEmail(@PathVariable String email) {
		Optional<User> userOpt = userRepository.getUserByEmail(email);
		if (!userOpt.isPresent()) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@ApiOperation(value = "회원가입 하기.")
	@PostMapping("/signup")
	public Object signUp(@RequestBody User user) {
		userRepository.save(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "로그인 하기.")
	@PostMapping("/login")
	public Object login(@RequestBody User user) {
		Optional<User> userOpt = userRepository.getUserByIdentityAndPassword(user.getIdentity(), user.getPassword());
		if (userOpt.isPresent()) {
			return new ResponseEntity<Integer>(userOpt.get().getId(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@ApiOperation(value = "프로필 편집 조회하기.")
	@GetMapping("/profile/{user_id}")
	public Object getUser(@PathVariable int user_id) {
		Optional<User> userOpt = Optional.of(userRepository.getUserById(user_id));
		Map<String, Object> response = new HashMap<>();
		if(userOpt.isPresent()) {
			response.put("user", userOpt);
			response.put("badges", userbadgeRepository.getAllWithBadge(user_id));
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}


	@ApiOperation(value = "프로필 편집 반영하기.")
	@PatchMapping("/profile/{user_id}")
	public Object updateUser(@PathVariable int user_id, @RequestBody Profile userRequest) throws Exception {
		User updateUser = userRepository.getUserById(user_id);
		boolean user_update = false;
		boolean badge_update = false;
		User user = userRequest.getUser();
		List<UserBadge> badges = userRequest.getBadges();
		MultipartFile image = userRequest.getImage();
		
		//프로필 사진 변경
		if(!image.isEmpty()) {
			String fileName = image.getOriginalFilename();
			Path path = Paths.get("C:/Users/82103/Desktop/healight/S05P12A605/heala_fe/src/assets" + image.getOriginalFilename());
			image.transferTo(path);
			updateUser.setImage(fileName);
			user_update = true;
		}
		//프로필 사진 외 정보 변경
		if(StringUtils.hasLength(user.getName())) {
			updateUser.setName(user.getName());
			user_update = true;
		}
		if(StringUtils.hasLength(user.getIdentity())) {
			updateUser.setIdentity(user.getIdentity());
			user_update = true;
		}
		if(StringUtils.hasLength(user.getIntroduction())) {
			updateUser.setIntroduction(user.getIntroduction());
			user_update = true;
		}
		//배지 변경
		if(badges.size()!=0) badge_update = true;
		
		if(user_update || badge_update) {
			if(user_update) userRepository.save(updateUser);
			if(badge_update) userbadgeRepository.saveAll(badges);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "팔로우하기.")
	@PostMapping("/follow")
	public Object follow(@RequestBody Follow follow) {
		int userId = follow.getUserId();
		int followId = follow.getFollowId();
		// userId 와 followId 에 해당하는 유저가 있는지 검사
		User user = userRepository.getUserById(userId);
		Optional<User> followUser = userRepository.findById(followId);

		// 있으면 follow 관계 저장
		if(user != null && followUser.isPresent()) {
			Follow newFollow = new Follow(followId, userId);
			followRepository.save(newFollow);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(value = "팔로우 취소하기.")
	@DeleteMapping("/follow")
	public Object cancelFollow(@RequestBody Follow follow) {
		Optional<Follow> oldFollow = followRepository.findFirstByFollowIdAndUserId(follow.getFollowId(), follow.getUserId());

		// 있으면 follow 관계 저장
		if(oldFollow.isPresent()) {
			followRepository.delete(oldFollow.get());
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
}


