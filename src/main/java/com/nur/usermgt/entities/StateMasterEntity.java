package com.nur.usermgt.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="STATE_MASTER")
@Data
public class StateMasterEntity {
	
	@Id
	private int stateId;	
	private String stateName;	
	private int countryId;

}
