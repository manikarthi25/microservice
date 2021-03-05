package com.train.zuul.gateway.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	private Environment environment;

	@Autowired
	public AuthorizationFilter(Environment environment, AuthenticationManager authenticationManager) {
		super(authenticationManager);
		this.environment = environment;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeaderName = request.getHeader(environment.getProperty("authorization.token.header.name"));

		if (authorizationHeaderName == null
				|| !authorizationHeaderName.startsWith(environment.getProperty("authorization.token.header.prefix"))) {
			filterChain.doFilter(request, response);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthorization(request);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);

	}

	private UsernamePasswordAuthenticationToken getAuthorization(HttpServletRequest request) {
		String authorizationHeaderName = request.getHeader(environment.getProperty("authorization.token.header.name"));

		if (authorizationHeaderName == null) {
			return null;
		}
		String token = authorizationHeaderName.replace(environment.getProperty("authorization.token.header.prefix"),
				"");
		String userSecurityId = Jwts.parser().setSigningKey(environment.getProperty("token.secret"))
				.parseClaimsJws(token).getBody().getSubject();
		if (userSecurityId == null) {
			return null;
		}
		return new UsernamePasswordAuthenticationToken(userSecurityId, null, new ArrayList<>());
	}

}