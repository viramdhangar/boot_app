/**
 * 
 */
package com.waio.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import com.waio.dao.IUserDao;
import com.waio.model.UserDTO;
import com.waio.sql.UserSql;

/**
 * @author Viramm
 *
 */
@Repository("UserDao")
public class UserDao extends AbstractDaoSupport implements IUserDao {

	protected final Log LOG = LogFactory.getLog(UserDao.class);

	@Override
	public int createUser(UserDTO userDTO) {
		int savedRecords = getJdbcTemplate().update(UserSql.CREATE_USER_SQL,
				new Object[] { userDTO.getUserName(), userDTO.getPassword(), userDTO.getUniqueNumber(),
						userDTO.getEmail(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getMiddleName(),
						userDTO.getFirstName().toUpperCase()+referralNumber(userDTO) });
		// insert user authorities
		insertUserAuthorities(userDTO);
		// insert referral code if entered
		if(StringUtils.isNotEmpty(userDTO.getReferralCode())) {
			insertReferralUser(userDTO);	
		}
		return savedRecords;
	}

	private int referralNumber(UserDTO userDTO) {
		getJdbcTemplate().update(UserSql.CREATE_QNIQUE_REFERRAL_CODE_SQL, new Object[] { userDTO.getUserName() });
		return getJdbcTemplate().queryForObject(UserSql.LAST_INSERTED_REFERRAL_CODE_SQL, new Object[] { userDTO.getUserName() }, Integer.class);
	}
	
	private void insertReferralUser(UserDTO userDTO) {
		getJdbcTemplate().update(UserSql.INSERT_REFERRED_USER_CODE_SQL, new Object[] { userDTO.getReferralCode(), userDTO.getUserName() });
	}
	
	private void insertUserAuthorities(UserDTO user) {
		for (GrantedAuthority auth : user.getAuthorities()) {
			getJdbcTemplate().update(UserSql.INSERT_AUTHORITY_SQL, user.getUserName(), auth.getAuthority());
		}
	}

	@Override
	public int validateDuplicateUser(UserDTO userDTO) {
		return getJdbcTemplate().queryForObject(UserSql.VALIDATE_DUPLICATE_USER_SQL,
				new Object[] { userDTO.getUserName(), userDTO.getUserName(), userDTO.getUserName() }, Integer.class);
	}

	@Override
	public List<UserDTO> login(UserDTO userDTO) {
		return getJdbcTemplate().query(UserSql.AUTHENTICATE_USER_SQL,
				new Object[] { userDTO.getUserName(), userDTO.getUserName(), userDTO.getUserName() },
				new BeanPropertyRowMapper<UserDTO>(UserDTO.class));
	}

	@Override
	public int validateReferralCode(UserDTO userDTO) {
		return getJdbcTemplate().queryForObject(UserSql.VALIDATE_REFERRAL_CODE_SQL,
				new Object[] { userDTO.getReferralCode() }, Integer.class);
	}
}
