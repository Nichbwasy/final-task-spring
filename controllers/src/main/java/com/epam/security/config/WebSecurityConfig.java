package com.epam.security.config;

import com.epam.roles.Roles;
import com.epam.security.encode.EncoderGenerator;
import com.epam.services.security.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserSecurityService securityService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/cards", "/cards/**").hasAuthority(Roles.CLIENT)
                .antMatchers("/requests", "/requests/**", "/replenishment/history/**").hasAuthority(Roles.ADMINISTRATOR)
                .antMatchers("/", "/about", "/contact", "/service", "/login", "/register", "/test").permitAll()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .defaultSuccessUrl("/cards", true)
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/")
                .and()
                .csrf().disable();
    }

    @Bean
    public DaoAuthenticationProvider userDetailsManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(securityService);
        return daoAuthenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return EncoderGenerator.generateBCryptEncoder();
    }
}
