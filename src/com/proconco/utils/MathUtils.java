package com.proconco.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The Class MathUtils.
 */
public class MathUtils {
	
	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(MathUtils.class.getName());
	
	/**
	 * Crypt with m d5.
	 *
	 * @param pass the pass
	 * @return the string
	 */
	public static String cryptWithMD5(String pass){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] passBytes = pass.getBytes();
			md.reset();
			byte[] digested = md.digest(passBytes);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < digested.length; i++) {
				sb.append(Integer.toHexString(0xff & digested[i]));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException ex) {
			logger.log(Level.SEVERE, ex.getMessage());
		}
		return null;
	}
}
