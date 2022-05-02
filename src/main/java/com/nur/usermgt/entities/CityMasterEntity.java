package com.nur.usermgt.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="CITY_MASTER")
@Data
public class CityMasterEntity {
	
	@Id
	private int cityId;
	private String cityName;
	private int stateId;

}
