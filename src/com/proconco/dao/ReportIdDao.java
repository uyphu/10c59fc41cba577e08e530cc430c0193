package com.proconco.dao;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.server.spi.response.CollectionResponse;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import com.proconco.constants.Constants;
import com.proconco.entity.Report;
import com.proconco.entity.ReportId;
import com.proconco.entity.User;
import com.proconco.exception.ErrorCode;
import com.proconco.exception.ErrorCodeDetail;
import com.proconco.exception.ProconcoException;

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
	public void initData(){
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
	public void cleanData(){
		List<ReportId> list = findAll();
		for (ReportId item : list) {
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
	public Query<ReportId> getQuery(String querySearch) throws ProconcoException { 
		try {
			if (querySearch != null) {
				Query<ReportId> query;
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
				return ofy().load().type(ReportId.class);
			}
		} catch (Exception e) {
			throw new ProconcoException(ErrorCode.SYSTEM_EXCEPTION.getId(),
					ErrorCodeDetail.ERROR_PARSE_QUERY + Constants.STRING_EXEPTION_DETAIL + e.getMessage());
		}
	}
	
	/**
	 * Search reportId.
	 *
	 * @param querySearch the query search
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the collection response
	 * @throws ProconcoException the proconco exception
	 */
	public CollectionResponse<ReportId> searchReportId(String querySearch, String cursorString, Integer count, Long userId)
			throws ProconcoException {
		Query<ReportId> query = getQuery(querySearch);
		if (userId != null) {
			query = query.filter("userKey", Key.create(User.class, userId));
		}
		query.order("-year").order("-week");
		return executeQuery(query, cursorString, count);
	}
	
	/**
	 * Gets the reportId by name.
	 *
	 * @param name the name
	 * @return the reportId by name
	 */
	public ReportId getReportIdByName(String name) {
		if (name != null) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("postName", name);
			Query<ReportId> query = getQuery(map);
			List<ReportId> list = executeQuery(query, 1); 
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} 
		return null;
	}
	
	/**
	 * Insert.
	 *
	 * @param reportId the report id
	 * @return the report id
	 * @throws ProconcoException the proconco exception
	 */
	public ReportId insert(ReportId reportId) throws ProconcoException{
		try {
			ReportId reportIdFound = find(reportId.getYear(), reportId.getWeek(), reportId.getUserId());
			if (reportIdFound == null) {
				//Insert into reportId
				reportId.setUserKey(Key.create(User.class, reportId.getUserId()));
				reportId.setCrtTms(Calendar.getInstance().getTime());
				persist(reportId);
				
				//Insert into Report
				ReportDao reportDao = new ReportDao();
				Report report = new Report();
				report.setId(reportId.getId());
				if (reportId.getUserKey() != null) {
					UserDao dao = new UserDao();
					User user = dao.find(reportId.getUserKey());
					if (user != null) {
						report.setCrtUid(user.getLogin());
						report.setUpdUid(user.getLogin());
					}
				}
				
				reportDao.insert(report);
				return reportId;
				
			} else {
				throw new ProconcoException(ErrorCode.CONFLICT_EXCEPTION, ErrorCodeDetail.ERROR_EXIST_OBJECT);
			}
			
		} catch (Exception e) {
			throw new ProconcoException(ErrorCode.SYSTEM_EXCEPTION.getId(),
					ErrorCodeDetail.ERROR_PARSE_QUERY + Constants.STRING_EXEPTION_DETAIL + e.getMessage());
		}
	}
	
	public ReportId merge(ReportId reportId) throws ProconcoException{
		try {
			ReportId oldReportId = find(reportId.getId());
			if (oldReportId == null) {
				throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION,
						ErrorCodeDetail.ERROR_RECORD_NOT_FOUND);
			}
			
			if (reportId.getStatus() != 0) {
				oldReportId.setStatus(reportId.getStatus());
			}
			persist(oldReportId);
			return oldReportId;
		} catch (Exception e) {
			throw new ProconcoException(ErrorCode.SYSTEM_EXCEPTION, ErrorCodeDetail.ERROR_UPDATE_ENTITY.getMsg()
					+ Constants.STRING_EXEPTION_DETAIL + e.getMessage());
		}
		
	}
	
	/**
	 * Removes the.
	 *
	 * @param reportId the report id
	 */
	public void remove(ReportId reportId) {
		
		//Delete report
		ReportDao reportDao = new ReportDao();
		Report report = reportDao.find(reportId.getId());
		if (report != null) {
			reportDao.delete(report);
		}
		//Delete report Id
		delete(reportId);
	}
	
	@Override
	public CollectionResponse<ReportId> list(String cursorString, Integer count) {
		Query<ReportId> query = ofy().load().type(ReportId.class).order("-crtTms");
		return executeQuery(query, cursorString, count);
	}
	
	/**
	 * Find.
	 *
	 * @param year the year
	 * @param week the week
	 * @param userId the user id
	 * @return the report id
	 */
	public ReportId find(Integer year, Integer week, Long userId) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("year", year);
		map.put("week", week);
		map.put("userKey", Key.create(User.class, userId));
		Query<ReportId> query = getQuery(map);
		List<ReportId> list = executeQuery(query, 10); 
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * Find.
	 *
	 * @param userId the user id
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the collection response
	 */
	public CollectionResponse<ReportId> find(Long userId, String cursorString, Integer count) {
		Query<ReportId> query = ofy().load().type(ReportId.class);
		query = query.filter("userKey", Key.create(User.class, userId));
		query = query.order("-year");
		query = query.order("-week");
		return executeQuery(query, cursorString, count);
	}
	
	/**
	 * Find.
	 *
	 * @param id the id
	 * @param userId the user id
	 * @return the report id
	 */
	public ReportId find(Long id, Long userId) {
		Query<ReportId> query = ofy().load().type(ReportId.class);
		query = query.filter("userKey", Key.create(User.class, userId));
		query = query.order("-year");
		query = query.order("-week");
		ReportIdDao dao = new ReportIdDao();
		return dao.find(id);
	}
	
	/**
	 * Approve.
	 *
	 * @param id the id
	 * @param login the login
	 * @return the report id
	 * @throws ProconcoException the proconco exception
	 */
	public ReportId approve(Long id, String login) throws ProconcoException{
		ReportId reportId = find(id);
		if (reportId != null) {
			reportId.setApprovalUser(login);
			reportId.setApprovalTms(Calendar.getInstance().getTime());
			reportId.setStatus(Constants.APPROVED_STATUS);
			update(reportId);
			return reportId;
		} else {
			throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION, ErrorCodeDetail.ERROR_RECORD_NOT_FOUND);
		}
	}
	
}
