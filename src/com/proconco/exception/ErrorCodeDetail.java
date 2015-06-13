package com.proconco.exception;

// TODO: Auto-generated Javadoc
/**
 * The Enum ErrorCodeDetail.
 */
public enum ErrorCodeDetail {

	/** The error duplicated name. */
	ERROR_DUPLICATED_NAME(600, "[600] Name already exists"), 
	
	/** The error duplicated email. */
	ERROR_DUPLICATED_EMAIL(601, "[601] Email already exists"), 
	
	/** The error exist object. */
	ERROR_EXIST_OBJECT(602, "[602] Object already exists"),
	
	/** The error record not found. */
	ERROR_RECORD_NOT_FOUND(603, "[603] Object not found"),
	
	/** The error parse query. */
	ERROR_PARSE_QUERY(604, "[604] Parsing query string error"),
	
	/** The error remove entity. */
	ERROR_REMOVE_ENTITY(605, "[605] Remove entity error"),
	
	/** The error insert entity. */
	ERROR_INSERT_ENTITY(606, "[606] Insert entity error"),
	
	/** The error update entity. */
	ERROR_UPDATE_ENTITY(607, "[607] Update entity error");

	/** The id. */
	private final int id;
	
	/** The msg. */
	private final String msg;

	/**
	 * Instantiates a new error code.
	 *
	 * @param id the id
	 * @param msg the msg
	 */
	ErrorCodeDetail(int id, String msg) {
		this.id = id;
		this.msg = msg;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Gets the msg.
	 *
	 * @return the msg
	 */
	public String getMsg() {
		return this.msg;
	}
}
