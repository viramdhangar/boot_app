/**
 * 
 */
package com.waio.service;

import com.waio.model.UserDTO;

/**
 * @author Viramm
 *
 */
public interface IUserService {

	int createUser(UserDTO userDTO);
	/**
	 * @param userDTO
	 * @return
	 */
	UserDTO login(UserDTO userDTO);
	/**
	 * @param userDTO
	 * @return
	 */
	int validateDuplicateUser(UserDTO userDTO);
	/**
	 * @param userDTO
	 * @return
	 */
	int validateReferralCode(UserDTO userDTO);
	/**
	 * @param userDTO
	 * @return
	 */
	String userValidation(UserDTO userDTO);
}
