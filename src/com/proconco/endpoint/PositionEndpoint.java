package com.proconco.endpoint;

import java.util.Calendar;

import javax.annotation.Nullable;
import javax.inject.Named;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.proconco.dao.PositionDao;
import com.proconco.entity.Position;
import com.proconco.exception.ErrorCode;
import com.proconco.exception.ErrorCodeDetail;
import com.proconco.exception.ProconcoException;

@Api(name = "positionendpoint", namespace = @ApiNamespace(ownerDomain = "proconco.com", ownerName = "proconco.com", packagePath = "entity"))
public class PositionEndpoint {

	/**
	 * Return a collection of positions
	 * 
	 * @param count
	 *            The number of positions
	 * @return a list of Positions
	 */
	@ApiMethod(name = "listPosition")
	public CollectionResponse<Position> listPosition(@Nullable @Named("cursor") String cursorString,
			@Nullable @Named("count") Integer count) {
		PositionDao dao = new PositionDao();
		return dao.list(cursorString, count);
	}

	/**
	 * This inserts a new <code>Position</code> object.
	 * 
	 * @param position
	 *            The object to be added.
	 * @return The object to be added.
	 */
	@ApiMethod(name = "insertPosition")
	public Position insertPosition(Position position) throws ProconcoException {
		// If if is not null, then check if it exists. If yes, throw an
		// Exception
		// that it is already present
		if (position.getId() != null) {
			if (position.getId() == 0) {
				position.setId(null);
			} else {
				if (findRecord(position.getId()) != null) {
					throw new ProconcoException(ErrorCode.CONFLICT_EXCEPTION.getId(),
							ErrorCodeDetail.ERROR_EXIST_OBJECT.getMsg());
				}
			}
		}
		// Since our @Id field is a Long, Objectify will generate a unique value
		// for us
		// when we use put
		PositionDao dao = new PositionDao();
		Position pos = dao.getPositionByName(position.getPostName());
		if (pos == null) {
			position.setCrtTms(Calendar.getInstance().getTime());
			position.setUpdTms(Calendar.getInstance().getTime());
			dao.persist(position);
		} else {
			throw new ProconcoException(ErrorCode.CONFLICT_EXCEPTION.getId(),
					ErrorCodeDetail.ERROR_EXIST_OBJECT.getMsg());
		}
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
	public Position updatePosition(Position position) throws ProconcoException {
		if (findRecord(position.getId()) == null) {
			throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION.getId(),
					ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
		}
		PositionDao dao = new PositionDao();
		Position pos = dao.getPositionByName(position.getPostName());
		if (pos == null || (pos.getId() == position.getId())) {
			position.setUpdTms(Calendar.getInstance().getTime());
			dao.update(position);
		} else {
			throw new ProconcoException(ErrorCode.CONFLICT_EXCEPTION.getId(),
					ErrorCodeDetail.ERROR_EXIST_OBJECT.getMsg());
		}
		return position;
	}

	/**
	 * This deletes an existing <code>Position</code> object.
	 * 
	 * @param id
	 *            The id of the object to be deleted.
	 */
	@ApiMethod(name = "removePosition")
	public void removePosition(@Named("id") Long id) throws ProconcoException {
		Position record = findRecord(id);
		if (record == null) {
			throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION.getId(),
					ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
		}
		PositionDao dao = new PositionDao();
		dao.delete(record);
	}

	/**
	 * Gets the position.
	 * 
	 * @param id
	 *            the id
	 * @return the position
	 */
	@ApiMethod(name = "getPosition")
	public Position getPosition(@Named("id") Long id) {
		return findRecord(id);
	}
	
	/**
	 * Gets the position by name.
	 *
	 * @param name the name
	 * @return the position by name
	 */
	@ApiMethod(name = "getPositionByName", httpMethod = HttpMethod.GET, path = "get_position_by_name")
	public Position getPositionByName(@Named("name") String name) throws ProconcoException{
		PositionDao dao = new PositionDao();
		Position position = dao.getPositionByName(name);
		if (position == null) {
			throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION.getId(),
					ErrorCodeDetail.ERROR_RECORD_NOT_FOUND.getMsg());
		}
		return position;
	}

	/**
	 * Find record.
	 * 
	 * @param id
	 *            the id
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

	@ApiMethod(name = "searchPosition", httpMethod = HttpMethod.GET, path = "search_position")
	public CollectionResponse<Position> searchPosition(@Nullable @Named("querySearch") String querySearch,
			@Nullable @Named("cursor") String cursorString, @Nullable @Named("count") Integer count)
			throws ProconcoException {
		PositionDao dao = new PositionDao();
		return dao.searchPosition(querySearch, cursorString, count);
	}

}
