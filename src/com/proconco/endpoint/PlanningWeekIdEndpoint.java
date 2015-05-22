package com.proconco.endpoint;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.proconco.dao.PlanningWeekIdDao;
import com.proconco.entity.PlanningWeekId;

@Api(name = "planningweekidendpoint", namespace = @ApiNamespace(ownerDomain = "proconco.com", ownerName = "proconco.com", packagePath = "entity"))
public class PlanningWeekIdEndpoint {

	/**
	* Return a collection of planningWeekIds
	*
	* @param count The number of planningWeekIds
	* @return a list of PlanningWeekIds
	*/
	@ApiMethod(name = "listPlanningWeekId")
	public CollectionResponse<PlanningWeekId> listPlanningWeekId(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("count") Integer count) {
		PlanningWeekIdDao dao = new PlanningWeekIdDao();
		return dao.list(cursorString, count);
	}
	
	/**
	* This inserts a new <code>PlanningWeekId</code> object.
	* @param planningWeekId The object to be added.
	* @return The object to be added.
	*/
	@ApiMethod(name = "insertPlanningWeekId")
	public PlanningWeekId insertPlanningWeekId(PlanningWeekId planningWeekId) throws ConflictException {
		// If if is not null, then check if it exists. If yes, throw an
		// Exception
		// that it is already present
		if (planningWeekId.getId() != null) {
			if (planningWeekId.getId() == 0) {
				planningWeekId.setId(null);
			} else {
				if (findRecord(planningWeekId.getId()) != null) {
					throw new ConflictException("Object already exists");
				}
			}
		}
		// Since our @Id field is a Long, Objectify will generate a unique value
		// for us
		// when we use put
		PlanningWeekIdDao dao = new PlanningWeekIdDao();
		dao.persist(planningWeekId);
		return planningWeekId;
	}

	/**
	 * This updates an existing <code>PlanningWeekId</code> object.
	 * 
	 * @param planningWeekId
	 *            The object to be added.
	 * @return The object to be updated.
	 */
	@ApiMethod(name = "updatePlanningWeekId")
	public PlanningWeekId updatePlanningWeekId(PlanningWeekId planningWeekId) throws NotFoundException {
		if (findRecord(planningWeekId.getId()) == null) {
			throw new NotFoundException("PlanningWeekId Record does not exist");
		}
		PlanningWeekIdDao dao = new PlanningWeekIdDao();
		dao.update(planningWeekId);
		return planningWeekId;
	}

	/**
	 * This deletes an existing <code>PlanningWeekId</code> object.
	 * 
	 * @param id
	 *            The id of the object to be deleted.
	 */
	@ApiMethod(name = "removePlanningWeekId")
	public void removePlanningWeekId(@Named("id") Long id) throws NotFoundException {
		PlanningWeekId record = findRecord(id);
		if (record == null) {
			throw new NotFoundException("PlanningWeekId Record does not exist");
		}
		PlanningWeekIdDao dao = new PlanningWeekIdDao();
		dao.delete(record);
	}
	
	/**
	 * Gets the planningWeekId.
	 *
	 * @param id the id
	 * @return the planningWeekId
	 */
	@ApiMethod(name = "getPlanningWeekId")
	public PlanningWeekId getPlanningWeekId(@Named("id") Long id) {
		return findRecord(id);
	}
	
	/**
	 * Find record.
	 *
	 * @param id the id
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

}
