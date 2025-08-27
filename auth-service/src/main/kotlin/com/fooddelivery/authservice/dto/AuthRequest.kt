package com.fooddelivery.authservice.dto

data class AuthRequest(
    val username: String,
    val password: String
)