package com.nur.usermgt.services;

import com.nur.usermgt.bindings.LoginForm;
import com.nur.usermgt.bindings.UnlockAccForm;
import com.nur.usermgt.bindings.UserRegForm;

public interface UserMgmtService {
	
	public String saveUser(UserRegForm regForm);
	
	public String loginUser(LoginForm loginForm);
	
	public String unlockUser(UnlockAccForm unlock);
	
	public String forgotPassword(String email);
	
}
