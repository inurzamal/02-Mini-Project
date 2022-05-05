package com.nur.usermgt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nur.usermgt.entities.UserDtlsEntity;

public interface UserDtlsRepository extends JpaRepository<UserDtlsEntity, Long> {
	
	public UserDtlsEntity findByEmail(String email);

}
