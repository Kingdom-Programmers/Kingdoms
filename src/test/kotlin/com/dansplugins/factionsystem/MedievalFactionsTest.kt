package com.dansplugins.factionsystem

import be.seeseemelk.mockbukkit.MockBukkit
import be.seeseemelk.mockbukkit.ServerMock
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

/**
 * Relevant: https://mockbukkit.readthedocs.io/en/latest/getting_started.html
 */
class MedievalFactionsTest {
    private lateinit var server: ServerMock
    private lateinit var plugin: MedievalFactions

    @BeforeEach
    fun setUp() {
        server = MockBukkit.mock() // start the mock server
        plugin = MockBukkit.load(MedievalFactions::class.java) // load plugin under test
    }

    @AfterEach
    fun tearDown() {
        // Stop the mock server
        MockBukkit.unmock();
    }

    @Test
    fun testSomething() {
        // Your test code here
    }
}