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
import com.proconco.dao.UserDao;

/**
 * The Class ReportId.
 */
@Entity
public class ReportId {

	/** The id. */
	@Id
	private Long id; //One To One Report
	
	/** The week. */
	private Integer week;
	
	/** The year. */
	private Integer year;
	
	/** The user profile key. */
	@Load
	private Key<User> userKey;
	
	/** The user profile id. */
	@IgnoreSave
	private Long userId;
	
	/** The user profile. */
	@IgnoreSave
	private User user;
	
	/** The crt tms. */
	@Index
	private Date crtTms;
	/**
	 * Instantiates a new report id.
	 */
	@OnLoad
	public void onLoad() {
		if (userKey != null) {
			UserDao dao = new UserDao();
			user = dao.find(userKey);
			userId = user.getId();
		}
	}
	
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

	public Date getCrtTms() {
		return crtTms;
	}

	public void setCrtTms(Date crtTms) {
		this.crtTms = crtTms;
	}
	
}
