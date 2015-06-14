package com.proconco.endpoint;

import java.util.Calendar;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.googlecode.objectify.Key;
import com.proconco.constants.Constants;
import com.proconco.dao.PlanningWeekIdDao;
import com.proconco.entity.PlanningWeekId;
import com.proconco.entity.User;
import com.proconco.exception.ErrorCode;
import com.proconco.exception.ErrorCodeDetail;
import com.proconco.exception.ProconcoException;

@Api(name = "planningweekidendpoint", namespace = @ApiNamespace(ownerDomain = "proconco.com", ownerName = "proconco.com", packagePath = "entity"))
public class PlanningWeekIdEndpoint {

	/**
	 * Return a collection of planningWeekIds
	 * 
	 * @param count
	 *            The number of planningWeekIds
	 * @return a list of PlanningWeekIds
	 */
	@ApiMethod(name = "listPlanningWeekId")
	public CollectionResponse<PlanningWeekId> listPlanningWeekId(@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("count") Integer count) {
		PlanningWeekIdDao dao = new PlanningWeekIdDao();
		return dao.list(cursorString, count);
	}

	/**
	 * This inserts a new <code>PlanningWeekId</code> object.
	 *
	 * @param planningWeekId            The object to be added.
	 * @return The object to be added.
	 * @throws ProconcoException the proconco exception
	 */
	@ApiMethod(name = "insertPlanningWeekId")
	public PlanningWeekId insertPlanningWeekId(PlanningWeekId planningWeekId) throws ProconcoException {
		try {
			// If if is not null, then check if it exists. If yes, throw an
			// Exception
			// that it is already present
			if (planningWeekId.getId() != null) {
				if (planningWeekId.getId() == 0) {
					planningWeekId = createId(planningWeekId);
				} else {
					if (findRecord(planningWeekId.getId()) != null) {
						throw new ProconcoException(ErrorCode.CONFLICT_EXCEPTION.getId(),
								ErrorCodeDetail.ERROR_EXIST_OBJECT.getMsg());
					}
				}
			} else {
				planningWeekId = createId(planningWeekId);
			}
			// Since our @Id field is a Long, Objectify will generate a unique
			// value
			// for us
			// when we use put
			PlanningWeekIdDao dao = new PlanningWeekIdDao();
			PlanningWeekId rep = dao.find(planningWeekId.getId());
			if (rep == null) {
				dao.insert(planningWeekId);
			} else {
				throw new ProconcoException(ErrorCode.CONFLICT_EXCEPTION.getId(),
						ErrorCodeDetail.ERROR_EXIST_OBJECT.getMsg());
			}
			return planningWeekId;
		} catch (Exception e) {
			throw new ProconcoException(ErrorCode.SYSTEM_EXCEPTION.getId(),
					ErrorCodeDetail.ERROR_INSERT_ENTITY.getMsg() + Constants.STRING_EXEPTION_DETAIL + e.getMessage());
		}

	}

	/**
	 * This updates an existing <code>PlanningWeekId</code> object.
	 *
	 * @param planningWeekId            The object to be added.
	 * @return The object to be updated.
	 * @throws ProconcoException the proconco exception
	 */
	@ApiMethod(name = "updatePlanningWeekId")
	public PlanningWeekId updatePlanningWeekId(PlanningWeekId planningWeekId) throws ProconcoException {
		try {
			if (findRecord(planningWeekId.getId()) == null) {
				throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION.getId(),
						ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
			}
			PlanningWeekIdDao dao = new PlanningWeekIdDao();
			PlanningWeekId rep = findRecord(planningWeekId.getId());
			if (rep != null) {
				dao.update(rep);
			} else {
				throw new ProconcoException(ErrorCode.CONFLICT_EXCEPTION.getId(),
						ErrorCodeDetail.ERROR_EXIST_OBJECT.getMsg());
			}
			return rep;
		} catch (Exception e) {
			throw new ProconcoException(ErrorCode.SYSTEM_EXCEPTION.getId(),
					ErrorCodeDetail.ERROR_UPDATE_ENTITY.getMsg() + Constants.STRING_EXEPTION_DETAIL + e.getMessage());
		}
		
	}

	/**
	 * This deletes an existing <code>PlanningWeekId</code> object.
	 * 
	 * @param id
	 *            The id of the object to be deleted.
	 */
	@ApiMethod(name = "removePlanningWeekId")
	public void removePlanningWeekId(@Named("id") Long id) throws ProconcoException {
		try {
			PlanningWeekId record = findRecord(id);
			if (record == null) {
				throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION.getId(),
						ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
			}
			PlanningWeekIdDao dao = new PlanningWeekIdDao();
			dao.remove(record);
		} catch (Exception e) {
			throw new ProconcoException(ErrorCode.SYSTEM_EXCEPTION.getId(),
					ErrorCodeDetail.ERROR_REMOVE_ENTITY.getMsg() + Constants.STRING_EXEPTION_DETAIL + e.getMessage());
		}

	}

	/**
	 * Gets the planningWeekId.
	 * 
	 * @param id
	 *            the id
	 * @return the planningWeekId
	 */
	@ApiMethod(name = "getPlanningWeekId")
	public PlanningWeekId getPlanningWeekId(@Named("id") Long id) {
		return findRecord(id);
	}

	/**
	 * Gets the planningWeekId by name.
	 * 
	 * @param name
	 *            the name
	 * @return the planningWeekId by name
	 */
	@ApiMethod(name = "getPlanningWeekIdByName", httpMethod = HttpMethod.GET, path = "get_planningWeekId_by_name")
	public PlanningWeekId getPlanningWeekIdByName(@Named("name") String name) throws ProconcoException {
		PlanningWeekIdDao dao = new PlanningWeekIdDao();
		PlanningWeekId planningWeekId = dao.getPlanningWeekIdByName(name);
		if (planningWeekId == null) {
			throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION.getId(),
					ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
		}
		return planningWeekId;
	}

	/**
	 * Find record.
	 * 
	 * @param id
	 *            the id
	 * @return the user main
	 */
	private PlanningWeekId findRecord(Long id) {
		PlanningWeekIdDao dao = new PlanningWeekIdDao();
		return dao.find(id);
	}

	/**
	 * Inits the data.
	 */
	@ApiMethod(name = "initData")
	public void initData() {
		PlanningWeekIdDao dao = new PlanningWeekIdDao();
		dao.initData();
	}

	/**
	 * Clean data.
	 */
	@ApiMethod(name = "cleanData")
	public void cleanData() {
		PlanningWeekIdDao dao = new PlanningWeekIdDao();
		dao.cleanData();
	}

	@ApiMethod(name = "searchPlanningWeekId", httpMethod = HttpMethod.GET, path = "search_planningWeekId")
	public CollectionResponse<PlanningWeekId> searchPlanningWeekId(@Nullable @Named("querySearch") String querySearch,
			@Nullable @Named("cursor") String cursorString, @Nullable @Named("count") Integer count)
			throws ProconcoException {
		PlanningWeekIdDao dao = new PlanningWeekIdDao();
		return dao.searchPlanningWeekId(querySearch, cursorString, count);
	}

	/**
	 * Creates the id.
	 * 
	 * @param planningWeekId
	 *            the planningWeek id
	 * @return the planningWeek id
	 */
	private PlanningWeekId createId(PlanningWeekId planningWeekId) {
		StringBuilder id = new StringBuilder();
		id.append(planningWeekId.getYear());
		if (planningWeekId.getWeek() < 10) {
			id.append(Constants.STRING_ZERO + planningWeekId.getWeek());
		} else {
			id.append(planningWeekId.getWeek());
		}
		planningWeekId.setId(Long.parseLong(id.toString()));
		if (planningWeekId.getUserId() != null) {
			Key<User> key = Key.create(User.class, planningWeekId.getUserId());
			planningWeekId.setUserKey(key);
		}

		planningWeekId.setCrtTms(Calendar.getInstance().getTime());
		return planningWeekId;
	}

}
