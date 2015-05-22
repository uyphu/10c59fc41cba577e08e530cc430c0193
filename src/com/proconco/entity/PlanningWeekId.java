package com.proconco.entity;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.OnLoad;
import com.proconco.dao.UserProfileDao;

// TODO: Auto-generated Javadoc
/**
 * The Class PlanningWeekId.
 */
@Entity
public class PlanningWeekId {

	/** The id. */
	@Id
	private Long id; //One to One PlanningWeek
	
	/** The week. */
	private Integer week;
	
	/** The year. */
	private Integer year;
	
	/** The user profile. */
	@Load
	private Key<UserProfile> userProfileKey;
	
	/** The user profile id. */
	@IgnoreSave
	private Long userProfileId;
	
	/** The user profile. */
	@IgnoreSave
	private UserProfile userProfile;
	
	/**
	 * On load.
	 */
	@OnLoad
	private void onLoad() {
		if (userProfileKey != null) {
			UserProfileDao dao = new UserProfileDao();
			userProfile = dao.find(userProfileKey);
		}
	}
	
	/**
	 * Instantiates a new planning week id.
	 */
	public PlanningWeekId() {
		
	}

	/**
	 * Instantiates a new planning week id.
	 *
	 * @param week the week
	 * @param year the year
	 * @param userProfileKey the user profile key
	 */
	public PlanningWeekId(Integer week, Integer year, Key<UserProfile> userProfileKey) {
		this.week = week;
		this.year = year;
		this.userProfileKey = userProfileKey;
	}

	/**
	 * Instantiates a new planning week id.
	 *
	 * @param id the id
	 * @param week the week
	 * @param year the year
	 * @param userProfileKey the user profile key
	 */
	public PlanningWeekId(Long id, Integer week, Integer year, Key<UserProfile> userProfileKey) {
		this.id = id;
		this.week = week;
		this.year = year;
		this.userProfileKey = userProfileKey;
	}
	
	/**
	 * Instantiates a new planning week id.
	 *
	 * @param week the week
	 * @param year the year
	 * @param userProfileId the user profile id
	 */
	public PlanningWeekId(Integer week, Integer year, Long userProfileId) {
		this.week = week;
		this.year = year;
		this.userProfileId = userProfileId;
	}
	
	/**
	 * Instantiates a new planning week id.
	 *
	 * @param id the id
	 * @param week the week
	 * @param year the year
	 * @param userProfileId the user profile id
	 */
	public PlanningWeekId(Long id, Integer week, Integer year, Long userProfileId) {
		this.id = id;
		this.week = week;
		this.year = year;
		this.userProfileId = userProfileId;
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
	public final Key<UserProfile> getUserProfileKey() {
		return this.userProfileKey;
	}

	/**
	 * Sets the user profile key.
	 *
	 * @param userProfileKey the new user profile key
	 */
	public final void setUserProfileKey(Key<UserProfile> userProfileKey) {
		this.userProfileKey = userProfileKey;
	}

	/**
	 * Gets the user profile id.
	 *
	 * @return the user profile id
	 */
	public final Long getUserProfileId() {
		return this.userProfileId;
	}

	/**
	 * Sets the user profile id.
	 *
	 * @param userProfileId the new user profile id
	 */
	public final void setUserProfileId(Long userProfileId) {
		this.userProfileId = userProfileId;
	}
	
	public final UserProfile getUserProfile() {
		return this.userProfile;
	}

	/**
	 * Sets the user profile.
	 *
	 * @param userProfile the new user profile
	 */
	public final void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

}
