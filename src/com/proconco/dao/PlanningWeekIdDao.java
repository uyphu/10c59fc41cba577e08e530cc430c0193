package com.proconco.dao;

import java.util.List;

import com.googlecode.objectify.Key;
import com.proconco.entity.PlanningWeekId;
import com.proconco.entity.User;

/**
 * The Class PlanningWeekIdDao.
 */
public class PlanningWeekIdDao extends AbstractDao<PlanningWeekId> {

	/**
	 * Instantiates a new group dao.
	 */
	public PlanningWeekIdDao() {
		super(PlanningWeekId.class);
	}

	/**
	 * Inits the data.
	 */
	public void initData() {
		PlanningWeekId planningWeekId;
		Integer week = 0;
		int year = 2015;
		for (Long i = 1L; i < 1000; i++) {
			if (i % 2 == 0) {
				planningWeekId = new PlanningWeekId(i, ++week, year, 1L);
			} else {
				planningWeekId = new PlanningWeekId(i, ++week, year, 2L);
			}
			
			Key<User> key = Key.create(User.class, planningWeekId.getUserId());
			if (key != null) {
				planningWeekId.setUserKey(key);
			}
			
			persist(planningWeekId);
			
			if (week == 52) {
				year--;
			}
		}
		
	}
	
	/**
	 * Clean data.
	 */
	public void cleanData() {
		List<PlanningWeekId> list = findAll();
		for (PlanningWeekId item : list) {
			delete(item);
		}
	}
}
