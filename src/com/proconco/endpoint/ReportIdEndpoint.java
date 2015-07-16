package com.proconco.endpoint;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.proconco.constants.Constants;
import com.proconco.dao.ReportIdDao;
import com.proconco.entity.ReportId;
import com.proconco.exception.ErrorCode;
import com.proconco.exception.ErrorCodeDetail;
import com.proconco.exception.ProconcoException;

@Api(name = "reportidendpoint", namespace = @ApiNamespace(ownerDomain = "proconco.com", ownerName = "proconco.com", packagePath = "entity"))
public class ReportIdEndpoint {

	/**
	 * Return a collection of reportIds
	 * 
	 * @param count
	 *            The number of reportIds
	 * @return a list of ReportIds
	 */
	@ApiMethod(name = "listReportId")
	public CollectionResponse<ReportId> listReportId(@Nullable @Named("userId") Long userId,
			@Nullable @Named("cursor") String cursorString, @Nullable @Named("count") Integer count) {
		ReportIdDao dao = new ReportIdDao();
		if (userId != null) {
			return dao.find(userId, cursorString, count);
		} else {
			return dao.list(cursorString, count);
		}
	}

	/**
	 * This inserts a new <code>ReportId</code> object.
	 * 
	 * @param reportId
	 *            The object to be added.
	 * @return The object to be added.
	 * @throws ProconcoException
	 *             the proconco exception
	 */
	@ApiMethod(name = "insertReportId")
	public ReportId insertReportId(ReportId reportId) throws ProconcoException {
		try {
			// If if is not null, then check if it exists. If yes, throw an
			// Exception
			// that it is already present
			if (reportId.getId() != null) {
				if (reportId.getId() == 0) {
					//reportId = createId(null);
					reportId.setId(null);
					reportId.setStatus(Constants.IN_WORK_STATUS);
				} else {
					if (findRecord(reportId.getId()) != null) {
						throw new ProconcoException(ErrorCode.CONFLICT_EXCEPTION,
								ErrorCodeDetail.ERROR_EXIST_OBJECT);
					}
				}
			} 
			// Since our @Id field is a Long, Objectify will generate a unique
			// value
			// for us
			// when we use put
			ReportIdDao dao = new ReportIdDao();
			return dao.insert(reportId);
		} catch (Exception e) {
			throw new ProconcoException(ErrorCode.SYSTEM_EXCEPTION,
					ErrorCodeDetail.ERROR_INSERT_ENTITY + Constants.STRING_EXEPTION_DETAIL + e.getMessage());
		}

	}

	/**
	 * This updates an existing <code>ReportId</code> object.
	 * 
	 * @param reportId
	 *            The object to be added.
	 * @return The object to be updated.
	 * @throws ProconcoException
	 *             the proconco exception
	 */
	@ApiMethod(name = "updateReportId")
	public ReportId updateReportId(ReportId reportId) throws ProconcoException {
		ReportIdDao dao = new ReportIdDao();
		return dao.merge(reportId);
	}

	/**
	 * This deletes an existing <code>ReportId</code> object.
	 * 
	 * @param id
	 *            The id of the object to be deleted.
	 */
	@ApiMethod(name = "removeReportId")
	public void removeReportId(@Named("id") Long id) throws ProconcoException {
		try {
			ReportId record = findRecord(id);
			if (record == null) {
				throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION,
						ErrorCodeDetail.ERROR_RECORD_NOT_FOUND);
			}
			ReportIdDao dao = new ReportIdDao();
			dao.remove(record);
		} catch (Exception e) {
			throw new ProconcoException(ErrorCode.SYSTEM_EXCEPTION,
					ErrorCodeDetail.ERROR_REMOVE_ENTITY + Constants.STRING_EXEPTION_DETAIL + e.getMessage());
		}

	}

	/**
	 * Gets the reportId.
	 * 
	 * @param id
	 *            the id
	 * @return the reportId
	 */
	@ApiMethod(name = "getReportId")
	public ReportId getReportId(@Named("id") Long id) {
		return findRecord(id);
	}

	/**
	 * Gets the reportId by name.
	 * 
	 * @param name
	 *            the name
	 * @return the reportId by name
	 */
	@ApiMethod(name = "getReportIdByName", httpMethod = HttpMethod.GET, path = "get_reportId_by_name")
	public ReportId getReportIdByName(@Named("name") String name) throws ProconcoException {
		ReportIdDao dao = new ReportIdDao();
		ReportId reportId = dao.getReportIdByName(name);
		if (reportId == null) {
			throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION,
					ErrorCodeDetail.ERROR_RECORD_NOT_FOUND);
		}
		return reportId;
	}

	/**
	 * Find record.
	 * 
	 * @param id
	 *            the id
	 * @return the user main
	 */
	private ReportId findRecord(Long id) {
		ReportIdDao dao = new ReportIdDao();
		return dao.find(id);
	}
	
	private ReportId findRecord(Long id, Long userId) {
		ReportIdDao dao = new ReportIdDao();
		return dao.find(id, userId);
	}

	/**
	 * Inits the data.
	 */
	@ApiMethod(name = "initData")
	public void initData() throws ProconcoException{
		ReportIdDao dao = new ReportIdDao();
		dao.initData();
	}

	/**
	 * Clean data.
	 */
	@ApiMethod(name = "cleanData")
	public void cleanData() {
		ReportIdDao dao = new ReportIdDao();
		dao.cleanData();
	}

	@ApiMethod(name = "searchReportId", httpMethod = HttpMethod.GET, path = "search_reportId")
	public CollectionResponse<ReportId> searchReportId(@Nullable @Named("querySearch") String querySearch,
			@Nullable @Named("cursor") String cursorString, @Nullable @Named("count") Integer count, 
			@Nullable @Named("userId") Long userId)
			throws ProconcoException {
		ReportIdDao dao = new ReportIdDao();
		if (querySearch != null && querySearch.indexOf("id:") != -1) {
			String id = querySearch.split(":")[1];
			ReportId reportId =  findRecord(Long.parseLong(id), userId);
			return dao.buildCollectionResponse(reportId, null);
		}
		return dao.searchReportId(querySearch, cursorString, count, userId);
	}

	/**
	 * Creates the id.
	 * 
	 * @param reportId
	 *            the report id
	 * @return the report id
	 */
//	private ReportId createId(ReportId reportId) {
//		StringBuilder id = new StringBuilder();
//		id.append(reportId.getYear());
//		if (reportId.getWeek() < 10) {
//			id.append(Constants.STRING_ZERO + reportId.getWeek());
//		} else {
//			id.append(reportId.getWeek());
//		}
//		reportId.setId(Long.parseLong(id.toString()));
//		if (reportId.getUserId() != null) {
//			Key<User> key = Key.create(User.class, reportId.getUserId());
//			reportId.setUserKey(key);
//		}
//
//		reportId.setCrtTms(Calendar.getInstance().getTime());
//		return reportId;
//	}

}
