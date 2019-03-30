/**
 * 
 */
package com.waio.dao;

import java.util.List;

import com.waio.model.AppDetail;
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
	/**
	 * @param userDTO
	 * @return
	 */
	int createUserInitially(UserDTO userDTO);
	/**
	 * @param username
	 * @return
	 */
	String checkIfCompleteDetailExist(String username);
	/**
	 * @param userDTO
	 * @return
	 */
	int validateDuplicateEmail(UserDTO userDTO); 
	/**
	 * @return
	 */
	AppDetail appDetail();
}
