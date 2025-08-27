package com.fooddelivery.authservice.dto

data class RegisterRequest(
    val username: String,
    val password: String,
    val role: String = "USER"
)