package com.proconco.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


@Entity
public class UserProfile {

	@Id
	private String id;
	private String userNm;
	private String pwd;
	private String role;
	private Group group;
	private Date failedLogin;
	private Date lastLogin;
	private Integer failedAttemp;
	private String sessionId;
	private Position position;
	private Set<Report> reports = new HashSet<Report>(0);
	private Set<PlanningWeek> planningWeeks = new HashSet<PlanningWeek>(0);
	private String disableFlg;
	private String disableReason;
	private String crtUid;
	private Date crtTms;
	private String updUid;
	private Date updTms;
}
