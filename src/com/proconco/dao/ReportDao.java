package com.proconco.dao;

import java.util.Calendar;

import com.proconco.entity.Report;

/**
 * The Class ReportDao.
 */
public class ReportDao extends AbstractDao<Report> {

	/**
	 * Instantiates a new group dao.
	 */
	public ReportDao() {
		super(Report.class);
	}

	/**
	 * Inits the data.
	 */
	public void initData() {
		Report report;
		ReportIdDao reportIdDao = new ReportIdDao();
		for (Long i = 1L; i < 1000; i++) {
			if (reportIdDao.find(i) != null) {
				Double amt = i * 100.0;
				report = new Report(i, amt, "marketRpt", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday",
						"sunday", "N", "crtUid", Calendar.getInstance().getTime(), "updUid", Calendar.getInstance()
								.getTime());
				persist(report);
			}

		}
		
	}
	
	/**
	 * Clean data.
	 */
	public void cleanData() {
		for (Long i = 1L; i < 1000; i++) {
			delete(find(i));
		}
	}
}
