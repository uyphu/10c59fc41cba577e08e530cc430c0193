package com.proconco.dao;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.server.spi.config.ApiMethod;
import com.googlecode.objectify.cmd.Query;
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
