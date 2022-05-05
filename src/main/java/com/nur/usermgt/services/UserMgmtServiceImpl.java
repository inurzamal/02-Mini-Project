package com.nur.usermgt.services;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nur.usermgt.bindings.LoginForm;
import com.nur.usermgt.bindings.UnlockAccForm;
import com.nur.usermgt.bindings.UserRegForm;
import com.nur.usermgt.entities.UserDtlsEntity;
import com.nur.usermgt.repository.UserDtlsRepository;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {
	
	@Autowired
	private UserDtlsRepository userRepo;

	@Override
	public String signIn(LoginForm loginForm) {
		
		UserDtlsEntity userDtls = userRepo.findByEmail(loginForm.getEmail());
		if(loginForm.getPwd().equals(userDtls.getPwd())) {
			return "success";
		}
		return "Invalid email or password";
	}

	@Override
	public String signUp(UserRegForm regForm) {

		UserDtlsEntity user = new UserDtlsEntity();
		
		user.setFname(regForm.getFname());
		user.setLname(regForm.getLname());
		user.setEmail(regForm.getEmail());
		user.setPhno(regForm.getPhno());
		user.setPwd("text");
		user.setDob(regForm.getDob());
		user.setGender(regForm.getGender());
		user.setCountryId(regForm.getCountryId());
		user.setStateId(regForm.getStateId());
		user.setCityId(regForm.getCityId());
		user.setAccStatus("locked");
		
		userRepo.save(user);
			
		//send email
		
		return "Success, Please check your email";
	}

	@Override
	public String uniqueEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String unlockUser(UnlockAccForm unlock) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String forgotPassword(String email) {
	
		UserDtlsEntity user = userRepo.findByEmail(email);
		
		if(email.equals(user.getEmail())) {
			user.getPwd();
			//send pwd email
			
			return "Please check yor mail";
		}
		return "Invalid email";
	}

	@Override
	public Map<Integer, String> getCountry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, String> getState(int countryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, String> getCity(int stateId) {
		// TODO Auto-generated method stub
		return null;
	}

}
