package com.fooddelivery.authservice.dto

data class RegisterResponse(
    val id: Long,
    val username: String,
    val role: String
)