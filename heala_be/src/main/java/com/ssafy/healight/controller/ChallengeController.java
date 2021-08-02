package com.ssafy.healight.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.healight.domain.entity.MyChallengeList;
import com.ssafy.healight.domain.entity.WithInput;
import com.ssafy.healight.service.KiwiChallengeService;
import com.ssafy.healight.service.WithChallengeService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/challenge")
@RestController
public class ChallengeController {
	
	final private WithChallengeService withChallengeService;
	final private KiwiChallengeService kiwiChallengeService;
	
	
	@ApiOperation(value = "챌린지 만들기.")
	@PostMapping("/post")
	public Object post(@RequestBody WithInput withInput) {
		withChallengeService.post(withInput);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "키위 챌린지 목록 조회하기.")
	@GetMapping("/kiwi")
	public Object getKiwiChallenge() {
		return kiwiChallengeService.getKiwiChallenge();
	}
	
	@ApiOperation(value = "키위 챌린지 상세 미션 조회하기.")
	@GetMapping("/kiwi/{category}/{user_id}")
	public Object getKiwiMission(@PathVariable int category, @PathVariable int user_id) {
		return kiwiChallengeService.getKiwiMission(category, user_id);
	}
	
	@ApiOperation(value = "마이 챌린지 목록 조회하기.")
	@GetMapping("/my/{user_id}")
	public Object getMyChallenge(@PathVariable int user_id) {
		List<Integer> myChallengeIdList = withChallengeService.getByUserid(user_id);
		List<MyChallengeList> myChallengeList = new ArrayList<>();
		for (int i = 0; i < myChallengeIdList.size(); i++) {
			int n = myChallengeIdList.get(i);
			MyChallengeList mcl = new MyChallengeList();
			mcl.setWithChallenge(withChallengeService.getByChallengeId(n));
			myChallengeList.add(mcl);
		}
		return myChallengeList;
	}
	
}
