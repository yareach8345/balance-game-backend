package com.yareach.balancegame.config

import jakarta.servlet.FilterChain
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.FilterChainProxy
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {
    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain =
        http
            .csrf{ it.disable() }
            .formLogin { it
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .permitAll()}
            .logout { it
                .logoutUrl("/logout")
                .deleteCookies("JSESSIONID")}
            .authorizeHttpRequests { it
                .requestMatchers("/admin/**", "/my/**").authenticated()
                .anyRequest().permitAll()}
            .build()
}