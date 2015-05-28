package com.proconco.dao;

import java.util.Calendar;
import java.util.List;

import com.proconco.entity.PlanningWeek;

public class PlanningWeekDao extends AbstractDao<PlanningWeek> {

	public PlanningWeekDao() {
		super(PlanningWeek.class);
	}


	/**
	 * Inits the data.
	 */
	public void initData() {
		PlanningWeek planningWeek;
		PlanningWeekIdDao planningWeekIdDao = new PlanningWeekIdDao();
		for (Long i = 1L; i < 1000; i++) {
			if (planningWeekIdDao.find(i) != null) {
				planningWeek = new PlanningWeek(i, "monday", "tuesday", "wednesday", "thursday", "friday", "saturday",
						"sunday", "N", "crtUid", Calendar.getInstance().getTime(), "updUid", Calendar.getInstance()
								.getTime());
				persist(planningWeek);
			}

		}
		
	}
	
	/**
	 * Clean data.
	 */
	public void cleanData() {
		List<PlanningWeek> list = findAll();
		for (PlanningWeek item : list) {
			delete(item);
		}
	}

}
