package com.proconco.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

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
	 * Adds the position.
	 *
	 * @param login the login
	 * @param positionId the position id
	 * @throws ProconcoException the proconco exception
	 */
	public void addPosition(String login, Long positionId) throws ProconcoException {
		try {
			User user = getUserByLogin(login);
			if (user != null && positionId != null) {
				user.setPositionKey(Key.create(Position.class, positionId));
				persist(user);
			}
		} catch (Exception e) {
			throw new ProconcoException(ErrorCode.BAD_REQUEST_EXCEPTION, ErrorCodeDetail.ERROR_UPDATE_ENTITY);
		}
	}
	
	/**
	 * Adds the group.
	 *
	 * @param login the login
	 * @param groupId the group id
	 * @throws ProconcoException the proconco exception
	 */
	public void addGroup(String login, Long groupId) throws ProconcoException {
		try {
			User user = getUserByLogin(login);
			if (user != null && groupId != null) {
				user.setGroupKey(Key.create(Group.class, groupId));
				persist(user);
			}
		} catch (Exception e) {
			throw new ProconcoException(ErrorCode.BAD_REQUEST_EXCEPTION, ErrorCodeDetail.ERROR_UPDATE_ENTITY);
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
		List<User> list = findAll();
		for (User item : list) {
			delete(item);
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
		try {
			// If if is not null, then check if it exists. If yes, throw an
			// Exception
			// that it is already present
			if (user.getId() != null) {
				if (user.getId().equals(0)) {
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
			
			//Add dedault role: ROLE_USER
			AuthorityDao authorityDao = new AuthorityDao();
			Authority authority = authorityDao.getAuthorityByName(AuthoritiesConstants.USER);
			List<Key<Authority>> list = new ArrayList<Key<Authority>>();
			list.add(Key.create(Authority.class, authority.getId()));
			user.setAuthorityKeys(list);
			
			//Save user
			persist(user);
			return user;
		} catch (Exception e) {
			throw new ProconcoException(ErrorCode.SYSTEM_EXCEPTION, ErrorCodeDetail.ERROR_PARSE_QUERY
					+ Constants.STRING_EXEPTION_DETAIL + e.getMessage());
		}

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
	
	/**
	 * Find by group.
	 *
	 * @param groupId the group id
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the collection response
	 * @throws ProconcoException the proconco exception
	 */
	public CollectionResponse<User> findByGroup(Long groupId, String querySearch, String cursorString, Integer count)
			throws ProconcoException {
		Query<User> query = ofy().load().type(User.class);
		if (querySearch != null) {
			query = getQuery(querySearch);
		}
		query = query.filter("groupKey", Key.create(Group.class, groupId));
		return executeQuery(query, cursorString, count);
	}

	/**
	 * Activate user.
	 *
	 * @param login the login
	 * @throws ProconcoException the proconco exception
	 */
	public void activateUser(String login) throws ProconcoException{
		try {
			User user = getUserByLogin(login);
			if (user != null) {
				user.setActivated(true);
				persist(user);
			}
		} catch (Exception e) {
			throw new ProconcoException(ErrorCode.BAD_REQUEST_EXCEPTION, ErrorCodeDetail.ERROR_UPDATE_ENTITY);
		}
		
	}
	
	/**
	 * List authority.
	 *
	 * @param userId the user id
	 * @return the collection response
	 * @throws ProconcoException the proconco exception
	 */
	public CollectionResponse<Authority> listAuthority(@Named("userId") Long userId) throws ProconcoException {
		User user = find(userId);
		if (user != null) {
			List<Key<Authority>> list = user.getAuthorityKeys();
			if (list != null && list.size() > 0) {
				List<Authority> authorities = new ArrayList<Authority>();
				for (Key<Authority> key : list) {
					AuthorityDao dao = new AuthorityDao();
					Authority authority = dao.find(key.getId());
					if (authority != null) {
						authorities.add(authority);
					}
				}
				AuthorityDao dao = new AuthorityDao();
				return dao.buildCollectionResponse(authorities);
			}
		} 
		
		throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION, ErrorCodeDetail.ERROR_RECORD_NOT_FOUND);
	} 

}
