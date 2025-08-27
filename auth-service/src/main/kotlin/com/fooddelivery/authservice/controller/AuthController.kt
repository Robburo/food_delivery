package com.fooddelivery.authservice.controller

import com.fooddelivery.authservice.dto.AuthRequest
import com.fooddelivery.authservice.dto.AuthResponse
import com.fooddelivery.authservice.dto.RegisterRequest
import com.fooddelivery.authservice.dto.RegisterResponse
import com.fooddelivery.authservice.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/token")
    fun getToken(@RequestBody request: AuthRequest): ResponseEntity<AuthResponse> =
        ResponseEntity.ok(authService.authenticate(request))

    @PostMapping("/register")
    fun register(@RequestBody request: RegisterRequest): ResponseEntity<RegisterResponse> =
        ResponseEntity.ok(authService.register(request))
}