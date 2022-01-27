package com.floapp.security.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static com.floapp.security.security.ApplicationUserPermission.*;
import static com.floapp.security.security.ApplicationUserRole.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfiguration(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .authorizeRequests()
                .antMatchers("/","index","/css/*","/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and().httpBasic();

    }
    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

        UserDetails ferUser= User.builder()
                .username("ferna")
                .password(passwordEncoder.encode("12345"))
//                .roles(STUDENT.name())
                .authorities(STUDENT.grantedAuthorities())
                .build();//ROLE_STUDENT
        UserDetails juanUser=User.builder()
                .username("juani")
                .password(passwordEncoder.encode("12345"))
//                .roles(ADMIN.name())        //ROLE_ADMIN
                .authorities(ADMIN.grantedAuthorities())
                .build();
        UserDetails tomUser=User.builder()
                .username("tom")
                .password(passwordEncoder.encode("12345"))
//                .roles(ADMINTRAINEE.name())    //ROLE_ADMINTRAINEE
                .authorities(ADMINTRAINEE.grantedAuthorities())
                .build();
//        System.out.println("paso por aca");
        return new InMemoryUserDetailsManager(
                ferUser,juanUser,tomUser
        );
    }

}
