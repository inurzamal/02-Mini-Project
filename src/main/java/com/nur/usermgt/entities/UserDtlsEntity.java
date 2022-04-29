package com.nur.usermgt.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class UserDtlsEntity {
	
	@Id
	@GeneratedValue
	private Long USER_ID;
	
	private String FIRST_NAME;
	private String LAST_NAME;
	private String USER_EMAIL;
	private String USER_PWD;
	private String USER_MOBILE;
	private Date DOB;
	private String GENDER;
	private int CITY_ID;
	private int STATE_ID;
	private int COUNTRY_ID;
	private String ACC_STATUS;
	private Date CREATED_DATE;
	private Date UPDATED_DATE;

}
