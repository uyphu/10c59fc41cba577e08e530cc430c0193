package com.proconco.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;





import com.customer.entity.Order;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.IgnoreSave;

@Entity
public class Group {

	@Id
	private Long id;
	private String grpName;
	//private Set<UserProfile> userProfiles = new HashSet<UserProfile>(0);
	private String delFlag;
	private String crtUid;
	private Date crtTms;
	private String updUid;
	private Date updTms;
	
	 @Ignore
	    @IgnoreSave
	    private List<UserProfile> orders;
}
