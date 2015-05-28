package com.proconco.entity;

import java.util.Calendar;
import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

/**
 * The Class Position.
 */
@Entity
public class Position {

	/** The id. */
	@Id
	private Long id;

	/** The post name. */
	@Index
	private String postName;
	
	/** The del flag. */
	private String delFlag;

	/** The crt uid. */
	private String crtUid;

	/** The crt tms. */
	private Date crtTms;

	/** The upd uid. */
	private String updUid;

	/** The upd tms. */
	private Date updTms;

	/**
	 * Instantiates a new position.
	 */
	public Position() {

	}

	/**
	 * Instantiates a new position.
	 * 
	 * @param postName
	 *            the post name
	 * @param delFlag
	 *            the del flag
	 * @param crtUid
	 *            the crt uid
	 * @param crtTms
	 *            the crt tms
	 * @param updUid
	 *            the upd uid
	 * @param updTms
	 *            the upd tms
	 */
	public Position(String postName, String delFlag, String crtUid, Date crtTms, String updUid, Date updTms) {
		this.postName = postName;
		this.delFlag = delFlag;
		this.crtUid = crtUid;
		this.crtTms = crtTms;
		this.updUid = updUid;
		this.updTms = updTms;
	}
	
	/**
	 * Instantiates a new position.
	 *
	 * @param postName the post name
	 * @param delFlag the del flag
	 * @param crtUid the crt uid
	 */
	public Position(String postName, String delFlag, String crtUid) {
		this.postName = postName;
		this.delFlag = delFlag;
		this.crtUid = crtUid;
		this.crtTms = Calendar.getInstance().getTime();
	}

	/**
	 * Instantiates a new position.
	 * 
	 * @param id
	 *            the id
	 * @param postName
	 *            the post name
	 * @param delFlag
	 *            the del flag
	 * @param crtUid
	 *            the crt uid
	 * @param crtTms
	 *            the crt tms
	 * @param updUid
	 *            the upd uid
	 * @param updTms
	 *            the upd tms
	 */
	public Position(Long id, String postName, String delFlag, String crtUid, Date crtTms, String updUid, Date updTms) {
		this.id = id;
		this.postName = postName;
		this.delFlag = delFlag;
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
	 * @param id
	 *            the new id
	 */
	public final void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the post name.
	 * 
	 * @return the post name
	 */
	public final String getPostName() {
		return this.postName;
	}

	/**
	 * Sets the post name.
	 * 
	 * @param postName
	 *            the new post name
	 */
	public final void setPostName(String postName) {
		this.postName = postName;
	}

	/**
	 * Gets the del flag.
	 * 
	 * @return the del flag
	 */
	public final String getDelFlag() {
		return this.delFlag;
	}

	/**
	 * Sets the del flag.
	 * 
	 * @param delFlag
	 *            the new del flag
	 */
	public final void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
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
	 * @param crtUid
	 *            the new crt uid
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
	 * @param crtTms
	 *            the new crt tms
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
	 * @param updUid
	 *            the new upd uid
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
	 * @param updTms
	 *            the new upd tms
	 */
	public final void setUpdTms(Date updTms) {
		this.updTms = updTms;
	}

}
