package com.nur.usermgt.bindings;

import lombok.Data;

@Data
public class UnlockAccForm {
	
	private String USER_EMAIL;
	private String tmp_pwd;
	private String pwd;
}
