package com.proconco.endpoint;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.proconco.dao.UserProfileDao;
import com.proconco.entity.UserProfile;

@Api(name = "userprofileendpoint", namespace = @ApiNamespace(ownerDomain = "proconco.com", ownerName = "proconco.com", packagePath = "entity"))
public class UserProfileEndpoint {

	/**
	* Return a collection of userProfiles
	*
	* @param count The number of userProfiles
	* @return a list of UserProfiles
	*/
	@ApiMethod(name = "listUserProfile")
	public CollectionResponse<UserProfile> listUserProfile(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("count") Integer count) {
		UserProfileDao dao = new UserProfileDao();
		return dao.list(cursorString, count);
	}
	
	/**
	* This inserts a new <code>UserProfile</code> object.
	* @param userProfile The object to be added.
	* @return The object to be added.
	*/
	@ApiMethod(name = "insertUserProfile")
	public UserProfile insertUserProfile(UserProfile userProfile) throws ConflictException {
		// If if is not null, then check if it exists. If yes, throw an
		// Exception
		// that it is already present
		if (userProfile.getId() != null) {
			if (userProfile.getId() == 0) {
				userProfile.setId(null);
			} else {
				if (findRecord(userProfile.getId()) != null) {
					throw new ConflictException("Object already exists");
				}
			}
		}
		// Since our @Id field is a Long, Objectify will generate a unique value
		// for us
		// when we use put
		UserProfileDao dao = new UserProfileDao();
		dao.persist(userProfile);
		return userProfile;
	}

	/**
	 * This updates an existing <code>UserProfile</code> object.
	 * 
	 * @param userProfile
	 *            The object to be added.
	 * @return The object to be updated.
	 */
	@ApiMethod(name = "updateUserProfile")
	public UserProfile updateUserProfile(UserProfile userProfile) throws NotFoundException {
		if (findRecord(userProfile.getId()) == null) {
			throw new NotFoundException("UserProfile Record does not exist");
		}
		UserProfileDao dao = new UserProfileDao();
		dao.update(userProfile);
		return userProfile;
	}

	/**
	 * This deletes an existing <code>UserProfile</code> object.
	 * 
	 * @param id
	 *            The id of the object to be deleted.
	 */
	@ApiMethod(name = "removeUserProfile")
	public void removeUserProfile(@Named("id") Long id) throws NotFoundException {
		UserProfile record = findRecord(id);
		if (record == null) {
			throw new NotFoundException("UserProfile Record does not exist");
		}
		UserProfileDao dao = new UserProfileDao();
		dao.delete(record);
	}
	
	/**
	 * Gets the userProfile.
	 *
	 * @param id the id
	 * @return the userProfile
	 */
	@ApiMethod(name = "getUserProfile")
	public UserProfile getUserProfile(@Named("id") Long id) {
		return findRecord(id);
	}
	
	/**
	 * Find record.
	 *
	 * @param id the id
	 * @return the user main
	 */
	private UserProfile findRecord(Long id) {
		UserProfileDao dao = new UserProfileDao();
		return dao.find(id);
	}
	
	/**
	 * Inits the data.
	 */
	@ApiMethod(name = "initData")
	public void initData() {
		UserProfileDao dao = new UserProfileDao();
		dao.initData();
	}
	
	/**
	 * Clean data.
	 */
	@ApiMethod(name = "cleanData")
	public void cleanData() {
		UserProfileDao dao = new UserProfileDao();
		dao.cleanData();
	}

}
