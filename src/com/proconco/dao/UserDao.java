package com.proconco.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.CollectionResponse;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import com.proconco.constants.AuthoritiesConstants;
import com.proconco.constants.Constants;
import com.proconco.entity.Authority;
import com.proconco.entity.Group;
import com.proconco.entity.Position;
import com.proconco.entity.User;
import com.proconco.exception.ErrorCode;
import com.proconco.exception.ErrorCodeDetail;
import com.proconco.exception.ProconcoException;
import com.proconco.utils.MathUtils;

/**
 * The Class UserProfileDao.
 */
public class UserDao extends AbstractDao<User> {

	/**
	 * Instantiates a new group dao.
	 */
	public UserDao() {
		super(User.class);
	}

	@Override
	public User update(User user) {
		User oldUser = find(user.getId());
		if (user.getFirstName() != null) {
			oldUser.setFirstName(user.getFirstName());
		}
		if (user.getLastName() != null) {
			oldUser.setLastName(user.getLastName());
		}
		if (user.getLangKey() != null) {
			oldUser.setLangKey(user.getLangKey());
		}
		if (user.getEmail() != null) {
			oldUser.setEmail(user.getEmail());
		}
		if (user.getGroupId() != null) {
			oldUser.setGroupKey(Key.create(Group.class, user.getGroupId()));
		}
		if (user.getPositionId() != null) {
			oldUser.setPositionKey(Key.create(Position.class, user.getPositionId()));
		}
		if (user.isActivated() != oldUser.isActivated()) {
			oldUser.setActivated(user.isActivated());
		}
		return super.update(oldUser);
	}

	/**
	 * Login.
	 *
	 * @param login the login
	 * @param password the password
	 * @return the user profile
	 * @throws ProconcoException the proconco exception
	 */
	@ApiMethod(name = "login")
	public User login(String login, String password) throws ProconcoException {
		Map<String, Object> columns = new HashMap<String, Object>();
		columns.put("login", login);
		//columns.put("password", MathUtils.cryptWithMD5(password));
		Query<User> query = getQuery(columns);
		List<User> list = executeQuery(query, 1);
		if (list != null && list.size() > 0) {
			User user = list.get(0);
			if (!user.isActivated()) {
				throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION.getId(),
						ErrorCodeDetail.ERROR_NOT_ACTIVATED.getMsg());
			}
			if (!user.getPassword().equals(MathUtils.cryptWithMD5(password))) {
				throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION.getId(),
						ErrorCodeDetail.ERROR_INVALID_PASSWORD.getMsg());
			} else {
				return list.get(0);
			}
		} else {
			throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION.getId(),
					ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
		}
	}

	/**
	 * Gets the user by login.
	 * 
	 * @param login
	 *            the login
	 * @return the user by login
	 */
	public User getUserByLogin(String login) {
		Map<String, Object> columns = new HashMap<String, Object>();
		columns.put("login", login);
		Query<User> query = getQuery(columns);
		List<User> list = executeQuery(query, 1);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * Gets the user by email.
	 * 
	 * @param email
	 *            the email
	 * @return the user by email
	 */
	public User getUserByEmail(String email) {
		Map<String, Object> columns = new HashMap<String, Object>();
		columns.put("email", email);
		Query<User> query = getQuery(columns);
		List<User> list = executeQuery(query, 1);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * Adds the role.
	 * 
	 * @param login
	 *            the login
	 * @param role
	 *            the role
	 */
	@ApiMethod(name = "addRole")
	public void addRole(String login, String role) throws ProconcoException {
		User user = getUserByLogin(login);
		AuthorityDao authorityDao = new AuthorityDao();
		Authority authority = authorityDao.getAuthorityByName(role);
		if (user != null && authority != null) {
			Key<Authority> key = Key.create(Authority.class, authority.getId());
			List<Key<Authority>> list = user.getAuthorityKeys();
			if (list == null) {
				list = new ArrayList<Key<Authority>>();
			}
			if (!list.contains(key)) {
				list.add(key);
				user.setAuthorityKeys(list);
				persist(user);
			} else {
				throw new ProconcoException(ErrorCode.CONFLICT_EXCEPTION.getId(),
						ErrorCodeDetail.ERROR_DUPLICATED_ROLE.getMsg());
			}
		} else {
			throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION.getId(),
					ErrorCodeDetail.ERROR_NOT_FOUND_ROLE.getMsg());
		}
	}

	/**
	 * Inits the data.
	 */
	public void initData() {
		User user;

		for (Long i = 1L; i < 1000; i++) {
			if (i % 2 == 0) {
				user = new User(i, "login" + String.valueOf(i), "123456", "firstName" + String.valueOf(i), "lastName"
						+ String.valueOf(i), "email" + String.valueOf(i) + "@yahoo.com", "EN");
			} else {
				user = new User(i, "login" + String.valueOf(i), "123456", "firstName" + String.valueOf(i), "lastName"
						+ String.valueOf(i), "email" + String.valueOf(i) + "@gmail.com", "EN");
			}

			user.setCreateDate(Calendar.getInstance().getTime());
			persist(user);
		}

	}

	/**
	 * Clean data.
	 */
	public void cleanData() {
		for (Long i = 1L; i < 1000; i++) {
			delete(find(i));
		}
	}

	/**
	 * Insert.
	 * 
	 * @param user
	 *            the user
	 * @return the user
	 * @throws ProconcoException
	 *             the proconco exception
	 */
	public User insert(User user) throws ProconcoException {

		// If if is not null, then check if it exists. If yes, throw an
		// Exception
		// that it is already present
		if (user.getId() != null) {
			if (user.getId() == 0) {
				user.setId(null);
			} else {
				if (find(user.getId()) != null) {
					throw new ProconcoException(ErrorCode.CONFLICT_EXCEPTION.getId(),
							ErrorCodeDetail.ERROR_EXIST_OBJECT.getMsg());
				}
			}
		}
		// Since our @Id field is a Long, Objectify will generate a unique value
		// for us
		// when we use put

		User loginUser = getUserByLogin(user.getLogin());
		User emailUser = getUserByEmail(user.getEmail());
		if (loginUser != null && emailUser != null) {
			throw new ProconcoException(ErrorCode.CONFLICT_EXCEPTION.getId(),
					ErrorCodeDetail.ERROR_CONFLICT_LOGIN_AND_EMAIL.getMsg());
		}

		if (loginUser != null) {
			throw new ProconcoException(ErrorCode.CONFLICT_EXCEPTION.getId(),
					ErrorCodeDetail.ERROR_CONFLICT_LOGIN.getMsg());
		}

		if (emailUser != null) {
			throw new ProconcoException(ErrorCode.CONFLICT_EXCEPTION.getId(),
					ErrorCodeDetail.ERROR_CONFLICT_EMAIL.getMsg());
		}

		// Persist data.
		user.setCreateDate(Calendar.getInstance().getTime());
		user.setPassword(MathUtils.cryptWithMD5(user.getPassword()));
		user = persist(user);
		if (user.getId() != null) {
			addRole(user.getLogin(), AuthoritiesConstants.USER);
		}

		return user;
	}

	/**
	 * Gets the query.
	 * 
	 * @param querySearch
	 *            the query search
	 * @return the query
	 * @throws ProconcoException
	 *             the proconco exception
	 */
	public Query<User> getQuery(String querySearch) throws ProconcoException {
		try {
			if (querySearch != null) {
				Query<User> query;
				Map<String, Object> map = new HashMap<String, Object>();
				if (querySearch.indexOf("delFlag:") != -1) {
					String[] queries = querySearch.split(":");
					map.put("delFlag", Long.parseLong(queries[1]));
					query = getQuery(map);
				} else if (querySearch.indexOf("email:") != -1) {
					query = getQueryByName("email", querySearch);
				} else {
					query = getQueryByName("login", querySearch);
				}
				return query;
			} else {
				return ofy().load().type(User.class);
			}
		} catch (Exception e) {
			throw new ProconcoException(ErrorCode.SYSTEM_EXCEPTION.getId(), ErrorCodeDetail.ERROR_PARSE_QUERY
					+ Constants.STRING_EXEPTION_DETAIL + e.getMessage());
		}
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
	public CollectionResponse<User> searchUser(String querySearch, String cursorString, Integer count)
			throws ProconcoException {
		Query<User> query = getQuery(querySearch);
		return executeQuery(query, cursorString, count);
	}

	/**
	 * Change password.
	 *
	 * @param user            the user
	 * @return the user
	 * @throws ProconcoException the proconco exception
	 */
	public User changePassword(User user) throws ProconcoException {
		try {
			User oldUser = find(user.getId());
			if (oldUser == null) {
				throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION.getId(),
						ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
			}
			oldUser.setPassword(MathUtils.cryptWithMD5(user.getPassword()));
			return super.update(oldUser);
		} catch (Exception e) {
			throw new ProconcoException(ErrorCode.SYSTEM_EXCEPTION.getId(), ErrorCodeDetail.ERROR_PARSE_QUERY
					+ Constants.STRING_EXEPTION_DETAIL + e.getMessage());
		}
	}

}
