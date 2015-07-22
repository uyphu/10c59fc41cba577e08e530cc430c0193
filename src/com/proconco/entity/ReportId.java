package com.proconco.entity;

import java.util.Date;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.OnLoad;
import com.proconco.constants.Constants;
import com.proconco.dao.UserDao;

// TODO: Auto-generated Javadoc
/**
 * The Class ReportId.
 */
@Entity
public class ReportId {

	/** The id. */
	@Id
	private Long id; //One To One Report
	
	/** The week. */
	@Index
	private Integer week;
	
	/** The year. */
	@Index
	private Integer year;
	
	/** The user profile key. */
	@Load
	@Index
	private Key<User> userKey;
	
	/** The status. */
	@Index
	private int status;
	
	/** The user profile id. */
	@IgnoreSave
	private Long userId;
	
	/** The username. */
	@IgnoreSave
	private String username;
	
	/** The user profile. */
	@IgnoreSave
	private User user;
	
	/** The yyyyww. */
	@IgnoreSave
	private String yyyyww;
	
	/** The crt tms. */
	@Index
	private Date crtTms;
	
	/** The approval user. */
	private String approvalUser;
	
	/** The approval tms. */
	@Index
	private Date approvalTms;
	
	/**
	 * Instantiates a new report id.
	 */
	@OnLoad
	public void onLoad() {
		if (userKey != null) {
			UserDao dao = new UserDao();
			User user = dao.find(userKey);
			if (user != null) {
				userId = user.getId();
				username = user.getLogin();
			}
		}
		
		if (week < 10) {
			yyyyww = year + "0" + week;
		} else {
			yyyyww = year + week + Constants.STRING_EMPTY;
		}
	}
	
	/**
	 * Instantiates a new report id.
	 */
	public ReportId() {
		
	}
	
	/**
	 * Instantiates a new report id.
	 *
	 * @param week the week
	 * @param year the year
	 * @param userKey the user profile key
	 */
	public ReportId(Integer week, Integer year, Key<User> userKey) {
		this.week = week;
		this.year = year;
		this.userKey = userKey;
	}
	
	/**
	 * Instantiates a new report id.
	 *
	 * @param id the id
	 * @param week the week
	 * @param year the year
	 * @param userKey the user profile key
	 */
	public ReportId(Long id, Integer week, Integer year, Key<User> userKey) {
		this.id = id;
		this.week = week;
		this.year = year;
		this.userKey = userKey;
	}
	
	/**
	 * Instantiates a new report id.
	 *
	 * @param week the week
	 * @param year the year
	 * @param userId the user profile id
	 */
	public ReportId(Integer week, Integer year, Long userId) {
		this.week = week;
		this.year = year;
		this.userId = userId;
	}
	
	/**
	 * Instantiates a new report id.
	 *
	 * @param id the id
	 * @param week the week
	 * @param year the year
	 * @param userId the user profile id
	 */
	public ReportId(Long id, Integer week, Integer year, Long userId) {
		this.id = id;
		this.week = week;
		this.year = year;
		this.userId = userId;
	}

	/**
	 * Load data.
	 */
	@OnLoad
	private void loadData() {
		
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public final Long getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public final void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the week.
	 *
	 * @return the week
	 */
	public final Integer getWeek() {
		return this.week;
	}

	/**
	 * Sets the week.
	 *
	 * @param week the new week
	 */
	public final void setWeek(Integer week) {
		this.week = week;
	}

	/**
	 * Gets the year.
	 *
	 * @return the year
	 */
	public final Integer getYear() {
		return this.year;
	}

	/**
	 * Sets the year.
	 *
	 * @param year the new year
	 */
	public final void setYear(Integer year) {
		this.year = year;
	}

	/**
	 * Gets the user profile key.
	 *
	 * @return the user profile key
	 */
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE) 
	public final Key<User> getUserKey() {
		return this.userKey;
	}

	/**
	 * Sets the user profile key.
	 *
	 * @param userKey the new user profile key
	 */
	public final void setUserKey(Key<User> userKey) {
		this.userKey = userKey;
	}

	/**
	 * Gets the user profile id.
	 *
	 * @return the user profile id
	 */
	public final Long getUserId() {
		return this.userId;
	}

	/**
	 * Sets the user profile id.
	 *
	 * @param userId the new user profile id
	 */
	public final void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * Gets the user profile.
	 *
	 * @return the user profile
	 */
	public final User getUser() {
		return this.user;
	}

	/**
	 * Sets the user profile.
	 *
	 * @param user the new user profile
	 */
	public final void setUser(User user) {
		this.user = user;
	}

	/**
	 * Gets the crt tms.
	 *
	 * @return the crt tms
	 */
	public Date getCrtTms() {
		return crtTms;
	}

	/**
	 * Sets the crt tms.
	 *
	 * @param crtTms the new crt tms
	 */
	public void setCrtTms(Date crtTms) {
		this.crtTms = crtTms;
	}

	/**
	 * Gets the yyyyww.
	 *
	 * @return the yyyyww
	 */
	public final String getYyyyww() {
		return this.yyyyww;
	}

	/**
	 * Sets the yyyyww.
	 *
	 * @param yyyyww the new yyyyww
	 */
	public final void setYyyyww(String yyyyww) {
		this.yyyyww = yyyyww;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public final String getUsername() {
		return this.username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the new username
	 */
	public final void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public final int getStatus() {
		return this.status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public final void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Gets the approval user.
	 *
	 * @return the approval user
	 */
	public final String getApprovalUser() {
		return this.approvalUser;
	}

	/**
	 * Sets the approval user.
	 *
	 * @param approvalUser the new approval user
	 */
	public final void setApprovalUser(String approvalUser) {
		this.approvalUser = approvalUser;
	}

	/**
	 * Gets the approval tms.
	 *
	 * @return the approval tms
	 */
	public final Date getApprovalTms() {
		return this.approvalTms;
	}

	/**
	 * Sets the approval tms.
	 *
	 * @param approvalTms the new approval tms
	 */
	public final void setApprovalTms(Date approvalTms) {
		this.approvalTms = approvalTms;
	}
	
}
