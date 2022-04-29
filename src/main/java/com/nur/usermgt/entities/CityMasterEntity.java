package com.nur.usermgt.entities;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class CityMasterEntity {
	
	private int CITY_ID;
	private String CITY_NAME;
	private int STATE_ID;

}
