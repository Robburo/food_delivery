package com.fooddelivery.authservice.service

import com.fooddelivery.authservice.domain.UserEntity
import com.fooddelivery.authservice.domain.UserRole
import com.fooddelivery.authservice.dto.AuthRequest
import com.fooddelivery.authservice.dto.AuthResponse
import com.fooddelivery.authservice.dto.RegisterRequest
import com.fooddelivery.authservice.dto.RegisterResponse
import com.fooddelivery.authservice.repository.UserRepository
import com.fooddelivery.authservice.security.JwtUtil
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

    @Transactional
    fun register(request: RegisterRequest): RegisterResponse {
        if (userRepository.findByUsername(request.username).isPresent) {
            throw IllegalArgumentException("Username already exists")
        }

        val encodedPassword = passwordEncoder.encode(request.password)
        val user = UserEntity(
            username = request.username,
            password = encodedPassword,
            role = UserRole.valueOf(request.role.uppercase())
        )
        val saved = userRepository.save(user)
        return RegisterResponse(
            id = saved.id,
            username = saved.username,
            role = saved.role.name
        )
    }
}