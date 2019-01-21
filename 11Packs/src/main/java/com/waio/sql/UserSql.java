/**
 * 
 */
package com.waio.sql;

/**
 * @author Viramm
 *
 */
public class UserSql {

	// Use SQL
	public static final String INSERT_AUTHORITY_SQL = "insert into authorities (username, authority) values (?,?)";
	public static final String CREATE_USER_SQL = "insert into users (username, password, unique_number, email, firstname, lastname, middlename, referral_code) values (?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String VALIDATE_DUPLICATE_USER_SQL = "select count(username) from users where username = ? OR email=? OR unique_number=?";
	public static final String AUTHENTICATE_USER_SQL = "select users.*, authorities.authority role from users, authorities where users.username = authorities.username and (users.username=? OR users.unique_number=? OR users.email=?)";
	public static final String CREATE_QNIQUE_REFERRAL_CODE_SQL = "insert into referral (username) values (?)";
	public static final String LAST_INSERTED_REFERRAL_CODE_SQL = "SELECT referral_code from referral where username=?";
	public static final String INSERT_REFERRED_USER_CODE_SQL = "insert into referred_user (referral_code, username) values (?, ?)";
	public static final String VALIDATE_REFERRAL_CODE_SQL = "select count(referral_code) from users where referral_code=?";
}
