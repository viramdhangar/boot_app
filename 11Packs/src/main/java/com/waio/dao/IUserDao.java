/**
 * 
 */
package com.waio.dao;

import java.util.List;

import com.waio.model.UserDTO;

/**
 * @author Viramm
 *
 */
public interface IUserDao {

	/**
	 * @param userDTO
	 * @return
	 */
	public int createUser(UserDTO userDTO);
	/**
	 * @param userDTO
	 * @return
	 */
	int validateDuplicateUser(UserDTO userDTO);
	/**
	 * @param userDTO
	 * @return
	 */
	List<UserDTO> login(UserDTO userDTO);
	/**
	 * @param userDTO
	 * @return
	 */
	int validateReferralCode(UserDTO userDTO);
}
