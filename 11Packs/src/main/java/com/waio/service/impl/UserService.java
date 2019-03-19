/**
 * 
 */
package com.waio.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.waio.dao.IUserDao;
import com.waio.model.UserDTO;
import com.waio.service.IUserService;

/**
 * @author Viramm
 *
 */
@Service("UserService")
public class UserService implements IUserService {

	@Autowired
	private IUserDao userDao;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public int createUser(UserDTO userDTO) {

		// set authorities
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		userDTO.setAuthorities(authorities);
		// encrypt password
		userDTO.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
		userDTO.setUserName(userDTO.getUniqueNumber());
		return userDao.createUser(userDTO);
	}

	public int createUserInitially(String uniqueNumber) {

		// set authorities
		UserDTO userDTO = new UserDTO();
		userDTO.setUserName(uniqueNumber);
		userDTO.setUniqueNumber(uniqueNumber);
		return userDao.createUserInitially(userDTO);
	}
	/**
	 * @param userDTO
	 */
	@Override
	public String userValidation(UserDTO userDTO) {
		// check if user is already registered
		if (userDao.validateDuplicateEmail(userDTO) > 0) {
			return "Email already exist.";
		}
		// validate referral code if applied
		if(StringUtils.isNotEmpty(userDTO.getReferralCode())) {
			if(userDao.validateReferralCode(userDTO) < 1) {
				return "Referral Code is not valid";
			}
		}
/*		// phone validation
		if(!userDTO.getUniqueNumber().matches("[0-9]+") && !(userDTO.getUniqueNumber().length() == 10)) {
			return "Mobile number is not valid";
		}
		// validate email
		if(!emailValidator.validateEmail(userDTO.getEmail())) {
			return "Email is not valid";
		}*/
		// first name should not be empty
		if(StringUtils.isEmpty(userDTO.getFirstName())) {
			return "First name should not be empty";
		}
		return StringUtils.EMPTY;
	}

	@Override
	public UserDTO login(UserDTO userDTO) {
		List<UserDTO> userList = userDao.login(userDTO);
		UserDTO dbUser = null;
		if (CollectionUtils.isNotEmpty(userList)) {
			dbUser = userList.get(0);
			if (BCrypt.checkpw(userDTO.getPassword(), dbUser.getPassword())) {
				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				dbUser.setPassword(null);
				for(UserDTO uDTO : userList) {
					authorities.add(new SimpleGrantedAuthority(uDTO.getRole()));	
				}
				dbUser.setAuthorities(authorities);
				return dbUser;
			}
		}
		return null;
	}

	@Override
	public int validateDuplicateUser(UserDTO userDTO) {
		return userDao.validateDuplicateUser(userDTO);
	}

	@Override
	public int validateReferralCode(UserDTO userDTO) {
		return userDao.validateReferralCode(userDTO);
	}

	@Override
	public String checkIfCompleteDetailExist(String username) {
		return userDao.checkIfCompleteDetailExist(username);
	}
}
