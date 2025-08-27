package com.fooddelivery.orderservice.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(JwtProperties::class)
class JwtConfig

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    var secret: String = ""
)