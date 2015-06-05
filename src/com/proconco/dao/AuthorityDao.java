package com.proconco.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.cmd.Query;
import com.proconco.entity.Authority;

/**
 * The Class AuthorityDao.
 */
public class AuthorityDao extends AbstractDao<Authority>{

	/**
	 * Instantiates a new authority dao.
	 */
	public AuthorityDao() {
		super(Authority.class);
	}
	
	/**
	 * Gets the authority by name.
	 *
	 * @param name the name
	 * @return the authority by name
	 */
	public Authority getAuthorityByName(String name) {
		Map<String, Object> columns = new HashMap<String, Object>();
		columns.put("name", name);
		Query<Authority> query = getQuery(columns);
		List<Authority> list = executeQuery(query, 1);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} 
		return null;
	}

	@Override
	public void initData() {
		Authority authority;
		
		authority = new Authority(1L, "Admin");
		persist(authority);
		authority = new Authority(2L, "Moderator");
		persist(authority);
		authority = new Authority(3L, "Super User");
		persist(authority);
		authority = new Authority(4L, "User");
		persist(authority);
	}

	@Override
	public void cleanData() {
		List<Authority> authorities = findAll();
		for (Authority authority : authorities) {
			delete(authority);
		}
	}

}
