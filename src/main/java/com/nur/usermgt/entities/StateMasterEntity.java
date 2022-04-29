package com.nur.usermgt.entities;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class StateMasterEntity {
	
	private int STATE_ID;
	private String STATE_NAME;
	private int COUNTRY_ID;

}
