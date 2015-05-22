package com.proconco.dao;

import java.util.Calendar;

import com.proconco.entity.Position;

/**
 * The Class PositionDao.
 */
public class PositionDao extends AbstractDao<Position> {

	/**
	 * Instantiates a new group dao.
	 */
	public PositionDao() {
		super(Position.class);
	}

	/**
	 * Inits the data.
	 */
	public void initData() {
		Position position;
		
		for (Long i = 1L; i < 1000; i++) {
			position = new Position(i, "postName" + String.valueOf(i), "N", "crtUid" + String.valueOf(i), Calendar
					.getInstance().getTime(), "updUid" + String.valueOf(i), Calendar.getInstance().getTime());
			persist(position);
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
