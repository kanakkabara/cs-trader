package com.cs.trader.config;

import com.cs.trader.domain.User;
import com.cs.trader.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	UserService userService;
	
	@SuppressWarnings("deprecation")
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		/*auth
			.inMemoryAuthentication()
			.withUser("john").password("{noop}smith").roles("USER")
			.and()
			.withUser("admin").password("{noop}admin").roles("USER", "ADMIN");*/
		
//		 auth.jdbcAuthentication().dataSource(jdbcTemplate.getDataSource())
//		  .usersByUsernameQuery(
//		   "SELECT USERNAME,PASSWORD,ENABLED FROM USERS WHERE USERNAME=?")
//		  .authoritiesByUsernameQuery(
//		   "SELECT USERS.USERNAME,USER_ROLE,ENABLED AUTHORITY FROM USER_ROLES,USERS " +
//				  "WHERE USER_ROLES.USERNAME = USERS.USERNAME AND USERS.USERNAME=?")
//		  .passwordEncoder(NoOpPasswordEncoder.getInstance());

		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/h2/**").permitAll()
			.antMatchers(HttpMethod.GET, "/sectors").hasAnyAuthority(User.Authority.TRADER.toString(), User.Authority.ADMIN.toString())
			.antMatchers(HttpMethod.GET, "/sectors/*").hasAnyAuthority(User.Authority.TRADER.toString(), User.Authority.ADMIN.toString())
			.antMatchers(HttpMethod.POST, "/sectors").hasAuthority(User.Authority.ADMIN.toString())
			.antMatchers(HttpMethod.PUT, "/sectors/*").hasAuthority(User.Authority.ADMIN.toString())
			.antMatchers(HttpMethod.DELETE, "/sectors/*").hasAuthority(User.Authority.ADMIN.toString())

			.antMatchers(HttpMethod.GET, "/companies").hasAnyAuthority(User.Authority.TRADER.toString(), User.Authority.ADMIN.toString())
			.antMatchers(HttpMethod.GET, "/companies/*").hasAnyAuthority(User.Authority.TRADER.toString(), User.Authority.ADMIN.toString())
			.antMatchers(HttpMethod.POST, "/companies").hasAuthority(User.Authority.ADMIN.toString())
			.antMatchers(HttpMethod.PUT, "/companies/*").hasAuthority(User.Authority.ADMIN.toString())
			.antMatchers(HttpMethod.DELETE, "/companies/*").hasAuthority(User.Authority.ADMIN.toString())

			.antMatchers("/traders").hasAuthority(User.Authority.ADMIN.toString())
			.antMatchers("/traders/{\\d+}/activitysummary").hasAnyAuthority(User.Authority.ADMIN.toString(), User.Authority.COMPLIANCE_OFFICER.toString())
			.antMatchers("/traders/top*").hasAnyAuthority(User.Authority.ADMIN.toString(), User.Authority.COMPLIANCE_OFFICER.toString())
			
			.antMatchers("/quotes").authenticated()
			.antMatchers("/quotes/{\\\\d+}").authenticated()
			
//
//			.antMatchers(HttpMethod.GET, "/orders*").hasAnyAuthority(User.Authority.TRADER.toString(), User.Authority.ADMIN.toString())

			.antMatchers("/log").hasAuthority(User.Authority.COMPLIANCE_OFFICER.toString())
//			.antMatchers("/user").hasAuthority(User.Authority.ADMIN.toString())
		.and()
			.httpBasic()//.formLogin().usernameParameter("username").passwordParameter("password")
		.and()
			.csrf().disable()
			.headers().frameOptions().disable();
	}
}
