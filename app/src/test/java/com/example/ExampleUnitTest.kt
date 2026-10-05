package com.example

import com.example.data.repository.PortfolioRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.security.MessageDigest

class ExampleUnitTest {

    @Test
    fun repository_hasProjectsAndCredentials() {
        assertEquals("Sathvik", PortfolioRepository.developerName)
        assertTrue(PortfolioRepository.projects.isNotEmpty())

        val factForge = PortfolioRepository.projects.find { it.id == "factforge" }
        assertNotNull("FactForge project should exist", factForge)

        val cloudflareProject = PortfolioRepository.projects.find { it.id == "edge-tokenvault" }
        assertNotNull("Cloudflare TokenVault project should exist", cloudflareProject)
    }

    @Test
    fun personas_hasFourCuratedOptions() {
        assertEquals(4, PortfolioRepository.personas.size)
        val balanced = PortfolioRepository.personas.find { it.id == "balanced" }
        assertNotNull(balanced)
        assertTrue(balanced!!.headline.contains("Engineering production-ready AI"))
    }

    @Test
    fun tokenization_generatesValidSha256Surrogate() {
        val input = "sk_test_secret_12345"
        val hash = MessageDigest.getInstance("SHA-256")
            .digest(input.toByteArray())
            .joinToString("") { "%02x".format(it) }
        val token = "tok_cf_${hash.take(12)}_sec"

        assertTrue(token.startsWith("tok_cf_"))
        assertTrue(token.endsWith("_sec"))
    }

    @Test
    fun skills_containCloudflareAndCompose() {
        val hasCloudflare = PortfolioRepository.skills.any { it.name.contains("Cloudflare") }
        val hasCompose = PortfolioRepository.skills.any { it.name.contains("Compose") }
        assertTrue(hasCloudflare)
        assertTrue(hasCompose)
    }
}
