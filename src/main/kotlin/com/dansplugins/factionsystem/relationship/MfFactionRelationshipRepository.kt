package com.dansplugins.factionsystem.relationship

import com.dansplugins.factionsystem.faction.MfFactionId

interface MfFactionRelationshipRepository {

    fun getFactionRelationships(factionId: MfFactionId, targetId: MfFactionId): List<MfFactionRelationship>
    fun upsert(relationship: MfFactionRelationship): MfFactionRelationship
    fun delete(relationshipId: MfFactionRelationshipId)

}