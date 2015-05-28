package com.proconco.dao;

import java.util.Calendar;
import java.util.List;

import com.proconco.entity.SessionInfo;
import com.proconco.entity.User;

/**
 * The Class SessionInfoDao.
 */
public class SessionInfoDao extends AbstractDao<SessionInfo> {

	/**
	 * Instantiates a new group dao.
	 */
	public SessionInfoDao() {
		super(SessionInfo.class);
	}

	/**
	 * Inits the data.
	 */
	public void initData() {
		SessionInfo sessionInfo;
		UserDao userDao = new UserDao();
		for (Long i = 1L; i < 1000; i++) {
			if (i % 2 == 0) {
				User user = userDao.find(1L);
				if (user != null) {
					sessionInfo = new SessionInfo(i, user.getId(), user.getLogin(), 
							Calendar.getInstance().getTime(), "crtUid", Calendar.getInstance().getTime(), 
							"updUid", Calendar.getInstance().getTime());
					persist(sessionInfo);
				}
						
			} else {
				User user = userDao.find(1L);
				if (user != null) {
					sessionInfo = new SessionInfo(i, user.getId(), user.getLogin(), 
							Calendar.getInstance().getTime(), "crtUid", Calendar.getInstance().getTime(), 
							"updUid", Calendar.getInstance().getTime());
					persist(sessionInfo);
				}
			}
		}
		
	}
	
	/**
	 * Clean data.
	 */
	public void cleanData() {
		List<SessionInfo> list = findAll();
		for (SessionInfo sessionInfo : list) {
			delete(sessionInfo);
		}
	}
}
