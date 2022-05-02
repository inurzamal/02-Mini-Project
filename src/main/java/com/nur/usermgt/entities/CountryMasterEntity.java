package com.nur.usermgt.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="COUNTRY_MASTER")
@Data
public class CountryMasterEntity {
	
	@Id
	private int countryId;	
	private String countryName;

}
