package com.nur.usermgt.services;

import java.util.Map;

import com.nur.usermgt.bindings.LoginForm;
import com.nur.usermgt.bindings.UnlockAccForm;
import com.nur.usermgt.bindings.UserRegForm;

public interface UserMgmtService {
	
	public String signIn(LoginForm loginForm);
	
	public String signUp(UserRegForm regForm);
	
	public String uniqueEmail(String email);
	
	public String unlockUser(UnlockAccForm unlock);
	
	public String forgotPassword(String email);
	
	Map<Integer, String> getCountry();
	
	Map<Integer, String> getState(int countryId);
	
	Map<Integer, String> getCity(int stateId);
	
}
