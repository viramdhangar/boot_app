package com.waio.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.google.common.collect.ImmutableList;

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
		.antMatchers("/").permitAll()
		.antMatchers("/striker").permitAll()
		.antMatchers("/user").permitAll()
		.antMatchers("/download/file/*").permitAll()
		.antMatchers("/login").permitAll()
		.antMatchers("/registration").permitAll()
		.antMatchers("/identity/registration").permitAll()
		.antMatchers("/identity/login").permitAll()
		.antMatchers("/identity/logout").permitAll()
		.antMatchers("/verification/sendMobileOTP/*/otp").permitAll()
		.antMatchers("/verification/verifyMobileOTP/*/otp").permitAll()
		.antMatchers("/verification/sendEmailOTP/*/otp").permitAll()
		.antMatchers("/verification/verifyEmailOTP/*/otp").permitAll()
		.antMatchers("/api/v1/matches").permitAll()
		.antMatchers("/api/v1/match/*").permitAll()
		.antMatchers("/api/job/v1/matchesAPI").permitAll()
		.antMatchers("/api/job/v1/declareWinner/*").permitAll()
		.antMatchers("/api/job/v1/distributeWinning/*").permitAll()
		.antMatchers("/api/job/v1/updateMatchesStatus").permitAll()
		.antMatchers("/api/v1/leagues/*").permitAll()
		.antMatchers("/api/v1/league/*").permitAll()
		.antMatchers("/api/v1/winningBreakup/*").permitAll()
		.antMatchers("/api/v1/squad/*").permitAll()
		.antMatchers("/api/v1/createTeam").permitAll()
		.antMatchers("/api/v1/fantasyPoints").permitAll()
		.antMatchers("/api/v1/addMoney").permitAll()
		.antMatchers("/api/v1/joinLeague/*").permitAll()
		.antMatchers("/api/v1/allMatchesTeams/*").permitAll()
		.antMatchers("/api/v1/joinedMatchesAndLeagues/*").permitAll()
		.antMatchers("/api/v1/teamsOfMatch/*/*").permitAll()
		.antMatchers("/api/v1/teamView/*/*/*").permitAll()
		.antMatchers("/api/v1/teamEdit/*/*/*").permitAll()
		.antMatchers("/api/v1/joinedLeagues/*").permitAll()
		.antMatchers("/api/v1/joinedLeagues/*/*").permitAll()
		.antMatchers("/api/v1/joinedLeagueTeams/*/*/*").permitAll()
		.antMatchers("/api/v1/joinedLeagueAllTeams/*/*/*").permitAll()
		.antMatchers("/api/v1/switchTeam/*/*").permitAll()
		.antMatchers("/api/job/v1/createLeague").permitAll()
		.antMatchers("/api/job/v1/updateScoreAndPoints").permitAll()
		.antMatchers("/api/v1/teamsRankAndPointsInLeague/*/*/*").permitAll()
		.antMatchers("/api/v1/teamDetailWithPoints/*/*").permitAll()
		.antMatchers("/api/v1/account/*").permitAll()
		.antMatchers("/welcome").hasAnyRole("USER", "ADMIN")
		.antMatchers("/getEmployees").hasAnyRole("USER", "ADMIN")
		.antMatchers("/addNewEmployee").hasAnyRole("ADMIN")
		 .antMatchers(HttpMethod.OPTIONS, "/**").permitAll() 
		 .antMatchers(HttpMethod.POST, "/**").permitAll() 
		.anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll()
				.and().logout().permitAll();

		http.csrf().disable();
		//http.cors();
	}
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}