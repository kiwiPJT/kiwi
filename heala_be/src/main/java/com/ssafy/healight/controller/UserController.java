package com.ssafy.healight.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.healight.domain.entity.User;
import com.ssafy.healight.domain.repository.UserRepository;
//import com.ssafy.healight.model.service.UserService;

@CrossOrigin("*")
@RequestMapping("/user")
@RestController

public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	// 아이디 중복 검사
	@GetMapping("/checkidentity/{identity}")
	public Object checkId(@PathVariable String identity) {
		Optional<User> userOpt = userRepository.getUserByIdentity(identity);
		if (!userOpt.isPresent()) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// 이메일 중복 검사
	@GetMapping("/checkemail/{email}")
	public Object checkEmail(@PathVariable String email) {
		Optional<User> userOpt = userRepository.getUserByEmail(email);
		if (!userOpt.isPresent()) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	// 회원 가입
	@PostMapping("/signup")
	public Object signUp(@RequestBody User user) {
		userRepository.save(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	// 로그인
	@PostMapping("/login")
	public Object login(@RequestBody User user) {
		Optional<User> userOpt = userRepository.getUserByIdentityAndPassword(user.getIdentity(), user.getPassword());
		if (userOpt.isPresent()) {
			return new ResponseEntity<Integer>(userOpt.get().getId(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
}


