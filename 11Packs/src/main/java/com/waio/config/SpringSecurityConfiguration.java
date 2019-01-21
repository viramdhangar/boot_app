package com.waio.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
    DataSource dataSource;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Enable jdbc authentication
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
	}

	@Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager() throws Exception {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
		jdbcUserDetailsManager.setDataSource(dataSource);
		return jdbcUserDetailsManager;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/user").permitAll()
		.antMatchers("/login").permitAll()
		.antMatchers("/registration").permitAll()
		.antMatchers("/identity/registration").permitAll()
		.antMatchers("/identity/login").permitAll()
		.antMatchers("/identity/logout").permitAll()
		.antMatchers("/sendMobileOTP/*/otp").permitAll()
		.antMatchers("/verifyMobileOTP/*/otp").permitAll()
		.antMatchers("/sendEmailOTP/*/otp").permitAll()
		.antMatchers("/verifyEmailOTP/*/otp").permitAll()
		.antMatchers("/api/v1/matches").permitAll()
		.antMatchers("/api/job/v1/matchesAPI").permitAll()
		.antMatchers("/api/v1/leagues/*").permitAll()
		.antMatchers("/api/v1/winningBreakup/*").permitAll()
		.antMatchers("/api/v1/squad/*").permitAll()
		.antMatchers("/api/v1/createTeam").permitAll()
		.antMatchers("/api/v1/joinLeague").permitAll()
		.antMatchers("/api/v1/allMatchesTeams/*").permitAll()
		.antMatchers("/api/v1/teamsOfMatch/*/*").permitAll()
		.antMatchers("/api/v1/teamView/*/*/*").permitAll()
		.antMatchers("/api/v1/teamEdit/*/*/*").permitAll()
		.antMatchers("/api/v1/joinedLeagues/*").permitAll()
		.antMatchers("/api/v1/joinedLeagues/*/*").permitAll()
		.antMatchers("/api/v1/joinedLeagueTeams/*/*/*").permitAll()
		.antMatchers("/api/job/v1/createLeague").permitAll()
		.antMatchers("/api/job/v1/updateScoreAndPoints/*").permitAll()
		.antMatchers("/api/job/v1/teamsRankAndPointsInLeague/*/*/*").permitAll()
		.antMatchers("/api/job/v1/teamDetailWithPoints/*").permitAll()
		.antMatchers("/welcome").hasAnyRole("USER", "ADMIN")
		.antMatchers("/getEmployees").hasAnyRole("USER", "ADMIN")
		.antMatchers("/addNewEmployee").hasAnyRole("ADMIN")
		.anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll()
				.and().logout().permitAll();

		http.csrf().disable();
	}
}