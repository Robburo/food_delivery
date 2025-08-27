package com.fooddelivery.paymentservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/actuator/health").permitAll()
                    .requestMatchers("/api/payments/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { jwt ->
                    jwt.jwtAuthenticationConverter(jwtAuthConverter())
                }
            }
        return http.build()
    }

    private fun jwtAuthConverter(): JwtAuthenticationConverter {
        val converter = JwtAuthenticationConverter()
        val granted = JwtGrantedAuthoritiesConverter()
        granted.setAuthorityPrefix("ROLE_")     // prepend ROLE_
        granted.setAuthoritiesClaimName("role") // map from JWT "role" claim
        converter.setJwtGrantedAuthoritiesConverter(granted)
        return converter
    }
}