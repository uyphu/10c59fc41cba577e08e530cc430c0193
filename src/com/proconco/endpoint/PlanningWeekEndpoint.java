package com.proconco.endpoint;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.proconco.dao.PlanningWeekDao;
import com.proconco.entity.PlanningWeek;

@Api(name = "planningweekendpoint", namespace = @ApiNamespace(ownerDomain = "proconco.com", ownerName = "proconco.com", packagePath = "entity"))
public class PlanningWeekEndpoint {

	/**
	* Return a collection of planningWeeks
	*
	* @param count The number of planningWeeks
	* @return a list of PlanningWeeks
	*/
	@ApiMethod(name = "listPlanningWeek")
	public CollectionResponse<PlanningWeek> listPlanningWeek(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("count") Integer count) {
		PlanningWeekDao dao = new PlanningWeekDao();
		return dao.list(cursorString, count);
	}
	
	/**
	* This inserts a new <code>PlanningWeek</code> object.
	* @param planningWeek The object to be added.
	* @return The object to be added.
	*/
	@ApiMethod(name = "insertPlanningWeek")
	public PlanningWeek insertPlanningWeek(PlanningWeek planningWeek) throws ConflictException {
		// If if is not null, then check if it exists. If yes, throw an
		// Exception
		// that it is already present
		if (planningWeek.getId() != null) {
			if (planningWeek.getId() == 0) {
				planningWeek.setId(null);
			} else {
				if (findRecord(planningWeek.getId()) != null) {
					throw new ConflictException("Object already exists");
				}
			}
		}
		// Since our @Id field is a Long, Objectify will generate a unique value
		// for us
		// when we use put
		PlanningWeekDao dao = new PlanningWeekDao();
		dao.persist(planningWeek);
		return planningWeek;
	}

	/**
	 * This updates an existing <code>PlanningWeek</code> object.
	 * 
	 * @param planningWeek
	 *            The object to be added.
	 * @return The object to be updated.
	 */
	@ApiMethod(name = "updatePlanningWeek")
	public PlanningWeek updatePlanningWeek(PlanningWeek planningWeek) throws NotFoundException {
		if (findRecord(planningWeek.getId()) == null) {
			throw new NotFoundException("PlanningWeek Record does not exist");
		}
		PlanningWeekDao dao = new PlanningWeekDao();
		dao.update(planningWeek);
		return planningWeek;
	}

	/**
	 * This deletes an existing <code>PlanningWeek</code> object.
	 * 
	 * @param id
	 *            The id of the object to be deleted.
	 */
	@ApiMethod(name = "removePlanningWeek")
	public void removePlanningWeek(@Named("id") Long id) throws NotFoundException {
		PlanningWeek record = findRecord(id);
		if (record == null) {
			throw new NotFoundException("PlanningWeek Record does not exist");
		}
		PlanningWeekDao dao = new PlanningWeekDao();
		dao.delete(record);
	}
	
	/**
	 * Gets the planningWeek.
	 *
	 * @param id the id
	 * @return the planningWeek
	 */
	@ApiMethod(name = "getPlanningWeek")
	public PlanningWeek getPlanningWeek(@Named("id") Long id) {
		return findRecord(id);
	}
	
	/**
	 * Find record.
	 *
	 * @param id the id
	 * @return the user main
	 */
	private PlanningWeek findRecord(Long id) {
		PlanningWeekDao dao = new PlanningWeekDao();
		return dao.find(id);
	}
	
	/**
	 * Inits the data.
	 */
	@ApiMethod(name = "initData")
	public void initData() {
		PlanningWeekDao dao = new PlanningWeekDao();
		dao.initData();
	}
	
	/**
	 * Clean data.
	 */
	@ApiMethod(name = "cleanData")
	public void cleanData() {
		PlanningWeekDao dao = new PlanningWeekDao();
		dao.cleanData();
	}

}
