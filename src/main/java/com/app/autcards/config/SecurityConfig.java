package com.app.autcards.config;

import com.app.autcards.service.Impl.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Oauth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler;

    public SecurityConfig(Oauth2AuthenticationSuccessHandler oauth2AuthenticationSuccessHandler) {
        this.oauth2AuthenticationSuccessHandler = oauth2AuthenticationSuccessHandler;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/resources/**", "/templates/**", "/", "/static/**",
                        "/login", "/webjars/**", "/js/**", "/css/**", "/vendor/**",
                        "/fonts/**","/images/**", "/resources/", "src/main/resources/static/images/logo.png").permitAll()
                .antMatchers("/logout").authenticated()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .loginPage("/login").permitAll()
                .successHandler(oauth2AuthenticationSuccessHandler)
                .userInfoEndpoint()
                .oidcUserService(new MyUserService())
                .and()
                .and()
                .logout(l -> l
                        .logoutUrl("/logout")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutSuccessUrl("/login")
                );
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
