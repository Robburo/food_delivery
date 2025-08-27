package com.fooddelivery.authservice.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil(
    @Value("\${jwt.secret}") secret: String,
    @Value("\${jwt.issuer}") private val issuer: String,
    @Value("\${jwt.expiration}") private val expirationMillis: Long
) {

    private val key: SecretKey = Keys.hmacShaKeyFor(secret.toByteArray())

    fun generateToken(username: String, role: String): String {
        val now = Date()
        val expiry = Date(now.time + expirationMillis)

        return Jwts.builder()
            .setSubject(username)
            .claim("role", role)
            .setIssuer(issuer)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(key, SignatureAlgorithm.HS256)   // ✅ HMAC instead of RSA
            .compact()
    }
}