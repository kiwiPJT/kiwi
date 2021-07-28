package com.ssafy.healight.domain.entity;

import javax.persistence.*;
import lombok.*;


@Getter
@Entity
@Table(name = "user_badge")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UserBadge {
	@Id
	private int id;
	
	private boolean selected;
	private int user_id;

	//다대일 : badge의 id와 연결
	@ManyToOne
	@JoinColumn(name="badge_id")
	private Badge badge;
	
}
