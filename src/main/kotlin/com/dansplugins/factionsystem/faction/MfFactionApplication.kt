package com.dansplugins.factionsystem.faction

import com.dansplugins.factionsystem.player.MfPlayerId

public data class MfFactionApplication(
    @get:JvmName("getFactionId")
    val factionId: MfFactionId,
    @get:JvmName("getApplicantId")
    val applicantId: MfPlayerId
)
