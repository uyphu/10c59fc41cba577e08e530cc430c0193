package com.proconco.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.api.server.spi.response.CollectionResponse;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.Query;
import com.proconco.constants.Constants;
import com.proconco.entity.Authority;
import com.proconco.entity.Position;
import com.proconco.exception.ErrorCode;
import com.proconco.exception.ErrorCodeDetail;
import com.proconco.exception.ProconcoException;

/**
 * The Class PositionDao.
 */
public class PositionDao extends AbstractDao<Position> {

	/**
	 * Instantiates a new group dao.
	 */
	public PositionDao() {
		super(Position.class);
	}

	/**
	 * Inits the data.
	 */
	public void initData() {
		Position position;
		
		for (Long i = 1L; i < 10; i++) {
			position = new Position(i, "postName" + String.valueOf(i), "N", "crtUid" + String.valueOf(i), Calendar
					.getInstance().getTime(), "updUid" + String.valueOf(i), Calendar.getInstance().getTime());
			persist(position);
		}
		
	}
	
	/**
	 * Clean data.
	 */
	public void cleanData() {
		List<Position> list = findAll();
		for (Position item : list) {
			delete(item);
		}
	}
	
	/**
	 * Gets the query.
	 *
	 * @param querySearch the query search
	 * @return the query
	 * @throws ProconcoException the proconco exception
	 */
	public Query<Position> getQuery(String querySearch) throws ProconcoException { 
		try {
			if (querySearch != null) {
				Query<Position> query;
				Map<String,Object> map = new HashMap<String, Object>();
				if (querySearch.indexOf("delFlag:") != -1) {
					String[] queries = querySearch.split(":");
					map.put("delFlag", Long.parseLong(queries[1]));
					query = getQuery(map);
				} else {
					query = getQueryByName("postName", querySearch);
				}
				return query;
			} else {
				return ofy().load().type(Position.class);
			}
		} catch (Exception e) {
			throw new ProconcoException(ErrorCode.SYSTEM_EXCEPTION.getId(),
					ErrorCodeDetail.ERROR_PARSE_QUERY + Constants.STRING_EXEPTION_DETAIL + e.getMessage());
		}
	}
	
	/**
	 * Search position.
	 *
	 * @param querySearch the query search
	 * @param cursorString the cursor string
	 * @param count the count
	 * @return the collection response
	 * @throws ProconcoException the proconco exception
	 */
	public CollectionResponse<Position> searchPosition(String querySearch, String cursorString, Integer count)
			throws ProconcoException {
		Query<Position> query = getQuery(querySearch);
		return executeQuery(query, cursorString, count);
	}
	
	/**
	 * Gets the position by name.
	 *
	 * @param name the name
	 * @return the position by name
	 */
	public Position getPositionByName(String name) {
		if (name != null) {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("postName", name);
			Query<Position> query = getQuery(map);
			List<Position> list = executeQuery(query, 1); 
			if (list != null && list.size() > 0) {
				return list.get(0);
			}
		} 
		return null;
	}
	
	/**
	 * Adds the role.
	 *
	 * @param positionId the position id
	 * @param role the role
	 * @throws ProconcoException the proconco exception
	 */
	public void addRole(Long positionId, String role) throws ProconcoException {
		Position position = find(positionId);
		AuthorityDao authorityDao = new AuthorityDao();
		Authority authority = authorityDao.getAuthorityByName(role);
		if (position != null && authority != null) {
			Key<Authority> key = Key.create(Authority.class, authority.getId());
			List<Key<Authority>> list = position.getAuthorityKeys();
			if (list == null) {
				list = new ArrayList<Key<Authority>>();
			}
			if (!list.contains(key)) {
				list.add(key);
				position.setAuthorityKeys(list);
				persist(position);
			} else {
				throw new ProconcoException(ErrorCode.CONFLICT_EXCEPTION, ErrorCodeDetail.ERROR_DUPLICATED_ROLE);
			}
		} else {
			throw new ProconcoException(ErrorCode.NOT_FOUND_EXCEPTION, ErrorCodeDetail.ERROR_NOT_FOUND_ROLE);
		}
	}

}
