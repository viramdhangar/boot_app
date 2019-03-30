/**
 * 
 */
package com.waio.dao.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Repository;

import com.waio.dao.IUserDao;
import com.waio.model.AppDetail;
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
		try {
			String sql = UserSql.UPDATE_USER_SQL;
			String referalCode = userDTO.getFirstName().toUpperCase() + referralNumber(userDTO);
			int savedRecords = getJdbcTemplate().update(sql,
					new Object[] { userDTO.getPassword(),
							userDTO.getUniqueNumber(), 
							userDTO.getEmail(), 
							userDTO.getFirstName(),
							userDTO.getLastName(), 
							userDTO.getMiddleName(),
							referalCode, 
							userDTO.getUserName() });
		// insert user authorities
		insertUserAuthorities(userDTO);
		// insert referral code if entered
		if(StringUtils.isNotEmpty(userDTO.getReferralCode())) {
			insertReferralUser(userDTO);	
		}

		// give sign up bonus amount
		BigDecimal signupAmount = new BigDecimal(50);
		String acctxnSql = "insert into account_transaction (username, status, created, created_id, bonus_amount) values (?, 'BONUS', current_timestamp(), 'SIGNUP', ?)";
		int accountTxn = getJdbcTemplate().update(acctxnSql,
				new Object[] { userDTO.getUserName(), signupAmount });
		if(accountTxn > 0) {
			System.out.println("Signup bonus added to transaction table");			
		}

		String accountSql = "insert into account ( username, bonus_amount, updated, updatedid) values (?, ?, current_timestamp(), 'BONUS')";
		int account = getJdbcTemplate().update(accountSql,
				new Object[] { userDTO.getUserName(), signupAmount });
		if(account > 0) {
			System.out.println("Signup bonus added to account table");			
		}
		
		return savedRecords;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e);
			return 0;
		}
		
	}
	
	public int createUserInitially(UserDTO userDTO) {
		try {
		int savedRecords = getJdbcTemplate().update(UserSql.CREATE_USER_INI_SQL,
				new Object[] { userDTO.getUserName(), userDTO.getUniqueNumber() });
		return savedRecords;
		}catch(Exception e){
			System.out.println(e);
			return 0;
		}
	}

	public String checkIfCompleteDetailExist(String username) {
		return getJdbcTemplate().queryForObject(UserSql.VALIDATE_USER_DETAIL_SQL, new Object[] { username }, String.class);
	}
	
	private int referralNumber(UserDTO userDTO) {
		getJdbcTemplate().update(UserSql.CREATE_QNIQUE_REFERRAL_CODE_SQL, new Object[] { userDTO.getUserName() });
		int i = getJdbcTemplate().queryForObject(UserSql.LAST_INSERTED_REFERRAL_CODE_SQL, new Object[] { userDTO.getUserName() }, Integer.class);
		return i;
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
	public int validateDuplicateEmail(UserDTO userDTO) {
		return getJdbcTemplate().queryForObject(UserSql.VALIDATE_DUPLICATE_EMAIL_SQL,
				new Object[] { userDTO.getEmail(), userDTO.getEmail(), userDTO.getEmail() }, Integer.class);
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
	
	@Override
	public AppDetail appDetail() {
		List<AppDetail> appList = getJdbcTemplate().query(UserSql.MY_APP_SQL, new Object[] {}, new BeanPropertyRowMapper<AppDetail>(AppDetail.class));
		return appList.get(0);
	}
}
