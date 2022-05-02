package com.nur.usermgt.services;

import org.springframework.stereotype.Service;

import com.nur.usermgt.bindings.LoginForm;
import com.nur.usermgt.bindings.UnlockAccForm;
import com.nur.usermgt.bindings.UserRegForm;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {

	@Override
	public String saveUser(UserRegForm regForm) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String loginUser(LoginForm loginForm) {
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
		// TODO Auto-generated method stub
		return null;
	}

}
