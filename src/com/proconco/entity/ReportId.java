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
	private Key<UserProfile> userProfileKey;
	
	/** The user profile id. */
	@IgnoreSave
	private Long userProfileId;
	
	/** The user profile. */
	@IgnoreSave
	private UserProfile userProfile;
	/**
	 * Instantiates a new report id.
	 */
	@OnLoad
	public void onLoad() {
		if (userProfileKey != null) {
			UserProfileDao dao = new UserProfileDao();
			userProfile = dao.find(userProfileKey);
		}
	}
	
	public ReportId() {
		
	}
	
	/**
	 * Instantiates a new report id.
	 *
	 * @param week the week
	 * @param year the year
	 * @param userProfileKey the user profile key
	 */
	public ReportId(Integer week, Integer year, Key<UserProfile> userProfileKey) {
		this.week = week;
		this.year = year;
		this.userProfileKey = userProfileKey;
	}
	
	/**
	 * Instantiates a new report id.
	 *
	 * @param id the id
	 * @param week the week
	 * @param year the year
	 * @param userProfileKey the user profile key
	 */
	public ReportId(Long id, Integer week, Integer year, Key<UserProfile> userProfileKey) {
		this.id = id;
		this.week = week;
		this.year = year;
		this.userProfileKey = userProfileKey;
	}
	
	/**
	 * Instantiates a new report id.
	 *
	 * @param week the week
	 * @param year the year
	 * @param userProfileId the user profile id
	 */
	public ReportId(Integer week, Integer year, Long userProfileId) {
		this.week = week;
		this.year = year;
		this.userProfileId = userProfileId;
	}
	
	/**
	 * Instantiates a new report id.
	 *
	 * @param id the id
	 * @param week the week
	 * @param year the year
	 * @param userProfileId the user profile id
	 */
	public ReportId(Long id, Integer week, Integer year, Long userProfileId) {
		this.id = id;
		this.week = week;
		this.year = year;
		this.userProfileId = userProfileId;
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

	/**
	 * Gets the user profile.
	 *
	 * @return the user profile
	 */
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
