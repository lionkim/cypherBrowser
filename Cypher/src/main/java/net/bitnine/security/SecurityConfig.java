package net.bitnine.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import lombok.extern.java.Log;

@Log
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)

public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Autowired
	SecurityUsersService zerockUsersService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		log.info("security config..............");

		http.authorizeRequests().antMatchers("/").permitAll()
		    .and()
                .authorizeRequests().antMatchers("/h2console/**").permitAll();
        
		http.authorizeRequests().antMatchers("/guest/**").permitAll();

		http.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER");

		http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");

		// http.formLogin();
		http.formLogin().loginPage("/login");

		http.exceptionHandling().accessDeniedPage("/accessDenied");

		// http.logout().invalidateHttpSession(true);
		http.logout().logoutUrl("/logout").invalidateHttpSession(true);

		// http.userDetailsService(zerockUserService);

		http.rememberMe()
		    .key("zerock")
		    .userDetailsService(zerockUsersService)
		    .tokenRepository(getJDBCRepository())
			.tokenValiditySeconds(60 * 60 * 24);

		 
        http.csrf().disable();
        http.headers().frameOptions().disable();
	}

	private PersistentTokenRepository getJDBCRepository() {

		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		return repo;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

		log.info("build Auth global........");

		auth.userDetailsService(zerockUsersService).passwordEncoder(passwordEncoder());

	}

}

// @Autowired
// public void configureGlobal(AuthenticationManagerBuilder auth) throws
// Exception {
//
// log.info("build Auth global........");
//
// auth.inMemoryAuthentication().withUser("manager").password("1111").roles("MANAGER");
// }

// @Autowired
// public void configureGlobal(AuthenticationManagerBuilder auth) throws
// Exception {
//
// log.info("build Auth global........");
//
// String query1 = "SELECT uid username, upw password, true enabled FROM
// tbl_members WHERE uid= ?";
//
// String query2 = "SELECT member uid, role_name role FROM tbl_member_roles
// WHERE member = ?";
//
// auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(query1).rolePrefix("ROLE_")
// .authoritiesByUsernameQuery(query2);
//
// }
