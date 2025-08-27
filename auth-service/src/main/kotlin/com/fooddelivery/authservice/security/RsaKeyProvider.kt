package com.fooddelivery.authservice.security

import org.springframework.stereotype.Component
import java.security.KeyPair
import java.security.KeyPairGenerator

@Component
class RsaKeyProvider {
    val keyPair: KeyPair = generateRsaKey()

    private fun generateRsaKey(): KeyPair {
        val generator = KeyPairGenerator.getInstance("RSA")
        generator.initialize(2048)
        return generator.generateKeyPair()
    }
}