package com.fooddelivery.authservice.service

import com.fooddelivery.authservice.dto.AuthRequest
import com.fooddelivery.authservice.dto.AuthResponse
import com.fooddelivery.authservice.repository.UserRepository
import com.fooddelivery.authservice.security.JwtUtil
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val jwtUtil: JwtUtil
) {
    private val passwordEncoder = BCryptPasswordEncoder()

    fun authenticate(request: AuthRequest): AuthResponse {
        val user = userRepository.findByUsername(request.username)
            .orElseThrow { IllegalArgumentException("Invalid username or password") }

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("Invalid username or password")
        }

        val token = jwtUtil.generateToken(user.username, user.role.name)
        return AuthResponse(token)
    }
}