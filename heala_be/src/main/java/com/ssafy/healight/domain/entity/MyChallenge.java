package com.ssafy.healight.domain.entity;

import javax.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Getter
@Table(name="my_challenge")
public class MyChallenge {
	
	@Id
	private int id;
	
	private int user_id;
	private int with_challenge_id;
}
