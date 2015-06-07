package com.proconco.dao;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.googlecode.objectify.cmd.Query;
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
		
		for (Long i = 1L; i < 10; i++) {
			group = new Group(i, "grpName" + String.valueOf(i), "N", "crtUid" + String.valueOf(i), Calendar
					.getInstance().getTime(), "updUid" + String.valueOf(i), Calendar.getInstance().getTime());
			persist(group);
		}
		
	}
	
	/**
	 * Clean data.
	 */
	public void cleanData() {
		List<Group> list = findAll();
		for (Group item : list) {
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
	public Query<Group> getQuery(String querySearch) throws NotFoundException{ 
		try {
			if (querySearch != null) {
				Query<Group> query;
				Map<String,Object> map = new HashMap<String, Object>();
				if (querySearch.indexOf("delFlag:") != -1) {
					String[] queries = querySearch.split(":");
					map.put("delFlag", Long.parseLong(queries[1]));
					query = getQuery(map);
				} else {
					query = getQueryByName("grpName", querySearch);
				}
				return query;
			} else {
				return ofy().load().type(Group.class);
			}
		} catch (Exception e) {
			throw new NotFoundException("No found record. Cause:" + e.getMessage());
		}
	}
	
	/**
	 * Search group.
	 *
	 * @param querySearch the query search
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the collection response
	 * @throws NotFoundException the not found exception
	 */
	public CollectionResponse<Group> searchGroup(String querySearch, String cursorString, Integer count)
			throws NotFoundException {
		Query<Group> query = getQuery(querySearch);
		return executeQuery(query, cursorString, count);
	}
}
