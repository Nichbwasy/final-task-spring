package com.epam.config.security;

import com.epam.repositories.ClientRepository;
import com.epam.roles.Roles;
import com.epam.config.security.utils.EncoderGenerator;
import com.epam.services.security.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserSecurityService securityService;

    @Autowired
    private ClientRepository clientRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(securityService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
                .antMatchers("/cards", "/cards/**").authenticated()
                .antMatchers("/requests", "/requests/**", "/replenishment/history/**", "/transactions/history/**").hasAuthority(Roles.ADMINISTRATOR)
                .antMatchers("/", "/about", "/contact", "/service", "/login", "/register", "/test").permitAll()
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .defaultSuccessUrl("/cards", true)
                .and()
                .logout()
                .permitAll()
                .logoutSuccessUrl("/");
        http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean(), securityService));
        http.addFilterBefore(new JwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return EncoderGenerator.generateBCryptEncoder();
    }
}
