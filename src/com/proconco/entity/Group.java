package com.proconco.entity;

import java.util.Date;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.OnLoad;

/**
 * The Class Group.
 */
@Entity
public class Group {

	/** The id. */
	@Id
	private Long id;
	
	/** The grp name. */
	private String grpName;
	
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

	/** The user profiles. */
	@Ignore
	@IgnoreSave
	private List<UserProfile> userProfiles;
	
	@OnLoad
    private void loadData() {
    	
    }

	/**
	 * Instantiates a new group.
	 */
	public Group() {
		
	}
	
	/**
	 * Instantiates a new group.
	 *
	 * @param grpName the grp name
	 * @param delFlag the del flag
	 * @param crtUid the crt uid
	 * @param crtTms the crt tms
	 * @param updUid the upd uid
	 * @param updTms the upd tms
	 */
	public Group(String grpName, String delFlag, String crtUid, Date crtTms, String updUid, Date updTms) {
		this.grpName = grpName;
		this.delFlag = delFlag;
		this.crtUid = crtUid;
		this.crtTms = crtTms;
		this.updUid = updUid;
		this.updTms = updTms;
	}
	
	/**
	 * Instantiates a new group.
	 *
	 * @param id the id
	 * @param grpName the grp name
	 * @param delFlag the del flag
	 * @param crtUid the crt uid
	 * @param crtTms the crt tms
	 * @param updUid the upd uid
	 * @param updTms the upd tms
	 */
	public Group(Long id, String grpName, String delFlag, String crtUid, Date crtTms, String updUid, Date updTms) {
		this.id = id;
		this.grpName = grpName;
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
	 * @param id the new id
	 */
	public final void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Gets the grp name.
	 *
	 * @return the grp name
	 */
	public final String getGrpName() {
		return this.grpName;
	}
	
	/**
	 * Sets the grp name.
	 *
	 * @param grpName the new grp name
	 */
	public final void setGrpName(String grpName) {
		this.grpName = grpName;
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
	 * @param delFlag the new del flag
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
	 * Gets the user profiles.
	 *
	 * @return the user profiles
	 */
	public final List<UserProfile> getUserProfiles() {
		return this.userProfiles;
	}
	
	/**
	 * Sets the user profiles.
	 *
	 * @param userProfiles the new user profiles
	 */
	public final void setUserProfiles(List<UserProfile> userProfiles) {
		this.userProfiles = userProfiles;
	}
	
}
