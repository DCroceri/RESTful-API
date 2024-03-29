package net.awsomerecipes.ws.api.rest.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import net.awsomerecipes.ws.api.rest.beans.security.UserAuthority;
import net.awsomerecipes.ws.api.rest.facades.security.impl.UserDetailsServiceImpl;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private UserDetailsServiceImpl jwtUserDetailsService;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	public void configureGlobal( AuthenticationManagerBuilder auth ) throws Exception {
		auth.userDetailsService( jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Autowired
	TokenHelper tokenHelper;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS ).and()
				.authorizeRequests().antMatchers("/api/users/**",
												 "/api/roles/**").hasAuthority(UserAuthority.ROLE_ADMIN.getName()).and()
				.authorizeRequests().antMatchers("/api/recipes/**",
												 "/api/comments/**",
												 "/auth/**").authenticated().and()
				// custom auth filter added
				.addFilterBefore(new TokenAuthenticationFilter(tokenHelper, jwtUserDetailsService), BasicAuthenticationFilter.class);

		http.csrf().disable();
	}


	@Override
	public void configure(WebSecurity web) throws Exception {
		// TokenAuthenticationFilter will ignore the below paths
		web.ignoring().antMatchers(
				HttpMethod.POST,
				"/auth/login",
				"/auth/signup"
		);

	}
}
