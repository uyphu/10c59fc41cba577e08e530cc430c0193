package com.proconco.endpoint;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.proconco.constants.Constants;
import com.proconco.dao.UserDao;
import com.proconco.entity.User;
import com.proconco.exception.ErrorCode;
import com.proconco.exception.ErrorCodeDetail;
import com.proconco.exception.ProconcoException;

@Api(name = "userendpoint", namespace = @ApiNamespace(ownerDomain = "proconco.com", ownerName = "proconco.com", packagePath = "entity"))
public class UserEndpoint {

	/**
	 * Return a collection of users
	 * 
	 * @param count
	 *            The number of users
	 * @return a list of Users
	 */
	@ApiMethod(name = "listUser")
	public CollectionResponse<User> listUser(@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("count") Integer count) {
		UserDao dao = new UserDao();
		return dao.list(cursorString, count);
	}

	/**
	 * This inserts a new <code>User</code> object.
	 * 
	 * @param user
	 *            The object to be added.
	 * @return The object to be added.
	 */
	@ApiMethod(name = "insertUser")
	public User insertUser(User user) throws ProconcoException {
		UserDao dao = new UserDao();
		user = dao.insert(user);
		return user;
	}

	/**
	 * This updates an existing <code>User</code> object.
	 * 
	 * @param user
	 *            The object to be added.
	 * @return The object to be updated.
	 */
	@ApiMethod(name = "updateUser")
	public User updateUser(User user) throws ProconcoException {
		if (findRecord(user.getId()) == null) {
			throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION,
					ErrorCodeDetail.ERROR_RECORD_NOT_FOUND);
		}
		UserDao dao = new UserDao();
		dao.update(user);
		return user;
	}

	/**
	 * This deletes an existing <code>User</code> object.
	 * 
	 * @param id
	 *            The id of the object to be deleted.
	 */
	@ApiMethod(name = "removeUser")
	public void removeUser(@Named("id") Long id) throws ProconcoException {
		User record = findRecord(id);
		if (record == null) {
			throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION,
					ErrorCodeDetail.ERROR_RECORD_NOT_FOUND);
		}
		UserDao dao = new UserDao();
		dao.delete(record);
	}

	/**
	 * Gets the user.
	 * 
	 * @param id
	 *            the id
	 * @return the user
	 */
	@ApiMethod(name = "getUser")
	public User getUser(@Named("id") Long id) {
		return findRecord(id);
	}
	
	/**
	 * Find record.
	 * 
	 * @param id
	 *            the id
	 * @return the user main
	 */
	private User findRecord(Long id) {
		UserDao dao = new UserDao();
		return dao.find(id);
	}

	/**
	 * Login.
	 * 
	 * @param email
	 *            the email
	 * @param pwd
	 *            the pwd
	 * @return the user profile
	 */
	@ApiMethod(name = "login", httpMethod = HttpMethod.GET, path = "login_user")
	public User login(@Named("login") String login, @Named("password") String password) throws ProconcoException {
		UserDao dao = new UserDao();
		return dao.login(login, password);
	}

	/**
	 * Logout.
	 * 
	 * @return the user
	 */
	@ApiMethod(name = "logout", httpMethod = HttpMethod.GET, path = "logout_user")
	public User logout() {
		// UserDao dao = new UserDao();
		// return dao.login(login, password);
		// delete Token key;
		return new User();
	}

	/**
	 * Gets the account.
	 * 
	 * @return the account
	 */
	@ApiMethod(name = "getAccount", httpMethod = HttpMethod.GET, path = "get_account")
	public User getAccount() {
		return new User();
	}

	/**
	 * Adds the role.
	 *
	 * @param login the login
	 * @param role the role
	 * @throws ProconcoException the proconco exception
	 */
	@ApiMethod(name = "addRole", httpMethod = HttpMethod.POST, path = "add_role")
	public void addRole(@Named("login") String login, @Named("role") String role) throws ProconcoException {
		UserDao dao = new UserDao();
		dao.addRole(login, role);
	}

	/**
	 * Inits the data.
	 */
	@ApiMethod(name = "initData", path = "initData")
	public void initData() {
		UserDao dao = new UserDao();
		dao.initData();
	}

	/**
	 * Clean data.
	 */
	@ApiMethod(name = "cleanData", path = "cleanData")
	public void cleanData() {
		UserDao dao = new UserDao();
		dao.cleanData();
	}

	/**
	 * Search user.
	 * 
	 * @param querySearch
	 *            the query search
	 * @param cursorString
	 *            the cursor string
	 * @param count
	 *            the count
	 * @return the collection response
	 * @throws ProconcoException
	 *             the proconco exception
	 */
	@ApiMethod(name = "searchUser", httpMethod = HttpMethod.GET, path = "search_user")
	public CollectionResponse<User> searchUser(@Nullable @Named("querySearch") String querySearch,
			@Nullable @Named("cursor") String cursorString, @Nullable @Named("count") Integer count)
			throws ProconcoException {
		try {
			UserDao dao = new UserDao();
			if (querySearch != null && querySearch.indexOf("id:") != -1) {
				String id = querySearch.split(":")[1];
				User user =  findRecord(Long.parseLong(id));
				return dao.buildCollectionResponse(user, null);
			}
			
			return dao.searchUser(querySearch, cursorString, count);
		} catch (Exception e) {
			throw new ProconcoException(ErrorCode.SYSTEM_EXCEPTION.getId(), ErrorCodeDetail.ERROR_PARSE_QUERY
					+ Constants.STRING_EXEPTION_DETAIL + e.getMessage());
		}
		
	}
	
	/**
	 * List user by group.
	 *
	 * @param groupId the group id
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the collection response
	 * @throws ProconcoException the proconco exception
	 */
	@ApiMethod(name = "listUserByGroup", httpMethod = HttpMethod.GET, path = "search_user_by_group")
	public CollectionResponse<User> listUserByGroup(@Nullable @Named("groupId") Long groupId,
			@Nullable @Named("querySearch") String querySearch, @Nullable @Named("cursor") String cursorString,
			@Nullable @Named("count") Integer count) throws ProconcoException {
		try {
			if (groupId == null) {
				throw new ProconcoException(ErrorCode.BAD_REQUEST_EXCEPTION, ErrorCodeDetail.ERROR_RECORD_NOT_FOUND);
			}
			UserDao dao = new UserDao();
			if (querySearch != null && querySearch.indexOf("id:") != -1) {
				String id = querySearch.split(":")[1];
				User user =  findRecord(Long.parseLong(id));
				if (user != null && user.getGroupId().equals(groupId)) {
					return dao.buildCollectionResponse(user, null);
				} else {
					throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION, ErrorCodeDetail.ERROR_RECORD_NOT_FOUND);
				}
				
			}
			return dao.findByGroup(groupId, querySearch, cursorString, count);
		} catch (Exception e) {
			throw new ProconcoException(ErrorCode.SYSTEM_EXCEPTION, ErrorCodeDetail.ERROR_PARSE_QUERY
					+ Constants.STRING_EXEPTION_DETAIL + e.getMessage());
		}
		
		
	}

	/**
	 * Change password.
	 * 
	 * @param user
	 *            the user
	 * @return the user
	 * @throws ProconcoException
	 *             the proconco exception
	 */
	@ApiMethod(name = "changePassword", httpMethod = HttpMethod.POST, path = "change_password")
	public User changePassword(User user) throws ProconcoException {
		UserDao dao = new UserDao();
		return dao.changePassword(user);
	}
	
	/**
	 * Adds the position.
	 *
	 * @param login the login
	 * @param positionId the position id
	 * @throws ProconcoException the proconco exception
	 */
	@ApiMethod(name = "addPosition", httpMethod = HttpMethod.POST, path = "add_position")
	public void addPosition(@Named("login") String login, @Named("positionId") Long positionId) throws ProconcoException {
		UserDao dao = new UserDao();
		dao.addPosition(login, positionId);
	} 
	
	/**
	 * Adds the group.
	 *
	 * @param login the login
	 * @param groupId the group id
	 * @throws ProconcoException the proconco exception
	 */
	@ApiMethod(name = "addGroup", httpMethod = HttpMethod.POST, path = "add_group")
	public void addGroup(@Named("login") String login, @Named("groupId") Long groupId) throws ProconcoException {
		UserDao dao = new UserDao();
		dao.addGroup(login, groupId);
	} 
	
	/**
	 * Activate user.
	 *
	 * @param login the login
	 * @throws ProconcoException the proconco exception
	 */
	@ApiMethod(name = "activateUser", httpMethod = HttpMethod.POST, path = "activate_user")
	public void activateUser(@Named("login") String login) throws ProconcoException {
		UserDao dao = new UserDao();
		dao.activateUser(login);
	} 

}
