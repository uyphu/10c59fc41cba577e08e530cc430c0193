package com.proconco.endpoint;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.proconco.dao.SessionInfoDao;
import com.proconco.entity.SessionInfo;

@Api(name = "sessioninfoendpoint", namespace = @ApiNamespace(ownerDomain = "proconco.com", ownerName = "proconco.com", packagePath = "entity"))
public class SessionInfoEndpoint {

	/**
	* Return a collection of sessionInfos
	*
	* @param count The number of sessionInfos
	* @return a list of SessionInfos
	*/
	@ApiMethod(name = "listSessionInfo")
	public CollectionResponse<SessionInfo> listSessionInfo(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("count") Integer count) {
		SessionInfoDao dao = new SessionInfoDao();
		return dao.list(cursorString, count);
	}
	
	/**
	* This inserts a new <code>SessionInfo</code> object.
	* @param sessionInfo The object to be added.
	* @return The object to be added.
	*/
	@ApiMethod(name = "insertSessionInfo")
	public SessionInfo insertSessionInfo(SessionInfo sessionInfo) throws ConflictException {
		// If if is not null, then check if it exists. If yes, throw an
		// Exception
		// that it is already present
		if (sessionInfo.getId() != null) {
			if (sessionInfo.getId() == 0) {
				sessionInfo.setId(null);
			} else {
				if (findRecord(sessionInfo.getId()) != null) {
					throw new ConflictException("Object already exists");
				}
			}
		}
		// Since our @Id field is a Long, Objectify will generate a unique value
		// for us
		// when we use put
		SessionInfoDao dao = new SessionInfoDao();
		dao.persist(sessionInfo);
		return sessionInfo;
	}

	/**
	 * This updates an existing <code>SessionInfo</code> object.
	 * 
	 * @param sessionInfo
	 *            The object to be added.
	 * @return The object to be updated.
	 */
	@ApiMethod(name = "updateSessionInfo")
	public SessionInfo updateSessionInfo(SessionInfo sessionInfo) throws NotFoundException {
		if (findRecord(sessionInfo.getId()) == null) {
			throw new NotFoundException("SessionInfo Record does not exist");
		}
		SessionInfoDao dao = new SessionInfoDao();
		dao.update(sessionInfo);
		return sessionInfo;
	}

	/**
	 * This deletes an existing <code>SessionInfo</code> object.
	 * 
	 * @param id
	 *            The id of the object to be deleted.
	 */
	@ApiMethod(name = "removeSessionInfo")
	public void removeSessionInfo(@Named("id") Long id) throws NotFoundException {
		SessionInfo record = findRecord(id);
		if (record == null) {
			throw new NotFoundException("SessionInfo Record does not exist");
		}
		SessionInfoDao dao = new SessionInfoDao();
		dao.delete(record);
	}
	
	/**
	 * Gets the sessionInfo.
	 *
	 * @param id the id
	 * @return the sessionInfo
	 */
	@ApiMethod(name = "getSessionInfo")
	public SessionInfo getSessionInfo(@Named("id") Long id) {
		return findRecord(id);
	}
	
	/**
	 * Find record.
	 *
	 * @param id the id
	 * @return the user main
	 */
	private SessionInfo findRecord(Long id) {
		SessionInfoDao dao = new SessionInfoDao();
		return dao.find(id);
	}
	
	/**
	 * Inits the data.
	 */
	@ApiMethod(name = "initData")
	public void initData() {
		SessionInfoDao dao = new SessionInfoDao();
		dao.initData();
	}
	
	/**
	 * Clean data.
	 */
	@ApiMethod(name = "cleanData")
	public void cleanData() {
		SessionInfoDao dao = new SessionInfoDao();
		dao.cleanData();
	}

}
