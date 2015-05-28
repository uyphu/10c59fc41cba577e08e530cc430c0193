package com.proconco.entity;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * The Class SessionInfo.
 */
@Entity
public class SessionInfo {

	/** The id. */
	@Id
	private Long id;
	
	/** The user id. */
	private Long userId;
	
	/** The user nm. */
	private String userNm;
	
	/** The lst suc login. */
	private Date lstSucLogin;
	
	/** The crt uid. */
	private String crtUid;
	
	/** The crt tms. */
	private Date crtTms;
	
	/** The upd uid. */
	private String updUid;
	
	/** The upd tms. */
	private Date updTms;

	/**
	 * Instantiates a new session info.
	 */
	public SessionInfo() {
		
	}

	/**
	 * Instantiates a new session info.
	 *
	 * @param userId the user profile id
	 * @param userNm the user nm
	 * @param lstSucLogin the lst suc login
	 * @param crtUid the crt uid
	 * @param crtTms the crt tms
	 * @param updUid the upd uid
	 * @param updTms the upd tms
	 */
	public SessionInfo(Long userId, String userNm, Date lstSucLogin, String crtUid, Date crtTms,
			String updUid, Date updTms) {
		this.userId = userId;
		this.userNm = userNm;
		this.lstSucLogin = lstSucLogin;
		this.crtUid = crtUid;
		this.crtTms = crtTms;
		this.updUid = updUid;
		this.updTms = updTms;
	}
	
	/**
	 * Instantiates a new session info.
	 *
	 * @param id the id
	 * @param userId the user profile id
	 * @param userNm the user nm
	 * @param lstSucLogin the lst suc login
	 * @param crtUid the crt uid
	 * @param crtTms the crt tms
	 * @param updUid the upd uid
	 * @param updTms the upd tms
	 */
	public SessionInfo(Long id, Long userId, String userNm, Date lstSucLogin, String crtUid, Date crtTms,
			String updUid, Date updTms) {
		this.id = id;
		this.userId = userId;
		this.userNm = userNm;
		this.lstSucLogin = lstSucLogin;
		this.crtUid = crtUid;
		this.crtTms = crtTms;
		this.updUid = updUid;
		this.updTms = updTms;
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
	 * Gets the lst suc login.
	 *
	 * @return the lst suc login
	 */
	public final Date getLstSucLogin() {
		return this.lstSucLogin;
	}

	/**
	 * Sets the lst suc login.
	 *
	 * @param lstSucLogin the new lst suc login
	 */
	public final void setLstSucLogin(Date lstSucLogin) {
		this.lstSucLogin = lstSucLogin;
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

}