package com.fooddelivery.restaurantservice.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.web.SecurityFilterChain
import javax.crypto.spec.SecretKeySpec

@Configuration
class SecurityConfig {

    @Autowired
    private lateinit var jwtProperties: JwtProperties

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/actuator/health").permitAll()
                    .requestMatchers("/api/restaurant/orders/**").hasRole("ADMIN")
                    .anyRequest().authenticated()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt {
                }
            }
        return http.build()
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        val key = SecretKeySpec(jwtProperties.secret.toByteArray(), "HmacSHA256")
        return NimbusJwtDecoder.withSecretKey(key).build()
    }
}