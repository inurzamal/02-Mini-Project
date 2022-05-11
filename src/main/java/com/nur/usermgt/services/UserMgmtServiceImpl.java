package com.nur.usermgt.services;

import java.io.BufferedReader;
import java.io.FileReader;
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
import com.nur.usermgt.utils.EmailUtils;

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

	@Autowired
	private EmailUtils emailUtils;

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

		if (entity == null) {
			return "Unique Email";
		}
		return "Email ALready exists";
	}

	@Override
	public Map<Integer, String> loadCountries() {

		List<CountryMasterEntity> countries = countryRepo.findAll();

		Map<Integer, String> countryMap = new HashMap<>();

		for (CountryMasterEntity entity : countries) {
			countryMap.put(entity.getCountryId(), entity.getCountryName());
		}

		return countryMap;
	}

	@Override
	public Map<Integer, String> loadStates(int countryId) {

		List<StateMasterEntity> states = stateRepo.findByCountryId(countryId);

		Map<Integer, String> stateMap = new HashMap<>();

		for (StateMasterEntity entity : states) {
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
		UserDtlsEntity savedEntity = userRepo.save(entity);

		String email = regForm.getEmail();
		String subjects = "User Registration - NIELIT Guwahati";
		String fileName = "UNLOCK-ACC-EMAIL-BODY-TEMPLATE.txt";
		System.out.println(fileName);
		System.out.println(entity);
		String body = readMailBodyContent(fileName, entity);
		System.out.println(body);
		boolean isSent = emailUtils.sendEmail(email, subjects, body);

		if (savedEntity.getUserId() != null && isSent) {
			return "SUCCESS";
		}

		return "FAIL";
	}

	@Override
	public String unlockUser(UnlockAccForm unlockAccForm) {
		
		if (!unlockAccForm.getNewPwd().equals(unlockAccForm.getConfirmNewPwd())) {
			return "Password and Confirm Password should be same";
		}
		UserDtlsEntity entity = userRepo.findByEmailAndPassword(unlockAccForm.getEmail(), unlockAccForm.getTmpPwd());

		if (entity == null) {
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

		if (entity == null) {
			return "No user available with this email";
		}

		String fileName = "RECOVER-PASSWORD-EMAIL-BODY-TEMPLATE.txt";
		String mailBody = readMailBodyContent(fileName, entity);
		String subjects = "Recover Password";

		boolean isSent = emailUtils.sendEmail(email, subjects, mailBody);

		if (isSent) {
			return "Password Sent to registered Email";
		}

		return null;

	}

	// Generating Random Password

	private String generatePassword() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 6;
		Random random = new Random();

		String generatedString = random.ints(leftLimit, rightLimit + 1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)).limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

		return generatedString;
	}

	private String readMailBodyContent(String fileName, UserDtlsEntity entity) {

		String mailBody = null;

		try {
			StringBuffer sb = new StringBuffer();

			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String line = br.readLine(); // reading first line data

			while (line != null) {
				sb.append(line); // appending line data to buffer obj
				line = br.readLine(); // reading next line data
			}

			mailBody = sb.toString();

			mailBody = mailBody.replace("{FNAME}", entity.getFname());
			mailBody = mailBody.replace("{LNAME}", entity.getLname());
			mailBody = mailBody.replace("{TEMP-PWD}", entity.getPassword());
			mailBody = mailBody.replace("{EMAIL}", entity.getEmail());
			mailBody = mailBody.replace("{PWD}", entity.getPassword());
			br.close();		
		} 
		catch (Exception e) {
			e.printStackTrace();
		}

		return mailBody;
	}
}
