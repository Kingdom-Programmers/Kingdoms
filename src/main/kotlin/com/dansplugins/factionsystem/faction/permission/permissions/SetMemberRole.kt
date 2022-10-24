package com.dansplugins.factionsystem.faction.permission.permissions

import com.dansplugins.factionsystem.MedievalFactions
import com.dansplugins.factionsystem.faction.permission.MfFactionPermission
import com.dansplugins.factionsystem.faction.permission.MfFactionPermissionType
import com.dansplugins.factionsystem.faction.role.MfFactionRoleId

class SetMemberRole(private val plugin: MedievalFactions) : MfFactionPermissionType() {
    override fun parse(name: String): MfFactionPermission? =
        if (name.matches(Regex("SET_MEMBER_ROLE\\((.+)\\)"))) {
            Regex("SET_MEMBER_ROLE\\((.+)\\)").find(name)
                ?.groupValues?.get(1)
                ?.let(::MfFactionRoleId)
                ?.let(::permissionFor)
        } else {
            null
        }

    override fun permissionsFor(roleIds: List<MfFactionRoleId>): List<MfFactionPermission> {
        return roleIds.map(::permissionFor)
    }

    private fun permissionFor(roleId: MfFactionRoleId) = MfFactionPermission(
        "SET_MEMBER_ROLE(${roleId.value})",
        { faction -> plugin.language["FactionPermissionSetMemberRole", faction.getRole(roleId)?.name ?: ""] },
        true
    )
}