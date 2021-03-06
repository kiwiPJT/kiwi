package com.ssafy.kiwi.model.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.kiwi.model.domain.entity.KiwiUser;

public interface KiwiUserRepository extends JpaRepository<KiwiUser, Integer>{

	KiwiUser getByUserIdAndMissionId(int userId, int missionId);

//	Object getKiwiUserByCategoryAndUserId(int category, int userId);

}
