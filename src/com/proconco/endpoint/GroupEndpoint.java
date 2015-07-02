package com.proconco.endpoint;

import java.util.Calendar;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.proconco.dao.GroupDao;
import com.proconco.dao.UserDao;
import com.proconco.entity.Group;
import com.proconco.entity.User;
import com.proconco.exception.ErrorCode;
import com.proconco.exception.ErrorCodeDetail;
import com.proconco.exception.ProconcoException;

@Api(name = "groupendpoint", namespace = @ApiNamespace(ownerDomain = "proconco.com", ownerName = "proconco.com", packagePath = "entity"))
public class GroupEndpoint {

	/**
	* Return a collection of groups
	*
	* @param count The number of groups
	* @return a list of Groups
	*/
	@ApiMethod(name = "listGroup")
	public CollectionResponse<Group> listGroup(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("count") Integer count) {
		GroupDao dao = new GroupDao();
		return dao.list(cursorString, count);
	}
	
	/**
	* This inserts a new <code>Group</code> object.
	* @param group The object to be added.
	* @return The object to be added.
	*/
	@ApiMethod(name = "insertGroup")
	public Group insertGroup(Group group) throws ProconcoException {
		// If if is not null, then check if it exists. If yes, throw an
		// Exception
		// that it is already present
		if (group.getId() != null) {
			if (group.getId() == 0) {
				group.setId(null);
			} else {
				if (findRecord(group.getId()) != null) {
					throw new ProconcoException(ErrorCode.CONFLICT_EXCEPTION,
							ErrorCodeDetail.ERROR_EXIST_OBJECT);
				}
			}
		}
		// Since our @Id field is a Long, Objectify will generate a unique value
		// for us
		// when we use put
		GroupDao dao = new GroupDao();
		Group pos = dao.getGroupByName(group.getGrpName());
		if (pos == null) {
			group.setCrtTms(Calendar.getInstance().getTime());
			group.setUpdTms(Calendar.getInstance().getTime());
			dao.persist(group);
		} else {
			throw new ProconcoException(ErrorCode.CONFLICT_EXCEPTION,
					ErrorCodeDetail.ERROR_EXIST_OBJECT);
		}
		return group;
	}

	/**
	 * This updates an existing <code>Group</code> object.
	 * 
	 * @param group
	 *            The object to be added.
	 * @return The object to be updated.
	 */
	@ApiMethod(name = "updateGroup")
	public Group updateGroup(Group group) throws ProconcoException {
		Group oldGroup = findRecord(group.getId());
		if (oldGroup == null) {
			throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION,
					ErrorCodeDetail.ERROR_RECORD_NOT_FOUND);
		}
		GroupDao dao = new GroupDao();
		Group pos = dao.getGroupByName(group.getGrpName());
		if (pos == null || pos.getId().equals(group.getId())) {
			oldGroup.setGrpName(group.getGrpName());
			oldGroup.setUpdTms(Calendar.getInstance().getTime());
			oldGroup.setUpdUid(group.getUpdUid());
			if (group.getManager() != null) {
				UserDao userDao = new UserDao();
				User manager = userDao.getUserByLogin(group.getManager());
				if (manager != null) {
					group.setManager(manager.getLogin());
				} else {
					throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION,
							ErrorCodeDetail.ERROR_USER_NOT_FOUND);
				}
			}
			dao.update(group);
		} else {
			throw new ProconcoException(ErrorCode.CONFLICT_EXCEPTION,
					ErrorCodeDetail.ERROR_EXIST_OBJECT);
		}
		return group;
	}

	/**
	 * This deletes an existing <code>Group</code> object.
	 *
	 * @param id            The id of the object to be deleted.
	 * @throws ProconcoException the proconco exception
	 */
	@ApiMethod(name = "removeGroup")
	public void removeGroup(@Named("id") Long id) throws ProconcoException {
		Group record = findRecord(id);
		if (record == null) {
			throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION,
					ErrorCodeDetail.ERROR_RECORD_NOT_FOUND);
		}
		GroupDao dao = new GroupDao();
		dao.delete(record);
	}
	
	/**
	 * Gets the group.
	 *
	 * @param id the id
	 * @return the group
	 */
	@ApiMethod(name = "getGroup")
	public Group getGroup(@Named("id") Long id) {
		return findRecord(id);
	}
	
	/**
	 * Find record.
	 *
	 * @param id the id
	 * @return the user main
	 */
	private Group findRecord(Long id) {
		GroupDao dao = new GroupDao();
		return dao.find(id);
	}
	
	/**
	 * Inits the data.
	 */
	@ApiMethod(name = "initData")
	public void initData() {
		GroupDao dao = new GroupDao();
		dao.initData();
	}
	
	/**
	 * Clean data.
	 */
	@ApiMethod(name = "cleanData")
	public void cleanData() {
		GroupDao dao = new GroupDao();
		dao.cleanData();
	}
	
	@ApiMethod(name = "searchGroup", httpMethod=HttpMethod.GET, path="search_group")
	public CollectionResponse<Group> searchGroup(
			@Nullable @Named("querySearch") String querySearch,
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("count") Integer count) throws ProconcoException {
		GroupDao dao = new GroupDao();
		return dao.searchGroup(querySearch, cursorString, count);
	}
}
