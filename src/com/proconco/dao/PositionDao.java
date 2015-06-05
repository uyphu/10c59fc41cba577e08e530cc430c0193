package com.proconco.dao;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.googlecode.objectify.cmd.Query;
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
		
		for (Long i = 1L; i < 10; i++) {
			position = new Position(i, "postName" + String.valueOf(i), "N", "crtUid" + String.valueOf(i), Calendar
					.getInstance().getTime(), "updUid" + String.valueOf(i), Calendar.getInstance().getTime());
			persist(position);
		}
		
	}
	
	/**
	 * Clean data.
	 */
	public void cleanData() {
		List<Position> list = findAll();
		for (Position item : list) {
			delete(item);
		}
	}
	
	public CollectionResponse<Position> searchPosition(String querySearch, String cursorString, Integer count) throws NotFoundException{ 
		try {
			if (querySearch != null) {
				Map<String,Object> map = new HashMap<String, Object>();
				if (querySearch.indexOf("id:") != -1) {
					String[] queries = querySearch.split(":");
					map.put("id", Long.parseLong(queries[1]));
				} else {
					map.put("postName", querySearch);
				}
				Query<Position> query = getQuery(map);
				return executeQuery(query, cursorString, count);
			}
		} catch (Exception e) {
			throw new NotFoundException("No found record. Cause:" + e.getMessage());
		}
		return null;
	}

}
