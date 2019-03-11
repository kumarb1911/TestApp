package com.practice.hackathon.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.practice.hackathon.dao.UserRepository;

@EnableWebSecurity
@EnableJpaRepositories(basePackageClasses=UserRepository.class)
@Configuration
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsService userDetailsService;
	@Autowired
	AuthenticationEntryPoint authenticationEntryPoint;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		/*auth.jdbcAuthentication().dataSource(dataSource)
		.usersByUsernameQuery("Select username,password from users where username=?");*/
		auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.headers().frameOptions().disable();
		
		http.authorizeRequests()
		.antMatchers("/console/**","/**/register")
		.permitAll()
		.anyRequest().authenticated()
		.and().httpBasic().authenticationEntryPoint(authenticationEntryPoint)
		.and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		//.and().logout().logoutUrl("/api/logout").logoutSuccessUrl("/api/v1/test").deleteCookies("JSESSIONID").invalidateHttpSession(true).clearAuthentication(true);
	}
	
	/*public UserDetailsService getUserDetailsService(){
		JdbcDaoImpl jdbcDaoImpl = new JdbcDaoImpl(); 
		jdbcDaoImpl.setDataSource(dataSource);
		jdbcDaoImpl.setUsersByUsernameQuery("Select username,password from users where username=?");
		return jdbcDaoImpl;
	}*/
	
	@Bean(name="passwordEncoder")
	public PasswordEncoder getPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
}
