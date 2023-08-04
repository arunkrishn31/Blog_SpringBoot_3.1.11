package com.myblogrestapi.Cofig;

import com.myblogrestapi.Filter.JwtAuthFilter;
import com.myblogrestapi.Service.UserInfoUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {
    @Autowired
    private JwtAuthFilter authFilter;
    @Bean
    public UserDetailsService userDetailsService(){ //if we use below 2.7.1 SB use  PasswordEncoder passwordEncoder in ()
//        UserDetails admin= User.withUsername("aaa")
//                .password(passwordEncoder.encode("Arun"))
//                .roles("ADMIN").build();
//        UserDetails user= User.withUsername("bbb")
//                .password(passwordEncoder.encode("Arun"))
//                .roles("USER").build();
//        return  new InMemoryUserDetailsManager(admin,user);
        return new UserInfoUserDetailsService();
    }

    // for normal Security
//    @Bean
//    public SecurityFilterChain securityFilterChain1(HttpSecurity httpSecurity) throws Exception {
//        return httpSecurity.csrf().disable()
//                .authorizeRequests()
//                .requestMatchers("/api/v1/comment/welcome","/user/**")
//                .permitAll()
//                .and()
//                .authorizeRequests()
//                .requestMatchers("/comment/**")
//                .authenticated()
//                .and().formLogin()
//                .and()
//                .build();
//    }

    //for JWT Security
    @Bean
    public SecurityFilterChain securityFilterChain1(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable()
                .authorizeRequests()
                .requestMatchers("/api/v1/comment/welcome", "/user/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .requestMatchers("/comment/**")
                .authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }



    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
