package com.proconco.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.cmd.Query;
import com.proconco.constants.AuthoritiesConstants;
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
		
		authority = new Authority(1L, "ROLE_ADMIN");
		persist(authority);
		authority = new Authority(2L, "ROLE_USER");
		persist(authority);
		authority = new Authority(3L, "ROLE_ANONYMOUS");
		persist(authority);
		authority = new Authority(4L, "READ_ONLY");
		persist(authority);
		authority = new Authority(5L, "READ_ALL");
		persist(authority);
		authority = new Authority(6L, "EDIT");
		persist(authority);
		authority = new Authority(7L, "APPROVAL");
		persist(authority);
		authority = new Authority(8L, "MANAGER");
		persist(authority);
		authority = new Authority(9L, AuthoritiesConstants.TEAM_LEADER);
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
