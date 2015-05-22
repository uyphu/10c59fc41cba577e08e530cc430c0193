package com.proconco.dao;

import java.util.Calendar;

import com.proconco.entity.Group;

/**
 * The Class GroupDao.
 */
public class GroupDao extends AbstractDao<Group> {

	/**
	 * Instantiates a new group dao.
	 */
	public GroupDao() {
		super(Group.class);
	}

	/**
	 * Inits the data.
	 */
	public void initData() {
		Group group;
		
		for (Long i = 1L; i < 1000; i++) {
			group = new Group(i, "grpName" + String.valueOf(i), "N", "crtUid" + String.valueOf(i), Calendar
					.getInstance().getTime(), "updUid" + String.valueOf(i), Calendar.getInstance().getTime());
			persist(group);
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
