package com.proconco.endpoint;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
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
			throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION.getId(),
					ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
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
			throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION.getId(),
					ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
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
		UserDao dao = new UserDao();
		return dao.searchUser(querySearch, cursorString, count);
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

}
