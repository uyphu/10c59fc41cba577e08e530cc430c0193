package com.proconco.entity;

import java.util.Date;
import java.util.List;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.OnLoad;


/**
 * The Class UserProfile.
 */
@Entity
public class UserProfile {

	/** The id. */
	@Id
	private Long id;
	
	/** The email. */
	private String email;
	
	/** The user nm. */
	private String userNm;
	
	/** The pwd. */
	private String pwd;
	
	/** The group key. */
	@Load
	private Key<Group> groupKey;
	
	/** The failed login. */
	private Date failedLogin;
	
	/** The last login. */
	private Date lastLogin;
	
	/** The failed attemp. */
	private Integer failedAttemp;
	
	/** The session id. */
	private String sessionId;
	
	/** The position key. */
	@Load
	private Key<Position> positionKey;
	
	/** The disable flg. */
	private String disableFlg;
	
	/** The disable reason. */
	private String disableReason;
	
	/** The crt uid. */
	private String crtUid;
	
	/** The crt tms. */
	private Date crtTms;
	
	/** The upd uid. */
	private String updUid;
	
	/** The upd tms. */
	private Date updTms;
	
	/** The reports. */
	@IgnoreSave
	private List<Report> reports;
	
	/** The planning weeks. */
	@IgnoreSave
	private List<PlanningWeek> planningWeeks;
	
	/** The group id. */
	@IgnoreSave
	private Long groupId;
	
	/** The position id. */
	@IgnoreSave
	private Long positionId;
	
	@OnLoad
	private void onLoad() {
		
	}
	
	/**
	 * Instantiates a new user profile.
	 */
	public UserProfile() {
		
	}

	/**
	 * Instantiates a new user profile.
	 *
	 * @param email the email
	 * @param userNm the user nm
	 * @param pwd the pwd
	 * @param failedLogin the failed login
	 * @param lastLogin the last login
	 * @param failedAttemp the failed attemp
	 * @param sessionId the session id
	 * @param disableFlg the disable flg
	 * @param disableReason the disable reason
	 * @param crtUid the crt uid
	 * @param crtTms the crt tms
	 * @param updUid the upd uid
	 * @param updTms the upd tms
	 * @param groupId the group id
	 * @param positionId the position id
	 */
	public UserProfile(String email, String userNm, String pwd, Date failedLogin, Date lastLogin, Integer failedAttemp,
			String sessionId, String disableFlg, String disableReason, String crtUid, Date crtTms, String updUid,
			Date updTms, Long groupId, Long positionId) {
		this.email = email;
		this.userNm = userNm;
		this.pwd = pwd;
		this.failedLogin = failedLogin;
		this.lastLogin = lastLogin;
		this.failedAttemp = failedAttemp;
		this.sessionId = sessionId;
		this.disableFlg = disableFlg;
		this.disableReason = disableReason;
		this.crtUid = crtUid;
		this.crtTms = crtTms;
		this.updUid = updUid;
		this.updTms = updTms;
		this.groupId = groupId;
		this.positionId = positionId;
	}

	/**
	 * Instantiates a new user profile.
	 *
	 * @param id the id
	 * @param email the email
	 * @param userNm the user nm
	 * @param pwd the pwd
	 * @param failedLogin the failed login
	 * @param lastLogin the last login
	 * @param failedAttemp the failed attemp
	 * @param sessionId the session id
	 * @param disableFlg the disable flg
	 * @param disableReason the disable reason
	 * @param crtUid the crt uid
	 * @param crtTms the crt tms
	 * @param updUid the upd uid
	 * @param updTms the upd tms
	 * @param groupId the group id
	 * @param positionId the position id
	 */
	public UserProfile(Long id, String email, String userNm, String pwd, Date failedLogin, Date lastLogin,
			Integer failedAttemp, String sessionId, String disableFlg, String disableReason, String crtUid,
			Date crtTms, String updUid, Date updTms, Long groupId, Long positionId) {
		this.id = id;
		this.email = email;
		this.userNm = userNm;
		this.pwd = pwd;
		this.failedLogin = failedLogin;
		this.lastLogin = lastLogin;
		this.failedAttemp = failedAttemp;
		this.sessionId = sessionId;
		this.disableFlg = disableFlg;
		this.disableReason = disableReason;
		this.crtUid = crtUid;
		this.crtTms = crtTms;
		this.updUid = updUid;
		this.updTms = updTms;
		this.groupId = groupId;
		this.positionId = positionId;
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
	 * Gets the email.
	 *
	 * @return the email
	 */
	public final String getEmail() {
		return this.email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public final void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the user nm.
	 *
	 * @return the user nm
	 */
	public final String getUserNm() {
		return this.userNm;
	}

	/**
	 * Sets the user nm.
	 *
	 * @param userNm the new user nm
	 */
	public final void setUserNm(String userNm) {
		this.userNm = userNm;
	}

	/**
	 * Gets the pwd.
	 *
	 * @return the pwd
	 */
	public final String getPwd() {
		return this.pwd;
	}

	/**
	 * Sets the pwd.
	 *
	 * @param pwd the new pwd
	 */
	public final void setPwd(String pwd) {
		this.pwd = pwd;
	}

	/**
	 * Gets the group key.
	 *
	 * @return the group key
	 */
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE) 
	public final Key<Group> getGroupKey() {
		return this.groupKey;
	}

	/**
	 * Sets the group key.
	 *
	 * @param groupKey the new group key
	 */
	public final void setGroupKey(Key<Group> groupKey) {
		this.groupKey = groupKey;
	}

	/**
	 * Gets the failed login.
	 *
	 * @return the failed login
	 */
	public final Date getFailedLogin() {
		return this.failedLogin;
	}

	/**
	 * Sets the failed login.
	 *
	 * @param failedLogin the new failed login
	 */
	public final void setFailedLogin(Date failedLogin) {
		this.failedLogin = failedLogin;
	}

	/**
	 * Gets the last login.
	 *
	 * @return the last login
	 */
	public final Date getLastLogin() {
		return this.lastLogin;
	}

	/**
	 * Sets the last login.
	 *
	 * @param lastLogin the new last login
	 */
	public final void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	/**
	 * Gets the failed attemp.
	 *
	 * @return the failed attemp
	 */
	public final Integer getFailedAttemp() {
		return this.failedAttemp;
	}

	/**
	 * Sets the failed attemp.
	 *
	 * @param failedAttemp the new failed attemp
	 */
	public final void setFailedAttemp(Integer failedAttemp) {
		this.failedAttemp = failedAttemp;
	}

	/**
	 * Gets the session id.
	 *
	 * @return the session id
	 */
	public final String getSessionId() {
		return this.sessionId;
	}

	/**
	 * Sets the session id.
	 *
	 * @param sessionId the new session id
	 */
	public final void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * Gets the position key.
	 *
	 * @return the position key
	 */
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE) 
	public final Key<Position> getPositionKey() {
		return this.positionKey;
	}

	/**
	 * Sets the position key.
	 *
	 * @param positionKey the new position key
	 */
	public final void setPositionKey(Key<Position> positionKey) {
		this.positionKey = positionKey;
	}

	/**
	 * Gets the disable flg.
	 *
	 * @return the disable flg
	 */
	public final String getDisableFlg() {
		return this.disableFlg;
	}

	/**
	 * Sets the disable flg.
	 *
	 * @param disableFlg the new disable flg
	 */
	public final void setDisableFlg(String disableFlg) {
		this.disableFlg = disableFlg;
	}

	/**
	 * Gets the disable reason.
	 *
	 * @return the disable reason
	 */
	public final String getDisableReason() {
		return this.disableReason;
	}

	/**
	 * Sets the disable reason.
	 *
	 * @param disableReason the new disable reason
	 */
	public final void setDisableReason(String disableReason) {
		this.disableReason = disableReason;
	}

	/**
	 * Gets the crt uid.
	 *
	 * @return the crt uid
	 */
	public final String getCrtUid() {
		return this.crtUid;
	}

	/**
	 * Sets the crt uid.
	 *
	 * @param crtUid the new crt uid
	 */
	public final void setCrtUid(String crtUid) {
		this.crtUid = crtUid;
	}

	/**
	 * Gets the crt tms.
	 *
	 * @return the crt tms
	 */
	public final Date getCrtTms() {
		return this.crtTms;
	}

	/**
	 * Sets the crt tms.
	 *
	 * @param crtTms the new crt tms
	 */
	public final void setCrtTms(Date crtTms) {
		this.crtTms = crtTms;
	}

	/**
	 * Gets the upd uid.
	 *
	 * @return the upd uid
	 */
	public final String getUpdUid() {
		return this.updUid;
	}

	/**
	 * Sets the upd uid.
	 *
	 * @param updUid the new upd uid
	 */
	public final void setUpdUid(String updUid) {
		this.updUid = updUid;
	}

	/**
	 * Gets the upd tms.
	 *
	 * @return the upd tms
	 */
	public final Date getUpdTms() {
		return this.updTms;
	}

	/**
	 * Sets the upd tms.
	 *
	 * @param updTms the new upd tms
	 */
	public final void setUpdTms(Date updTms) {
		this.updTms = updTms;
	}

	/**
	 * Gets the reports.
	 *
	 * @return the reports
	 */
	public final List<Report> getReports() {
		return this.reports;
	}

	/**
	 * Sets the reports.
	 *
	 * @param reports the new reports
	 */
	public final void setReports(List<Report> reports) {
		this.reports = reports;
	}

	/**
	 * Gets the planning weeks.
	 *
	 * @return the planning weeks
	 */
	public final List<PlanningWeek> getPlanningWeeks() {
		return this.planningWeeks;
	}

	/**
	 * Sets the planning weeks.
	 *
	 * @param planningWeeks the new planning weeks
	 */
	public final void setPlanningWeeks(List<PlanningWeek> planningWeeks) {
		this.planningWeeks = planningWeeks;
	}

	/**
	 * Gets the group id.
	 *
	 * @return the group id
	 */
	public final Long getGroupId() {
		return this.groupId;
	}

	/**
	 * Sets the group id.
	 *
	 * @param groupId the new group id
	 */
	public final void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	/**
	 * Gets the position id.
	 *
	 * @return the position id
	 */
	public final Long getPositionId() {
		return this.positionId;
	}

	/**
	 * Sets the position id.
	 *
	 * @param positionId the new position id
	 */
	public final void setPositionId(Long positionId) {
		this.positionId = positionId;
	}
	
}
