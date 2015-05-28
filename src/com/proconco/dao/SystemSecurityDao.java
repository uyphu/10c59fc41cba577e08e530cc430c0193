package com.proconco.dao;

import java.util.Calendar;
import java.util.List;

import com.proconco.entity.SystemSecurity;

/**
 * The Class SystemSecurityDao.
 */
public class SystemSecurityDao extends AbstractDao<SystemSecurity> {

	/**
	 * Instantiates a new group dao.
	 */
	public SystemSecurityDao() {
		super(SystemSecurity.class);
	}

	/**
	 * Inits the data.
	 */
	public void initData() {
		SystemSecurity systemSecurity;
		
		for (Long i = 1L; i < 1000; i++) {
			systemSecurity = new SystemSecurity(i, 6, 6, "Y", 3,  "crtUid", Calendar
					.getInstance().getTime(), "updUid", Calendar.getInstance().getTime());
			persist(systemSecurity);
		}

	}
	
	/**
	 * Clean data.
	 */
	public void cleanData() {
		List<SystemSecurity> list = findAll();
		for (SystemSecurity item : list) {
			delete(item);
		}
	}
}
