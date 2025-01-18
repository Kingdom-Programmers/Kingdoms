package com.dansplugins.factionsystem.faction

import com.dansplugins.factionsystem.player.MfPlayerId
import kotlin.test.Test
import kotlin.test.assertEquals

class MfFactionApplicationTest {

    @Test fun testInitialization() {
        // prepare
        val mfPlayerId = MfPlayerId("test")
        val mfFactionId = MfFactionId.generate()

        // execute
        val mfFactionApplication = MfFactionApplication(mfFactionId, mfPlayerId)

        // verify
        assertEquals(mfPlayerId, mfFactionApplication.applicantId)
        assertEquals(mfFactionId, mfFactionApplication.factionId)
    }
}
