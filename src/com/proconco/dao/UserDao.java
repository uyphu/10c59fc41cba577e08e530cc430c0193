package com.proconco.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.NotFoundException;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import com.proconco.entity.Authority;
import com.proconco.entity.Group;
import com.proconco.entity.Position;
import com.proconco.entity.User;

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
		return super.update(oldUser);
	}
	
	/**
	 * Login.
	 *
	 * @param email the email
	 * @param pwd the pwd
	 * @return the user profile
	 */
	@ApiMethod(name = "login")
	public User login(String login, String password) {
		Map<String, Object> columns = new HashMap<String, Object>();
		columns.put("login", login);
		columns.put("password", password);
		Query<User> query = getQuery(columns);
		List<User> list = executeQuery(query, 1);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * Gets the user by login.
	 *
	 * @param login the login
	 * @return the user by login
	 */
	private User getUserByLogin(String login) {
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
	 * Adds the role.
	 *
	 * @param login the login
	 * @param role the role
	 */
	@ApiMethod(name = "addRole")
	public void addRole(String login, String role) throws NotFoundException{
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
				throw new NotFoundException("Duplicated role.");
			}
		} else {
			throw new NotFoundException("Not found record to add role.");
		}
	}
	
	/**
	 * Inits the data.
	 */
	public void initData() {
		User user;
		
		for (Long i = 1L; i < 1000; i++) {
			if (i % 2 == 0) {
				user = new User(i, "login" + String.valueOf(i), "123456", "firstName"+ String.valueOf(i), 
						"lastName" + String.valueOf(i), "email"+ String.valueOf(i) + "@yahoo.com", "EN");
			} else {
				user = new User(i, "login" + String.valueOf(i), "123456", "firstName"+ String.valueOf(i), 
						"lastName" + String.valueOf(i), "email"+ String.valueOf(i) + "@gmail.com", "EN");
			}
			
//			Key<Group> groupKey = Key.create(Group.class, user.getGroupId());
//			if (groupKey != null) {
//				user.setGroupKey(groupKey);
//			}
//			
//			Key<Position> posKey = Key.create(Position.class, user.getPositionId());
//			if (posKey != null) {
//				user.setPositionKey(posKey);
//			}
			
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
}