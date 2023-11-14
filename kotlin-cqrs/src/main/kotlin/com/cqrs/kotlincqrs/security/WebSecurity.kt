package com.cqrs.kotlincqrs.security

import org.springframework.beans.factory.annotation.Configurable
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configurable
@EnableWebSecurity
class WebSecurity {

    @Bean
    fun securityFilterChain(http: HttpSecurity) : SecurityFilterChain {
        http.csrf {
            it.disable()
        }.authorizeHttpRequests {
            it.anyRequest().permitAll()
        }

        return http.build()
    }
}