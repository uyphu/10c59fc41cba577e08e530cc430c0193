package com.proconco.endpoint;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.proconco.dao.ReportIdDao;
import com.proconco.entity.ReportId;

@Api(name = "reportidendpoint", namespace = @ApiNamespace(ownerDomain = "proconco.com", ownerName = "proconco.com", packagePath = "entity"))
public class ReportIdEndpoint {

	/**
	* Return a collection of reportIds
	*
	* @param count The number of reportIds
	* @return a list of ReportIds
	*/
	@ApiMethod(name = "listReportId")
	public CollectionResponse<ReportId> listReportId(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("count") Integer count) {
		ReportIdDao dao = new ReportIdDao();
		return dao.list(cursorString, count);
	}
	
	/**
	* This inserts a new <code>ReportId</code> object.
	* @param reportId The object to be added.
	* @return The object to be added.
	*/
	@ApiMethod(name = "insertReportId")
	public ReportId insertReportId(ReportId reportId) throws ConflictException {
		// If if is not null, then check if it exists. If yes, throw an
		// Exception
		// that it is already present
		if (reportId.getId() != null) {
			if (reportId.getId() == 0) {
				reportId.setId(null);
			} else {
				if (findRecord(reportId.getId()) != null) {
					throw new ConflictException("Object already exists");
				}
			}
		}
		// Since our @Id field is a Long, Objectify will generate a unique value
		// for us
		// when we use put
		ReportIdDao dao = new ReportIdDao();
		dao.persist(reportId);
		return reportId;
	}

	/**
	 * This updates an existing <code>ReportId</code> object.
	 * 
	 * @param reportId
	 *            The object to be added.
	 * @return The object to be updated.
	 */
	@ApiMethod(name = "updateReportId")
	public ReportId updateReportId(ReportId reportId) throws NotFoundException {
		if (findRecord(reportId.getId()) == null) {
			throw new NotFoundException("ReportId Record does not exist");
		}
		ReportIdDao dao = new ReportIdDao();
		dao.update(reportId);
		return reportId;
	}

	/**
	 * This deletes an existing <code>ReportId</code> object.
	 * 
	 * @param id
	 *            The id of the object to be deleted.
	 */
	@ApiMethod(name = "removeReportId")
	public void removeReportId(@Named("id") Long id) throws NotFoundException {
		ReportId record = findRecord(id);
		if (record == null) {
			throw new NotFoundException("ReportId Record does not exist");
		}
		ReportIdDao dao = new ReportIdDao();
		dao.delete(record);
	}
	
	/**
	 * Gets the reportId.
	 *
	 * @param id the id
	 * @return the reportId
	 */
	@ApiMethod(name = "getReportId")
	public ReportId getReportId(@Named("id") Long id) {
		return findRecord(id);
	}
	
	/**
	 * Find record.
	 *
	 * @param id the id
	 * @return the user main
	 */
	private ReportId findRecord(Long id) {
		ReportIdDao dao = new ReportIdDao();
		return dao.find(id);
	}
	
	/**
	 * Inits the data.
	 */
	@ApiMethod(name = "initData")
	public void initData() {
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

}
