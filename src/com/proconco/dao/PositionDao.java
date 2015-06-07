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
	
	/**
	 * Gets the query.
	 *
	 * @param querySearch the query search
	 * @return the query
	 * @throws NotFoundException the not found exception
	 */
	public Query<Position> getQuery(String querySearch) throws NotFoundException{ 
		try {
			if (querySearch != null) {
				Query<Position> query;
				Map<String,Object> map = new HashMap<String, Object>();
				if (querySearch.indexOf("delFlag:") != -1) {
					String[] queries = querySearch.split(":");
					map.put("delFlag", Long.parseLong(queries[1]));
					query = getQuery(map);
				} else {
					query = getQueryByName("postName", querySearch);
				}
				return query;
			} else {
				return ofy().load().type(Position.class);
			}
		} catch (Exception e) {
			throw new NotFoundException("No found record. Cause:" + e.getMessage());
		}
	}
	
	/**
	 * Search position.
	 *
	 * @param querySearch the query search
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the collection response
	 * @throws NotFoundException the not found exception
	 */
	public CollectionResponse<Position> searchPosition(String querySearch, String cursorString, Integer count)
			throws NotFoundException {
		Query<Position> query = getQuery(querySearch);
		return executeQuery(query, cursorString, count);
	}

}
