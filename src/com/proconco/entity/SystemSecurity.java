package com.proconco.entity;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * The Class SystemSecurity.
 */
@Entity
public class SystemSecurity {

	/** The id. */
	@Id
	private Long id;
	
	/** The uid min len. */
	private Integer uidMinLen;
	
	/** The pwd min len. */
	private Integer pwdMinLen;
	
	/** The multi logon flg. */
	private String multiLogonFlg;
	
	/** The fail attempt. */
	private Integer failAttempt;
	
	/** The crt uid. */
	private String crtUid;
	
	/** The crt tms. */
	private Date crtTms;
	
	/** The upd uid. */
	private String updUid;
	
	/** The upd tms. */
	private Date updTms;
	
	/**
	 * Instantiates a new system security.
	 */
	public SystemSecurity() {
		
	}
	
	/**
	 * Instantiates a new system security.
	 *
	 * @param uidMinLen the uid min len
	 * @param pwdMinLen the pwd min len
	 * @param multiLogonFlg the multi logon flg
	 * @param failAttempt the fail attempt
	 * @param crtUid the crt uid
	 * @param crtTms the crt tms
	 * @param updUid the upd uid
	 * @param updTms the upd tms
	 */
	public SystemSecurity(Integer uidMinLen, Integer pwdMinLen, String multiLogonFlg, Integer failAttempt,
			String crtUid, Date crtTms, String updUid, Date updTms) {
		this.uidMinLen = uidMinLen;
		this.pwdMinLen = pwdMinLen;
		this.multiLogonFlg = multiLogonFlg;
		this.failAttempt = failAttempt;
		this.crtUid = crtUid;
		this.crtTms = crtTms;
		this.updUid = updUid;
		this.updTms = updTms;
	}
	
	/**
	 * Instantiates a new system security.
	 *
	 * @param id the id
	 * @param uidMinLen the uid min len
	 * @param pwdMinLen the pwd min len
	 * @param multiLogonFlg the multi logon flg
	 * @param failAttempt the fail attempt
	 * @param crtUid the crt uid
	 * @param crtTms the crt tms
	 * @param updUid the upd uid
	 * @param updTms the upd tms
	 */
	public SystemSecurity(Long id, Integer uidMinLen, Integer pwdMinLen, String multiLogonFlg, Integer failAttempt,
			String crtUid, Date crtTms, String updUid, Date updTms) {
		this.id = id;
		this.uidMinLen = uidMinLen;
		this.pwdMinLen = pwdMinLen;
		this.multiLogonFlg = multiLogonFlg;
		this.failAttempt = failAttempt;
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
	 * Gets the uid min len.
	 *
	 * @return the uid min len
	 */
	public final Integer getUidMinLen() {
		return this.uidMinLen;
	}
	
	/**
	 * Sets the uid min len.
	 *
	 * @param uidMinLen the new uid min len
	 */
	public final void setUidMinLen(Integer uidMinLen) {
		this.uidMinLen = uidMinLen;
	}
	
	/**
	 * Gets the pwd min len.
	 *
	 * @return the pwd min len
	 */
	public final Integer getPwdMinLen() {
		return this.pwdMinLen;
	}
	
	/**
	 * Sets the pwd min len.
	 *
	 * @param pwdMinLen the new pwd min len
	 */
	public final void setPwdMinLen(Integer pwdMinLen) {
		this.pwdMinLen = pwdMinLen;
	}
	
	/**
	 * Gets the multi logon flg.
	 *
	 * @return the multi logon flg
	 */
	public final String getMultiLogonFlg() {
		return this.multiLogonFlg;
	}
	
	/**
	 * Sets the multi logon flg.
	 *
	 * @param multiLogonFlg the new multi logon flg
	 */
	public final void setMultiLogonFlg(String multiLogonFlg) {
		this.multiLogonFlg = multiLogonFlg;
	}
	
	/**
	 * Gets the fail attempt.
	 *
	 * @return the fail attempt
	 */
	public final Integer getFailAttempt() {
		return this.failAttempt;
	}
	
	/**
	 * Sets the fail attempt.
	 *
	 * @param failAttempt the new fail attempt
	 */
	public final void setFailAttempt(Integer failAttempt) {
		this.failAttempt = failAttempt;
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
