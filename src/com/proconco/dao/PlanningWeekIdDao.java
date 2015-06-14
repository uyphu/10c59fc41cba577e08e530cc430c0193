package com.proconco.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.server.spi.response.CollectionResponse;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import com.proconco.constants.Constants;
import com.proconco.entity.PlanningWeek;
import com.proconco.entity.PlanningWeekId;
import com.proconco.entity.User;
import com.proconco.exception.ErrorCode;
import com.proconco.exception.ErrorCodeDetail;
import com.proconco.exception.ProconcoException;

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
	
	/**
	 * Gets the query.
	 *
	 * @param querySearch the query search
	 * @return the query
	 * @throws ProconcoException the proconco exception
	 */
	public Query<PlanningWeekId> getQuery(String querySearch) throws ProconcoException { 
		try {
			if (querySearch != null) {
				Query<PlanningWeekId> query;
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
				return ofy().load().type(PlanningWeekId.class);
			}
		} catch (Exception e) {
			throw new ProconcoException(ErrorCode.SYSTEM_EXCEPTION.getId(),
					ErrorCodeDetail.ERROR_PARSE_QUERY + Constants.STRING_EXEPTION_DETAIL + e.getMessage());
		}
	}
	
	/**
	 * Search planningWeekId.
	 *
	 * @param querySearch the query search
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the collection response
	 * @throws ProconcoException the proconco exception
	 */
	public CollectionResponse<PlanningWeekId> searchPlanningWeekId(String querySearch, String cursorString, Integer count)
			throws ProconcoException {
		Query<PlanningWeekId> query = getQuery(querySearch);
		return executeQuery(query, cursorString, count);
	}
	
	/**
	 * Gets the planningWeekId by name.
	 *
	 * @param name the name
	 * @return the planningWeekId by name
	 */
	public PlanningWeekId getPlanningWeekIdByName(String name) {
		if (name != null) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("postName", name);
			Query<PlanningWeekId> query = getQuery(map);
			List<PlanningWeekId> list = executeQuery(query, 1); 
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} 
		return null;
	}
	
	/**
	 * Insert.
	 *
	 * @param planningWeekId the planningWeek id
	 * @return the planningWeek id
	 */
	public PlanningWeekId insert(PlanningWeekId planningWeekId) {
		//Insert into planningWeekId
		persist(planningWeekId);
		
		//Insert into PlanningWeek
		PlanningWeekDao planningWeekDao = new PlanningWeekDao();
		PlanningWeek planningWeek = new PlanningWeek();
		planningWeek.setId(planningWeekId.getId());
		if (planningWeekId.getUserKey() != null) {
			UserDao dao = new UserDao();
			User user = dao.find(planningWeekId.getUserKey());
			if (user != null) {
				planningWeek.setCrtUid(user.getLogin());
				planningWeek.setUpdUid(user.getLogin());
			}
		}
		
		planningWeekDao.insert(planningWeek);
		return planningWeekId;
	}
	
	/**
	 * Removes the.
	 *
	 * @param planningWeekId the planningWeek id
	 */
	public void remove(PlanningWeekId planningWeekId) {
		
		//Delete planningWeek
		PlanningWeekDao planningWeekDao = new PlanningWeekDao();
		PlanningWeek planningWeek = planningWeekDao.find(planningWeekId.getId());
		if (planningWeek != null) {
			planningWeekDao.delete(planningWeek);
		}
		//Delete planningWeek Id
		delete(planningWeekId);
	}
}
