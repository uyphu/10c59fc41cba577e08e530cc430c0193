package com.proconco.dao;

import java.util.Calendar;

import com.googlecode.objectify.Key;
import com.proconco.entity.Group;
import com.proconco.entity.Position;
import com.proconco.entity.UserProfile;

/**
 * The Class UserProfileDao.
 */
public class UserProfileDao extends AbstractDao<UserProfile> {

	/**
	 * Instantiates a new group dao.
	 */
	public UserProfileDao() {
		super(UserProfile.class);
	}

	/**
	 * Inits the data.
	 */
	public void initData() {
		UserProfile userProfile;
		
		for (Long i = 1L; i < 1000; i++) {
			if (i % 2 == 0) {
				userProfile = new UserProfile(i, "eamiluser" + String.valueOf(i) + "@yahoo.com", 
						"userNm"+String.valueOf(i), "pwd12356", Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 0, 
						"12345", "Y", "disableReason", "crtUid", Calendar.getInstance().getTime(), "updUid", Calendar.getInstance().getTime(), 1L, 1L);
			} else {
				userProfile = new UserProfile(i, "eamiluser" + String.valueOf(i) + "@gmail.com", 
						"userNm"+String.valueOf(i), "pwd12356", Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), 0, 
						"12345", "Y", "disableReason", "crtUid", Calendar.getInstance().getTime(), "updUid", Calendar.getInstance().getTime(), 2L, 2L);
			}
			
			Key<Group> groupKey = Key.create(Group.class, userProfile.getGroupId());
			if (groupKey != null) {
				userProfile.setGroupKey(groupKey);
			}
			
			Key<Position> posKey = Key.create(Position.class, userProfile.getPositionId());
			if (posKey != null) {
				userProfile.setPositionKey(posKey);
			}
			
			persist(userProfile);
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
