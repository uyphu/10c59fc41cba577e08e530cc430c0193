package com.proconco.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.OnLoad;
import com.proconco.dao.AuthorityDao;
import com.proconco.dao.GroupDao;
import com.proconco.dao.PositionDao;


/**
 * The Class UserProfile.
 */
@Entity
public class User {

	/** The id. */
	@Id
	private Long id;
	
    /** The login. */
	@Index
    private String login;

    /** The password. */
	@Index
    private String password;

    /** The first name. */
	@Index
    private String firstName;

    /** The last name. */
	@Index
    private String lastName;

    /** The email. */
    @Index
    private String email;

    /** The activated. */
    @Index
    private boolean activated = false;

    /** The lang key. */
    private String langKey;

    /** The activation key. */
    private String activationKey;

    /** The reset key. */
    private String resetKey;

    /** The reset date. */
    private Date resetDate = null;
    
    /** The create date. */
    private Date createDate;
    
    /** The authority keys. */
    @Load
    private List<Key<Authority>> authorityKeys;

	/** The group key. */
	@Load
	@Index
	private Key<Group> groupKey;
	
	/** The position key. */
	@Load
	@Index
	private Key<Position> positionKey;
	
	/** The reports. */
	@IgnoreSave
	private List<Report> reports;
	
	/** The planning weeks. */
	@IgnoreSave
	private List<PlanningWeek> planningWeeks;
	
	/** The authority ids. */
	@IgnoreSave
	private List<Long> authorityIds;
	
	/** The authorities. */
	@IgnoreSave
	private List<Authority> authorities;
	
	/** The roles. */
	@IgnoreSave
	private List<String> roles;
	
	/** The group id. */
	@IgnoreSave
	private Long groupId;
	
	/** The position id. */
	@IgnoreSave
	private Long positionId;
	
	/** The group. */
	@IgnoreSave
	private Group group;
	
	/** The position. */
	@IgnoreSave
	private Position position;
	
	/**
	 * On load.
	 */
	@OnLoad
	private void onLoad() {
		if (groupKey != null) {
			GroupDao dao = new GroupDao();
			group = dao.find(groupKey.getId());
			groupId = groupKey.getId();
		}
		
		if (positionKey != null) {
			PositionDao dao = new PositionDao();
			position = dao.find(positionKey.getId());
			positionId = positionKey.getId();
		}
		
		if (authorityKeys != null) {
			roles = new ArrayList<String>();
			for (Key<Authority> key : authorityKeys) {
				AuthorityDao dao = new AuthorityDao();
				Authority authority = dao.find(key.getId());
				if (authority != null) {
					roles.add(authority.getName());
				}
			}
		}
	}
	
	/**
	 * Instantiates a new user profile.
	 */
	public User() {
		
	}

	/**
	 * Instantiates a new user.
	 *
	 * @param id the id
	 * @param login the login
	 * @param password the password
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param email the email
	 * @param langKey the lang key
	 */
	public User(Long id, String login, String password, String firstName, String lastName, String email, String langKey) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.langKey = langKey;
	}
	
	/**
	 * Instantiates a new user.
	 *
	 * @param login the login
	 * @param password the password
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param email the email
	 * @param langKey the lang key
	 */
	public User(String login, String password, String firstName, String lastName, String email, String langKey) {
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.langKey = langKey;
	}

	/**
	 * Instantiates a new user.
	 *
	 * @param id the id
	 * @param login the login
	 * @param password the password
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param email the email
	 * @param langKey the lang key
	 * @param authorityIds the authority ids
	 * @param groupId the group id
	 * @param positionId the position id
	 */
	public User(Long id, String login, String password, String firstName, String lastName, String email,
			String langKey, List<Long> authorityIds, Long groupId, Long positionId) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.langKey = langKey;
		this.authorityIds = authorityIds;
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
	 * Gets the login.
	 *
	 * @return the login
	 */
	public final String getLogin() {
		return this.login;
	}

	/**
	 * Sets the login.
	 *
	 * @param login the new login
	 */
	public final void setLogin(String login) {
		this.login = login;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public final String getPassword() {
		return this.password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the new password
	 */
	public final void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public final String getFirstName() {
		return this.firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public final void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public final String getLastName() {
		return this.lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public final void setLastName(String lastName) {
		this.lastName = lastName;
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
	 * Checks if is activated.
	 *
	 * @return true, if is activated
	 */
	public final boolean isActivated() {
		return this.activated;
	}

	/**
	 * Sets the activated.
	 *
	 * @param activated the new activated
	 */
	public final void setActivated(boolean activated) {
		this.activated = activated;
	}

	/**
	 * Gets the lang key.
	 *
	 * @return the lang key
	 */
	public final String getLangKey() {
		return this.langKey;
	}

	/**
	 * Sets the lang key.
	 *
	 * @param langKey the new lang key
	 */
	public final void setLangKey(String langKey) {
		this.langKey = langKey;
	}

	/**
	 * Gets the activation key.
	 *
	 * @return the activation key
	 */
	public final String getActivationKey() {
		return this.activationKey;
	}

	/**
	 * Sets the activation key.
	 *
	 * @param activationKey the new activation key
	 */
	public final void setActivationKey(String activationKey) {
		this.activationKey = activationKey;
	}

	/**
	 * Gets the reset key.
	 *
	 * @return the reset key
	 */
	public final String getResetKey() {
		return this.resetKey;
	}

	/**
	 * Sets the reset key.
	 *
	 * @param resetKey the new reset key
	 */
	public final void setResetKey(String resetKey) {
		this.resetKey = resetKey;
	}

	/**
	 * Gets the reset date.
	 *
	 * @return the reset date
	 */
	public final Date getResetDate() {
		return this.resetDate;
	}

	/**
	 * Sets the reset date.
	 *
	 * @param resetDate the new reset date
	 */
	public final void setResetDate(Date resetDate) {
		this.resetDate = resetDate;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the creates the date
	 */
	public final Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate the new creates the date
	 */
	public final void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * Gets the authority keys.
	 *
	 * @return the authority keys
	 */
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE) 
	public final List<Key<Authority>> getAuthorityKeys() {
		return this.authorityKeys;
	}

	/**
	 * Sets the authority keys.
	 *
	 * @param authorityKeys the new authority keys
	 */
	public final void setAuthorityKeys(List<Key<Authority>> authorityKeys) {
		this.authorityKeys = authorityKeys;
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
	 * Gets the authority ids.
	 *
	 * @return the authority ids
	 */
	public final List<Long> getAuthorityIds() {
		return this.authorityIds;
	}

	/**
	 * Sets the authority ids.
	 *
	 * @param authorityIds the new authority ids
	 */
	public final void setAuthorityIds(List<Long> authorityIds) {
		this.authorityIds = authorityIds;
	}
	
	/**
	 * Gets the authorities.
	 *
	 * @return the authorities
	 */
	public final List<Authority> getAuthorities() {
		return this.authorities;
	}

	/**
	 * Sets the authorities.
	 *
	 * @param authorities the new authorities
	 */
	public final void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	public final List<String> getRoles() {
		return this.roles;
	}

	/**
	 * Sets the roles.
	 *
	 * @param roles the new roles
	 */
	public final void setRoles(List<String> roles) {
		this.roles = roles;
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

	/**
	 * Gets the group.
	 *
	 * @return the group
	 */
	public final Group getGroup() {
		return this.group;
	}

	/**
	 * Sets the group.
	 *
	 * @param group the new group
	 */
	public final void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public final Position getPosition() {
		return this.position;
	}

	/**
	 * Sets the position.
	 *
	 * @param position the new position
	 */
	public final void setPosition(Position position) {
		this.position = position;
	}

}
