package com.proconco.dao;

import com.googlecode.objectify.Key;
import com.proconco.entity.ReportId;
import com.proconco.entity.UserProfile;

/**
 * The Class ReportIdDao.
 */
public class ReportIdDao extends AbstractDao<ReportId> {

	/**
	 * Instantiates a new group dao.
	 */
	public ReportIdDao() {
		super(ReportId.class);
	}

	/**
	 * Inits the data.
	 */
	public void initData() {
		ReportId reportId;
		Integer week = 0;
		int year = 2015;
		for (Long i = 1L; i < 1000; i++) {
			if (i % 2 == 0) {
				reportId = new ReportId(i, ++week, year, 1L);
			} else {
				reportId = new ReportId(i, ++week, year, 2L);
			}
			
			Key<UserProfile> key = Key.create(UserProfile.class, reportId.getUserProfileId());
			if (key != null) {
				reportId.setUserProfileKey(key);
			}
			
			persist(reportId);
			
			if (week == 52) {
				year--;
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
