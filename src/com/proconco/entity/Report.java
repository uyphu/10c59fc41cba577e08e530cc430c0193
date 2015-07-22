package com.proconco.entity;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.IgnoreSave;
import com.googlecode.objectify.annotation.OnLoad;
import com.proconco.dao.ReportIdDao;

// TODO: Auto-generated Javadoc
/**
 * The Class Report.
 */
@Entity
public class Report {

	/** The id. */
	@Id
	private Long id;
	
	/** The amt. */
	private Double amt;
	
	/** The market rpt. */
	private String marketRpt;
	
	/** The competitors. */
	private String competitors;
	
	/** The disease. */
	private String disease;
	
	/** The monday. */
	private String monday;
	
	/** The tuesday. */
	private String tuesday;
	
	/** The wednesday. */
	private String wednesday;
	
	/** The thursday. */
	private String thursday;
	
	/** The friday. */
	private String friday;
	
	/** The saturday. */
	private String saturday;
	
	/** The sunday. */
	private String sunday;
	
	/** The del flag. */
	private String delFlag;
	
	/** The crt uid. */
	private String crtUid;
	
	/** The crt tms. */
	private Date crtTms;
	
	/** The upd uid. */
	private String updUid;
	
	/** The upd tms. */
	private Date updTms;
	
	@IgnoreSave
	private int status;
	
	@OnLoad
	public void onLoad() {
		ReportIdDao dao = new ReportIdDao();
		ReportId reportId = dao.find(this.id);
		status = reportId.getStatus();
	}
	
	/**
	 * Instantiates a new report.
	 */
	public Report() {
		
	}
	
	/**
	 * Instantiates a new report.
	 *
	 * @param id the id
	 * @param amt the amt
	 * @param marketRpt the market rpt
	 * @param competitors the competitors
	 * @param disease the disease
	 * @param monday the monday
	 * @param tuesday the tuesday
	 * @param wednesday the wednesday
	 * @param thursday the thursday
	 * @param friday the friday
	 * @param saturday the saturday
	 * @param sunday the sunday
	 * @param delFlag the del flag
	 * @param crtUid the crt uid
	 * @param crtTms the crt tms
	 * @param updUid the upd uid
	 * @param updTms the upd tms
	 */
	public Report(Long id, Double amt, String marketRpt, String competitors, String disease, String monday,
			String tuesday, String wednesday, String thursday, String friday, String saturday, String sunday,
			String delFlag, String crtUid, Date crtTms, String updUid, Date updTms) {
		this.id = id;
		this.amt = amt;
		this.marketRpt = marketRpt;
		this.competitors = competitors;
		this.disease = disease;
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;
		this.delFlag = delFlag;
		this.crtUid = crtUid;
		this.crtTms = crtTms;
		this.updUid = updUid;
		this.updTms = updTms;
	}

	/**
	 * Instantiates a new report.
	 *
	 * @param amt the amt
	 * @param marketRpt the market rpt
	 * @param competitors the competitors
	 * @param disease the disease
	 * @param monday the monday
	 * @param tuesday the tuesday
	 * @param wednesday the wednesday
	 * @param thursday the thursday
	 * @param friday the friday
	 * @param saturday the saturday
	 * @param sunday the sunday
	 * @param delFlag the del flag
	 * @param crtUid the crt uid
	 * @param crtTms the crt tms
	 * @param updUid the upd uid
	 * @param updTms the upd tms
	 */
	public Report(Double amt, String marketRpt, String competitors, String disease, String monday,
			String tuesday, String wednesday, String thursday, String friday, String saturday, String sunday,
			String delFlag, String crtUid, Date crtTms, String updUid, Date updTms) {
		this.amt = amt;
		this.marketRpt = marketRpt;
		this.competitors = competitors;
		this.disease = disease;
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;
		this.delFlag = delFlag;
		this.crtUid = crtUid;
		this.crtTms = crtTms;
		this.updUid = updUid;
		this.updTms = updTms;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public final Long getId() {
		return this.id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public final void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Gets the amt.
	 *
	 * @return the amt
	 */
	public final Double getAmt() {
		return this.amt;
	}
	
	/**
	 * Sets the amt.
	 *
	 * @param amt the new amt
	 */
	public final void setAmt(Double amt) {
		this.amt = amt;
	}
	
	/**
	 * Gets the market rpt.
	 *
	 * @return the market rpt
	 */
	public final String getMarketRpt() {
		return this.marketRpt;
	}
	
	/**
	 * Sets the market rpt.
	 *
	 * @param marketRpt the new market rpt
	 */
	public final void setMarketRpt(String marketRpt) {
		this.marketRpt = marketRpt;
	}
	
	/**
	 * Gets the monday.
	 *
	 * @return the monday
	 */
	public final String getMonday() {
		return this.monday;
	}
	
	/**
	 * Sets the monday.
	 *
	 * @param monday the new monday
	 */
	public final void setMonday(String monday) {
		this.monday = monday;
	}
	
	/**
	 * Gets the tuesday.
	 *
	 * @return the tuesday
	 */
	public final String getTuesday() {
		return this.tuesday;
	}
	
	/**
	 * Sets the tuesday.
	 *
	 * @param tuesday the new tuesday
	 */
	public final void setTuesday(String tuesday) {
		this.tuesday = tuesday;
	}
	
	/**
	 * Gets the wednesday.
	 *
	 * @return the wednesday
	 */
	public final String getWednesday() {
		return this.wednesday;
	}
	
	/**
	 * Sets the wednesday.
	 *
	 * @param wednesday the new wednesday
	 */
	public final void setWednesday(String wednesday) {
		this.wednesday = wednesday;
	}
	
	/**
	 * Gets the thursday.
	 *
	 * @return the thursday
	 */
	public final String getThursday() {
		return this.thursday;
	}
	
	/**
	 * Sets the thursday.
	 *
	 * @param thursday the new thursday
	 */
	public final void setThursday(String thursday) {
		this.thursday = thursday;
	}
	
	/**
	 * Gets the friday.
	 *
	 * @return the friday
	 */
	public final String getFriday() {
		return this.friday;
	}
	
	/**
	 * Sets the friday.
	 *
	 * @param friday the new friday
	 */
	public final void setFriday(String friday) {
		this.friday = friday;
	}
	
	/**
	 * Gets the saturday.
	 *
	 * @return the saturday
	 */
	public final String getSaturday() {
		return this.saturday;
	}
	
	/**
	 * Sets the saturday.
	 *
	 * @param saturday the new saturday
	 */
	public final void setSaturday(String saturday) {
		this.saturday = saturday;
	}
	
	/**
	 * Gets the sunday.
	 *
	 * @return the sunday
	 */
	public final String getSunday() {
		return this.sunday;
	}
	
	/**
	 * Sets the sunday.
	 *
	 * @param sunday the new sunday
	 */
	public final void setSunday(String sunday) {
		this.sunday = sunday;
	}
	
	/**
	 * Gets the del flag.
	 *
	 * @return the del flag
	 */
	public final String getDelFlag() {
		return this.delFlag;
	}
	
	/**
	 * Sets the del flag.
	 *
	 * @param delFlag the new del flag
	 */
	public final void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	/**
	 * Gets the crt uid.
	 *
	 * @return the crt uid
	 */
	public final String getCrtUid() {
		return this.crtUid;
	}
	
	/**
	 * Sets the crt uid.
	 *
	 * @param crtUid the new crt uid
	 */
	public final void setCrtUid(String crtUid) {
		this.crtUid = crtUid;
	}
	
	/**
	 * Gets the crt tms.
	 *
	 * @return the crt tms
	 */
	public final Date getCrtTms() {
		return this.crtTms;
	}
	
	/**
	 * Sets the crt tms.
	 *
	 * @param crtTms the new crt tms
	 */
	public final void setCrtTms(Date crtTms) {
		this.crtTms = crtTms;
	}
	
	/**
	 * Gets the upd uid.
	 *
	 * @return the upd uid
	 */
	public final String getUpdUid() {
		return this.updUid;
	}
	
	/**
	 * Sets the upd uid.
	 *
	 * @param updUid the new upd uid
	 */
	public final void setUpdUid(String updUid) {
		this.updUid = updUid;
	}
	
	/**
	 * Gets the upd tms.
	 *
	 * @return the upd tms
	 */
	public final Date getUpdTms() {
		return this.updTms;
	}
	
	/**
	 * Sets the upd tms.
	 *
	 * @param updTms the new upd tms
	 */
	public final void setUpdTms(Date updTms) {
		this.updTms = updTms;
	}

	/**
	 * Gets the competitors.
	 *
	 * @return the competitors
	 */
	public final String getCompetitors() {
		return this.competitors;
	}

	/**
	 * Sets the competitors.
	 *
	 * @param competitors the new competitors
	 */
	public final void setCompetitors(String competitors) {
		this.competitors = competitors;
	}

	/**
	 * Gets the disease.
	 *
	 * @return the disease
	 */
	public final String getDisease() {
		return this.disease;
	}

	/**
	 * Sets the disease.
	 *
	 * @param disease the new disease
	 */
	public final void setDisease(String disease) {
		this.disease = disease;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public final int getStatus() {
		return this.status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public final void setStatus(int status) {
		this.status = status;
	}
	
}
