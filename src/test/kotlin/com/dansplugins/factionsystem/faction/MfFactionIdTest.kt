package com.dansplugins.factionsystem.faction

import kotlin.test.Test
import kotlin.test.assertEquals

class MfFactionIdTest {

    @Test fun testGenerate() {
        val factionId = MfFactionId.generate()
        assertEquals(36, factionId.value.length)
    }
}
