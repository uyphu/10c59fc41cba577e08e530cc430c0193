package com.proconco.endpoint;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.proconco.dao.ReportDao;
import com.proconco.entity.Report;

@Api(name = "reportendpoint", namespace = @ApiNamespace(ownerDomain = "proconco.com", ownerName = "proconco.com", packagePath = "entity"))
public class ReportEndpoint {

	/**
	* Return a collection of reports
	*
	* @param count The number of reports
	* @return a list of Reports
	*/
	@ApiMethod(name = "listReport")
	public CollectionResponse<Report> listReport(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("count") Integer count) {
		ReportDao dao = new ReportDao();
		return dao.list(cursorString, count);
	}
	
	/**
	* This inserts a new <code>Report</code> object.
	* @param report The object to be added.
	* @return The object to be added.
	*/
	@ApiMethod(name = "insertReport")
	public Report insertReport(Report report) throws ConflictException {
		// If if is not null, then check if it exists. If yes, throw an
		// Exception
		// that it is already present
		if (report.getId() != null) {
			if (report.getId() == 0) {
				report.setId(null);
			} else {
				if (findRecord(report.getId()) != null) {
					throw new ConflictException("Object already exists");
				}
			}
		}
		// Since our @Id field is a Long, Objectify will generate a unique value
		// for us
		// when we use put
		ReportDao dao = new ReportDao();
		dao.persist(report);
		return report;
	}

	/**
	 * This updates an existing <code>Report</code> object.
	 * 
	 * @param report
	 *            The object to be added.
	 * @return The object to be updated.
	 */
	@ApiMethod(name = "updateReport")
	public Report updateReport(Report report) throws NotFoundException {
		if (findRecord(report.getId()) == null) {
			throw new NotFoundException("Report Record does not exist");
		}
		ReportDao dao = new ReportDao();
		dao.update(report);
		return report;
	}

	/**
	 * This deletes an existing <code>Report</code> object.
	 * 
	 * @param id
	 *            The id of the object to be deleted.
	 */
	@ApiMethod(name = "removeReport")
	public void removeReport(@Named("id") Long id) throws NotFoundException {
		Report record = findRecord(id);
		if (record == null) {
			throw new NotFoundException("Report Record does not exist");
		}
		ReportDao dao = new ReportDao();
		dao.delete(record);
	}
	
	/**
	 * Gets the report.
	 *
	 * @param id the id
	 * @return the report
	 */
	@ApiMethod(name = "getReport")
	public Report getReport(@Named("id") Long id) {
		return findRecord(id);
	}
	
	/**
	 * Find record.
	 *
	 * @param id the id
	 * @return the user main
	 */
	private Report findRecord(Long id) {
		ReportDao dao = new ReportDao();
		return dao.find(id);
	}
	
	/**
	 * Inits the data.
	 */
	@ApiMethod(name = "initData")
	public void initData() {
		ReportDao dao = new ReportDao();
		dao.initData();
	}
	
	/**
	 * Clean data.
	 */
	@ApiMethod(name = "cleanData")
	public void cleanData() {
		ReportDao dao = new ReportDao();
		dao.cleanData();
	}

}
