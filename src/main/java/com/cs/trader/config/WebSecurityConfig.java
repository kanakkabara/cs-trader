package com.cs.trader.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings("deprecation")
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		/*auth
			.inMemoryAuthentication()
			.withUser("john").password("{noop}smith").roles("USER")
			.and()
			.withUser("admin").password("{noop}admin").roles("USER", "ADMIN");*/
		
		 auth.jdbcAuthentication().dataSource(jdbcTemplate.getDataSource())
		  .usersByUsernameQuery(
		   "SELECT USERNAME,PASSWORD,ENABLED FROM USERS WHERE USERNAME=?")
		  .authoritiesByUsernameQuery(
		   "SELECT USERS.USERNAME,USER_ROLE AUTHORITIES FROM USER_ROLES,USERS WHERE USER_ROLES.USERNAME = USERS.USERNAME AND USERS.USERNAME=?")
		  .passwordEncoder(NoOpPasswordEncoder.getInstance());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
			//.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			//	.and()
			.authorizeRequests()
			.antMatchers("/h2/**").permitAll()
			.antMatchers("/traders/*").authenticated()
			.anyRequest().authenticated()
			  .and()
		    .formLogin().usernameParameter("username").passwordParameter("password")
			  .and()
			  .csrf().disable()
				.headers().frameOptions().disable();
		
		/*http
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
		.authorizeRequests()
		.antMatchers("/h2/**").permitAll()
		.antMatchers("/traders/").hasRole("USER")
			.and()
		.httpBasic()
			.and()
		.csrf().disable()
		.headers().frameOptions().disable();*/
	}
}
