package com.nur.usermgt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nur.usermgt.entities.CountryMasterEntity;

public interface CountryMasterRepo extends JpaRepository<CountryMasterEntity, Integer> {

}
