package com.nur.usermgt.bindings;

import java.util.Date;

import lombok.Data;

@Data
public class UserRegForm {
	
	private String FIRST_NAME;
	private String LAST_NAME;
	private String USER_EMAIL;
	private String USER_MOBILE;
	private Date DOB;
	private String GENDER;
	private int CITY_ID;
	private int STATE_ID;
	private int COUNTRY_ID;

}
