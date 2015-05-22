package com.proconco.dao;

import java.util.Calendar;

import com.proconco.entity.SessionInfo;
import com.proconco.entity.UserProfile;

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
		UserProfileDao userProfileDao = new UserProfileDao();
		for (Long i = 1L; i < 1000; i++) {
			if (i % 2 == 0) {
				UserProfile userProfile = userProfileDao.find(1L);
				if (userProfile != null) {
					sessionInfo = new SessionInfo(i, userProfile.getId(), userProfile.getUserNm(), 
							Calendar.getInstance().getTime(), "crtUid", Calendar.getInstance().getTime(), 
							"updUid", Calendar.getInstance().getTime());
					persist(sessionInfo);
				}
						
			} else {
				UserProfile userProfile = userProfileDao.find(1L);
				if (userProfile != null) {
					sessionInfo = new SessionInfo(i, userProfile.getId(), userProfile.getUserNm(), 
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
		for (Long i = 1L; i < 1000; i++) {
			delete(find(i));
		}
	}
}
