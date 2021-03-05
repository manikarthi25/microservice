package com.train.zuul.gateway.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	private Environment environment;

	@Autowired
	public WebSecurity(Environment environment) {
		this.environment = environment;
	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable();
		httpSecurity.headers().frameOptions().disable();
		httpSecurity.authorizeRequests().antMatchers(environment.getProperty("zuul.actuator.url")).permitAll()
				.antMatchers(environment.getProperty("user.zuul.actuator.url")).permitAll()
				.antMatchers(environment.getProperty("account.zuul.actuator.url")).permitAll()
				.antMatchers(HttpMethod.POST, environment.getProperty("user.signup.url")).permitAll()
				.antMatchers(HttpMethod.POST, environment.getProperty("user.login.url")).permitAll().anyRequest()
				.authenticated().and().addFilter(new AuthorizationFilter(environment, authenticationManager()));

		httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

}
