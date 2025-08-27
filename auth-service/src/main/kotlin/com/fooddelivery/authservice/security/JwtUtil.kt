package com.fooddelivery.authservice.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil(
    private val rsaKeyProvider: RsaKeyProvider
) {
    private val privateKey = rsaKeyProvider.keyPair.private
    private val publicKey = rsaKeyProvider.keyPair.public
    private val issuer = "food-delivery-auth"
    private val expirationMillis: Long = 3600000 // 1 hour

    fun generateToken(username: String, role: String): String {
        val now = Date()
        val expiry = Date(now.time + expirationMillis)

        return Jwts.builder()
            .setSubject(username)
            .claim("role", role)
            .setIssuer(issuer)
            .setIssuedAt(now)
            .setExpiration(expiry)
            .signWith(privateKey, SignatureAlgorithm.RS256)
            .compact()
    }

    fun getPublicKey() = publicKey
}