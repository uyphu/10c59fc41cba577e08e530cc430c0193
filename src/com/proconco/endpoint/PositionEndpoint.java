package com.proconco.endpoint;

import java.util.Calendar;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.proconco.dao.PositionDao;
import com.proconco.entity.Position;

@Api(name = "positionendpoint", namespace = @ApiNamespace(ownerDomain = "proconco.com", ownerName = "proconco.com", packagePath = "entity"))
public class PositionEndpoint {

	/**
	* Return a collection of positions
	*
	* @param count The number of positions
	* @return a list of Positions
	*/
	@ApiMethod(name = "listPosition")
	public CollectionResponse<Position> listPosition(
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("count") Integer count) {
		PositionDao dao = new PositionDao();
		return dao.list(cursorString, count);
	}
	
	/**
	* This inserts a new <code>Position</code> object.
	* @param position The object to be added.
	* @return The object to be added.
	*/
	@ApiMethod(name = "insertPosition")
	public Position insertPosition(Position position) throws ConflictException {
		// If if is not null, then check if it exists. If yes, throw an
		// Exception
		// that it is already present
		if (position.getId() != null) {
			if (position.getId() == 0) {
				position.setId(null);
			} else {
				if (findRecord(position.getId()) != null) {
					throw new ConflictException("Object already exists");
				}
			}
		}
		// Since our @Id field is a Long, Objectify will generate a unique value
		// for us
		// when we use put
		PositionDao dao = new PositionDao();
		position.setCrtTms(Calendar.getInstance().getTime());
		position.setUpdTms(Calendar.getInstance().getTime());
		dao.persist(position);
		return position;
	}

	/**
	 * This updates an existing <code>Position</code> object.
	 * 
	 * @param position
	 *            The object to be added.
	 * @return The object to be updated.
	 */
	@ApiMethod(name = "updatePosition")
	public Position updatePosition(Position position) throws NotFoundException {
		if (findRecord(position.getId()) == null) {
			throw new NotFoundException("Position Record does not exist");
		}
		PositionDao dao = new PositionDao();
		dao.update(position);
		position.setUpdTms(Calendar.getInstance().getTime());
		return position;
	}

	/**
	 * This deletes an existing <code>Position</code> object.
	 * 
	 * @param id
	 *            The id of the object to be deleted.
	 */
	@ApiMethod(name = "removePosition")
	public void removePosition(@Named("id") Long id) throws NotFoundException {
		Position record = findRecord(id);
		if (record == null) {
			throw new NotFoundException("Position Record does not exist");
		}
		PositionDao dao = new PositionDao();
		dao.delete(record);
	}
	
	/**
	 * Gets the position.
	 *
	 * @param id the id
	 * @return the position
	 */
	@ApiMethod(name = "getPosition")
	public Position getPosition(@Named("id") Long id) {
		return findRecord(id);
	}
	
	/**
	 * Find record.
	 *
	 * @param id the id
	 * @return the user main
	 */
	private Position findRecord(Long id) {
		PositionDao dao = new PositionDao();
		return dao.find(id);
	}
	
	/**
	 * Inits the data.
	 */
	@ApiMethod(name = "initData")
	public void initData() {
		PositionDao dao = new PositionDao();
		dao.initData();
	}
	
	/**
	 * Clean data.
	 */
	@ApiMethod(name = "cleanData")
	public void cleanData() {
		PositionDao dao = new PositionDao();
		dao.cleanData();
	}
	
	@ApiMethod(name = "searchPosition", httpMethod=HttpMethod.GET, path="search_position")
	public CollectionResponse<Position> searchPosition(
			@Nullable @Named("querySearch") String querySearch,
			@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("count") Integer count) throws NotFoundException {
		PositionDao dao = new PositionDao();
		return dao.searchPosition(querySearch, cursorString, count);
	}

}
