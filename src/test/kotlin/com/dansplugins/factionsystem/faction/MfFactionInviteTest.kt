package com.dansplugins.factionsystem.faction

import com.dansplugins.factionsystem.player.MfPlayerId
import kotlin.test.Test
import kotlin.test.assertEquals

class MfFactionInviteTest {

    @Test fun testInitialization() {
        // prepare
        val mfPlayerId = MfPlayerId("test")

        // execute
        val mfFactionInvite = MfFactionInvite(mfPlayerId)

        // verify
        assertEquals(mfPlayerId, mfFactionInvite.playerId)
    }
}
