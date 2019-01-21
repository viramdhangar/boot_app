/**
 * 
 */
package com.waio.dao.impl;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * @author Viramm
 *
 */
public class AbstractDaoSupport extends JdbcDaoSupport {
	
	private Map<String, String> sqlMap;
	
	private boolean enableAuthorities = true;
	
	@Autowired
	DataSource dataSource;
	
	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
	}

	/**
	 * @return the sqlMap
	 */
	public Map<String, String> getSqlMap() {
		return sqlMap;
	}

	/**
	 * @param sqlMap
	 *            the sqlMap to set
	 */
	public void setSqlMap(Map<String, String> sqlMap) {
		this.sqlMap = sqlMap;
	}

	/**
	 * @return the enableAuthorities
	 */
	public boolean isEnableAuthorities() {
		return enableAuthorities;
	}

	/**
	 * @param enableAuthorities the enableAuthorities to set
	 */
	public void setEnableAuthorities(boolean enableAuthorities) {
		this.enableAuthorities = enableAuthorities;
	}
}
