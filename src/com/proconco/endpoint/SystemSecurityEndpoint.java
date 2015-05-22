package com.proconco.endpoint;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.proconco.dao.SystemSecurityDao;
import com.proconco.entity.SystemSecurity;

@Api(name = "systemsecurityendpoint", namespace = @ApiNamespace(ownerDomain = "proconco.com", ownerName = "proconco.com", packagePath = "entity"))
public class SystemSecurityEndpoint {

	/**
	* Return a collection of systemSecuritys
	*
	* @param count The number of systemSecuritys
	* @return a list of SystemSecuritys
	*/
	@ApiMethod(name = "listSystemSecurity")
	public CollectionResponse<SystemSecurity> listSystemSecurity(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("count") Integer count) {
		SystemSecurityDao dao = new SystemSecurityDao();
		return dao.list(cursorString, count);
	}
	
	/**
	* This inserts a new <code>SystemSecurity</code> object.
	* @param systemSecurity The object to be added.
	* @return The object to be added.
	*/
	@ApiMethod(name = "insertSystemSecurity")
	public SystemSecurity insertSystemSecurity(SystemSecurity systemSecurity) throws ConflictException {
		// If if is not null, then check if it exists. If yes, throw an
		// Exception
		// that it is already present
		if (systemSecurity.getId() != null) {
			if (systemSecurity.getId() == 0) {
				systemSecurity.setId(null);
			} else {
				if (findRecord(systemSecurity.getId()) != null) {
					throw new ConflictException("Object already exists");
				}
			}
		}
		// Since our @Id field is a Long, Objectify will generate a unique value
		// for us
		// when we use put
		SystemSecurityDao dao = new SystemSecurityDao();
		dao.persist(systemSecurity);
		return systemSecurity;
	}

	/**
	 * This updates an existing <code>SystemSecurity</code> object.
	 * 
	 * @param systemSecurity
	 *            The object to be added.
	 * @return The object to be updated.
	 */
	@ApiMethod(name = "updateSystemSecurity")
	public SystemSecurity updateSystemSecurity(SystemSecurity systemSecurity) throws NotFoundException {
		if (findRecord(systemSecurity.getId()) == null) {
			throw new NotFoundException("SystemSecurity Record does not exist");
		}
		SystemSecurityDao dao = new SystemSecurityDao();
		dao.update(systemSecurity);
		return systemSecurity;
	}

	/**
	 * This deletes an existing <code>SystemSecurity</code> object.
	 * 
	 * @param id
	 *            The id of the object to be deleted.
	 */
	@ApiMethod(name = "removeSystemSecurity")
	public void removeSystemSecurity(@Named("id") Long id) throws NotFoundException {
		SystemSecurity record = findRecord(id);
		if (record == null) {
			throw new NotFoundException("SystemSecurity Record does not exist");
		}
		SystemSecurityDao dao = new SystemSecurityDao();
		dao.delete(record);
	}
	
	/**
	 * Gets the systemSecurity.
	 *
	 * @param id the id
	 * @return the systemSecurity
	 */
	@ApiMethod(name = "getSystemSecurity")
	public SystemSecurity getSystemSecurity(@Named("id") Long id) {
		return findRecord(id);
	}
	
	/**
	 * Find record.
	 *
	 * @param id the id
	 * @return the user main
	 */
	private SystemSecurity findRecord(Long id) {
		SystemSecurityDao dao = new SystemSecurityDao();
		return dao.find(id);
	}
	
	/**
	 * Inits the data.
	 */
	@ApiMethod(name = "initData")
	public void initData() {
		SystemSecurityDao dao = new SystemSecurityDao();
		dao.initData();
	}
	
	/**
	 * Clean data.
	 */
	@ApiMethod(name = "cleanData")
	public void cleanData() {
		SystemSecurityDao dao = new SystemSecurityDao();
		dao.cleanData();
	}

}
