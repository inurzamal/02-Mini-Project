package com.nur.usermgt.entities;

import javax.persistence.Entity;

import lombok.Data;

@Entity
@Data
public class CountryMasterEntity {
	
	private int COUNTRY_ID;
	private String COUNTRY_NAME;

}
