package com.nivas.productservice.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.log.LogFormatUtils;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;


@Configuration
public class WebSecurityConfig {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Bean
	SecurityContextRepository securityContextRepository() {
		return new DelegatingSecurityContextRepository(new RequestAttributeSecurityContextRepository(),
				new HttpSessionSecurityContextRepository()); 
	}
	
	
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
	
	@Bean
	AuthenticationManager authManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(provider);
		
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//http.httpBasic(Customizer.withDefaults());
		
		http.authorizeHttpRequests(authorize->
		authorize.requestMatchers(HttpMethod.POST,"productapi/product","/saveProduct")
		.hasAnyRole("ADMIN")
		.requestMatchers(HttpMethod.GET,"productapi/product/{id}","/showGetProduct")
		.hasAnyRole("ADMIN","USER")
		.requestMatchers(HttpMethod.GET,"productapi/product/all","/showCreateProduct")
		.hasAnyRole("ADMIN")
		.requestMatchers(HttpMethod.POST,"/getProduct")
		.hasAnyRole("ADMIN","USER")
		.requestMatchers("/","/login").permitAll())
		.logout((logout) -> logout.logoutSuccessUrl("/"));
		
		http.csrf(csrfCustomizer -> {
			csrfCustomizer.ignoringRequestMatchers("productapi/product","/getProduct");
		});
		
		http.securityContext((securityContext) -> securityContext.requireExplicitSave(true));
		return http.build();
		
	}
	

}
