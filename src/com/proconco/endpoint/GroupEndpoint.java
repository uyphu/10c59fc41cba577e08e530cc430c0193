package com.proconco.endpoint;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.proconco.dao.GroupDao;
import com.proconco.entity.Group;

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
	public Group insertGroup(Group group) throws ConflictException {
		// If if is not null, then check if it exists. If yes, throw an
		// Exception
		// that it is already present
		if (group.getId() != null) {
			if (group.getId() == 0) {
				group.setId(null);
			} else {
				if (findRecord(group.getId()) != null) {
					throw new ConflictException("Object already exists");
				}
			}
		}
		// Since our @Id field is a Long, Objectify will generate a unique value
		// for us
		// when we use put
		GroupDao dao = new GroupDao();
		dao.persist(group);
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
	public Group updateGroup(Group group) throws NotFoundException {
		if (findRecord(group.getId()) == null) {
			throw new NotFoundException("Group Record does not exist");
		}
		GroupDao dao = new GroupDao();
		dao.update(group);
		return group;
	}

	/**
	 * This deletes an existing <code>Group</code> object.
	 * 
	 * @param id
	 *            The id of the object to be deleted.
	 */
	@ApiMethod(name = "removeGroup")
	public void removeGroup(@Named("id") Long id) throws NotFoundException {
		Group record = findRecord(id);
		if (record == null) {
			throw new NotFoundException("Group Record does not exist");
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

}