package com.proconco.dao;

import java.util.List;

import com.googlecode.objectify.Key;
import com.proconco.entity.ReportId;
import com.proconco.entity.User;

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
			
			Key<User> key = Key.create(User.class, reportId.getUserId());
			if (key != null) {
				reportId.setUserKey(key);
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
		List<ReportId> list = findAll();
		for (ReportId item : list) {
			delete(item);
		}
	}
}
