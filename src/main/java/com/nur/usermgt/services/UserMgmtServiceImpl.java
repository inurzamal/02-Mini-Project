package com.nur.usermgt.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nur.usermgt.bindings.LoginForm;
import com.nur.usermgt.bindings.UnlockAccForm;
import com.nur.usermgt.bindings.UserRegForm;
import com.nur.usermgt.entities.CityMasterEntity;
import com.nur.usermgt.entities.CountryMasterEntity;
import com.nur.usermgt.entities.StateMasterEntity;
import com.nur.usermgt.entities.UserDtlsEntity;
import com.nur.usermgt.repository.CityRepository;
import com.nur.usermgt.repository.CountryRepository;
import com.nur.usermgt.repository.StateRepository;
import com.nur.usermgt.repository.UserDtlsRepository;

@Service
public class UserMgmtServiceImpl implements UserMgmtService {

	@Autowired
	private UserDtlsRepository userRepo;

	@Autowired
	private CountryRepository countryRepo;

	@Autowired
	private StateRepository stateRepo;

	@Autowired
	private CityRepository cityRepo;

	@Override
	public String login(LoginForm loginForm) {

		UserDtlsEntity entity = userRepo.findByEmailAndPassword(loginForm.getEmail(), loginForm.getPwd());

		if (entity == null) {
			return "Inavlid Credentials";
		}

		if (entity != null && entity.getAccStatus().equals("LOCKED")) {
			return "Please unlock your account";
		}

		return "Success";
	}
	
	
	@Override
	public String emailCheck(String email) {
		
		UserDtlsEntity entity = userRepo.findByEmail(email);
		
		if(entity == null) {			
			return "Unique Email";
		}		
		return "Email ALready exists";
	}
	

	@Override
	public Map<Integer, String> loadCountries() {
		
		List<CountryMasterEntity> countries = countryRepo.findAll();
		
		Map<Integer, String> countryMap = new HashMap<>();
		
		for(CountryMasterEntity entity: countries) {
			countryMap.put(entity.getCountryId(), entity.getCountryName());
		}
		
		return countryMap;
	}
	

	@Override
	public Map<Integer, String> loadStates(int countryId) {
		
		List<StateMasterEntity> states = stateRepo.findByCountryId(countryId);
		
		Map<Integer, String> stateMap = new HashMap<>();
		
		for(StateMasterEntity entity: states) {
			stateMap.put(entity.getStateId(), entity.getStateName());
		}
		
		return stateMap;
	}

	@Override
	public Map<Integer, String> loadCities(int stateId) {

		List<CityMasterEntity> cities = cityRepo.findByStateId(stateId);
		
		Map<Integer, String> cityMap = new HashMap<>();
		
		cities.forEach(entity -> cityMap.put(entity.getCityId(), entity.getCityName()));
		
		return cityMap;
	}

	@Override
	public String registerUser(UserRegForm regForm) {
		
		UserDtlsEntity entity = new UserDtlsEntity();
		
		BeanUtils.copyProperties(regForm, entity);
		
		entity.setAccStatus("LOCKED");
		
		entity.setPassword(generatePassword());
		
		userRepo.save(entity);
		
		//TODO : send email
		
		return "success";
	}
	

	@Override
	public String unlockUser(UnlockAccForm unlockAccForm) {
		
		if(!unlockAccForm.getNewPwd().equals(unlockAccForm.getConfirmNewPwd())){
			return "Password and Confirm Password should be same";
		}
		
		UserDtlsEntity entity = userRepo.findByEmailAndPassword(unlockAccForm.getEmail(), unlockAccForm.getTmpPwd());
		
		if(entity == null) {
			return "Incorrect Email or Temporary Password";
		}
		
		entity.setPassword(unlockAccForm.getNewPwd());
		entity.setAccStatus("UNLOCKED");
		
		userRepo.save(entity);

		return "Account Unlocked";
	}

	@Override
	public String forgotPassword(String email) {
		
		UserDtlsEntity entity = userRepo.findByEmail(email);
		
		if(entity == null) {
			return "No user available with this email";
		}
			
		//TODO : send pwd to email
		
		return null;
	
	}
	
	
	//Generating Random Password
	
	private String generatePassword() {
	    int leftLimit = 48; // numeral '0'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 6;
	    Random random = new Random();

	    String generatedString = random.ints(leftLimit, rightLimit + 1)
	      .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
	      .limit(targetStringLength)
	      .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
	      .toString();

	    return generatedString;
	}

}


