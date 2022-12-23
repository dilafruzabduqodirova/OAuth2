package com.example.telegramapteka.config;

import com.example.telegramapteka.CustomFailureHandler;
import com.example.telegramapteka.CustomSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.RequestCache;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().and()
                .authorizeRequests()
                .antMatchers("/login").anonymous()
                .antMatchers("/resource/**", "/webjars/**", "/websocket/**").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .formLogin()

                // ставим только что написанные нами handler'ы
                .failureHandler(authenticationFailureHandler())
                .successHandler(authenticationSuccessHandler())

                .loginPage("/login")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout");

        // запрашиваем у HttpSecurity объект RequestCache
        // если он не null - передаём его в наш CustomSuccessHandler
        RequestCache requestCache = http.getSharedObject(RequestCache.class);
        if (requestCache != null) {
            authenticationSuccessHandler().setRequestCache(requestCache);
        }
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**", "/webjars/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CustomSuccessHandler authenticationSuccessHandler() {
        return new CustomSuccessHandler();
    }

    @Bean
    public CustomFailureHandler authenticationFailureHandler() {
        return new CustomFailureHandler();
    }

}
