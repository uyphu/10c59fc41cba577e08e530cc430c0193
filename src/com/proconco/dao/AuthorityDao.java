package com.proconco.dao;

import java.util.List;

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
