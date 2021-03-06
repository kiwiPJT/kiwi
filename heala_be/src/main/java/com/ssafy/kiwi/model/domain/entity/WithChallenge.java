package com.ssafy.kiwi.model.domain.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.sql.Date;

@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "WithChallenge")
@Builder
@Getter
@Setter
@ToString
@Table(name = "with_challenge")
public class WithChallenge {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String image;
	private String title;
	private int category;
	
	@Column(name = "start_date")
	private Date startDate;
	
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "certify_info")
	private String certifyInfo;
	
	private String introduction;
	
	@Column(name = "user_id")
	private int userId;
	
	@Column(name = "certify_day")
	private int certifyDay;
	
	@Column(name = "certify_week")
	private int certifyWeek;
	
	@Column(name = "kiwi_point")
	private int kiwiPoint;
	
	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP") 
	public Date createdAt;
	
	
	public WithChallenge(String image, String title, int category, Date start_date, Date end_date,
			String certify_info, String introduction, int user_id) {
		super();
		this.image = image;
		this.title = title;
		this.category = category;
		this.startDate = start_date;
		this.endDate = end_date;
		this.certifyInfo = certify_info;
		this.introduction = introduction;
		this.userId = user_id;
	}


	public WithChallenge(int id, String image, String title, int category, Date startDate, Date endDate, int userId) {
		super();
		this.id = id;
		this.image = image;
		this.title = title;
		this.category = category;
		this.startDate = startDate;
		this.endDate = endDate;
		this.userId = userId;
	}
	
    /**
     * insert ????????? (persist ?????????) ????????????.
     * */
    @PrePersist
    public void prePersist() {
        this.certifyDay = 7;
        this.certifyWeek = 1;
    }
	
	
}