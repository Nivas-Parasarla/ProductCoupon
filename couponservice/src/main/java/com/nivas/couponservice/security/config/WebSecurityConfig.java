package com.nivas.couponservice.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class WebSecurityConfig {
	
	@Autowired
	UserDetailsService userDetailsService;
	
	
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityContextRepository securityContextRepository() {
		return new DelegatingSecurityContextRepository(new RequestAttributeSecurityContextRepository(),
				new HttpSessionSecurityContextRepository());
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
			authorize.requestMatchers(HttpMethod.GET,"couponapi/coupons/{code:^[A-Z]*$}","/showGetCoupon","/getCoupon")
			.hasAnyRole("USER","ADMIN")
			.requestMatchers(HttpMethod.GET,"couponapi/coupons/all")
			.hasRole("ADMIN")
			.requestMatchers(HttpMethod.GET,"/showCreateCoupon","/createCoupon","/createResponse")
			.hasRole("ADMIN")
			.requestMatchers(HttpMethod.POST,"couponapi/coupons","/saveCoupon")
			.hasRole("ADMIN")
			.requestMatchers(HttpMethod.POST,"/getCoupon")
			.hasAnyRole("ADMIN","USER")
			.requestMatchers("/","/login","/showReg","/registerUser").permitAll())
			.logout((logout) -> logout.logoutSuccessUrl("/"));
		
		http.csrf(csrfCustomizer -> {
			csrfCustomizer.ignoringRequestMatchers("/couponapi/coupons/**");
			RequestMatcher requestMatchers = new RegexRequestMatcher("couponapi/coupons/{code:^[A-Z]*$}","POST");
			requestMatchers=new MvcRequestMatcher(new  HandlerMappingIntrospector(),"/getCoupon");
			csrfCustomizer.ignoringRequestMatchers(requestMatchers);
			});
		
		
		http.securityContext((securityContext) -> securityContext.requireExplicitSave(true));
		return http.build();
		
	}

}
