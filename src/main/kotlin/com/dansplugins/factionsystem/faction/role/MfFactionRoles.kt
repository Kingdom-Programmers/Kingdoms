package com.dansplugins.factionsystem.faction.role

import com.dansplugins.factionsystem.MedievalFactions
import com.dansplugins.factionsystem.chat.MfFactionChatChannel.ALLIES
import com.dansplugins.factionsystem.chat.MfFactionChatChannel.FACTION
import com.dansplugins.factionsystem.chat.MfFactionChatChannel.VASSALS
import com.dansplugins.factionsystem.faction.MfFactionId

data class MfFactionRoles(
    @get:JvmName("getDefaultRoleId")
    val defaultRoleId: MfFactionRoleId,
    val roles: List<MfFactionRole>
) : List<MfFactionRole> by roles {

    val default: MfFactionRole
        get() = getRole(defaultRoleId)!!

    companion object {
        fun defaults(plugin: MedievalFactions, factionId: MfFactionId): MfFactionRoles {
            val member = MfFactionRole(plugin, name = "Member")
            val officer = MfFactionRole(
                plugin,
                name = "Officer",
                permissionsByName = buildMap {
                    put(plugin.factionPermissions.requestAlliance.name, true)
                    put(plugin.factionPermissions.declareWar.name, true)
                    put(plugin.factionPermissions.makePeace.name, true)
                    put(plugin.factionPermissions.setHome.name, true)
                    put(plugin.factionPermissions.kick.name, true)
                    put(plugin.factionPermissions.viewRole(member.id).name, true)
                    put(plugin.factionPermissions.modifyRole(member.id).name, true)
                    put(plugin.factionPermissions.deleteRole(member.id).name, true)
                    put(plugin.factionPermissions.setMemberRole(member.id).name, true)
                    put(plugin.factionPermissions.setRolePermission(plugin.factionPermissions.listLaws).name, true)
                    put(plugin.factionPermissions.setRolePermission(plugin.factionPermissions.chat(FACTION)).name, true)
                    put(plugin.factionPermissions.setRolePermission(plugin.factionPermissions.chat(VASSALS)).name, true)
                    put(plugin.factionPermissions.setRolePermission(plugin.factionPermissions.chat(ALLIES)).name, true)
                    put(plugin.factionPermissions.setRolePermission(plugin.factionPermissions.viewFlags).name, true)
                    put(plugin.factionPermissions.setRolePermission(plugin.factionPermissions.goHome).name, true)
                    put(plugin.factionPermissions.setRolePermission(plugin.factionPermissions.viewInfo).name, true)
                    put(plugin.factionPermissions.setRolePermission(plugin.factionPermissions.viewStats).name, true)
                    put(plugin.factionPermissions.setRolePermission(plugin.factionPermissions.invite).name, true)
                    put(plugin.factionPermissions.setRolePermission(plugin.factionPermissions.viewRole(member.id)).name, true)
                    put(plugin.factionPermissions.setRolePermission(plugin.factionPermissions.requestAlliance).name, true)
                    put(plugin.factionPermissions.setRolePermission(plugin.factionPermissions.declareWar).name, true)
                    put(plugin.factionPermissions.setRolePermission(plugin.factionPermissions.makePeace).name, true)
                    put(plugin.factionPermissions.setRolePermission(plugin.factionPermissions.setHome).name, true)
                    put(plugin.factionPermissions.setRolePermission(plugin.factionPermissions.kick).name, true)
                    put(plugin.factionPermissions.setRolePermission(plugin.factionPermissions.modifyRole(member.id)).name, true)
                    put(plugin.factionPermissions.setRolePermission(plugin.factionPermissions.listMembers).name, true)
                    put(plugin.factionPermissions.createRole.name, true)
                    put(plugin.factionPermissions.approveApp.name, true)
                    put(plugin.factionPermissions.denyApp.name, true)
                }
            )
            val ownerId = MfFactionRoleId.generate()
            val owner = MfFactionRole(
                plugin,
                id = ownerId,
                name = "Owner",
                permissionsByName = buildMap {
                    put(plugin.factionPermissions.addLaw.name, true)
                    put(plugin.factionPermissions.editLaw.name, true)
                    put(plugin.factionPermissions.moveLaw.name, true)
                    put(plugin.factionPermissions.removeLaw.name, true)
                    put(plugin.factionPermissions.listLaws.name, true)
                    put(plugin.factionPermissions.requestAlliance.name, true)
                    put(plugin.factionPermissions.breakAlliance.name, true)
                    put(plugin.factionPermissions.toggleAutoclaim.name, true)
                    put(plugin.factionPermissions.setRolePermission(plugin.factionPermissions.chat(FACTION)).name, true)
                    put(plugin.factionPermissions.setRolePermission(plugin.factionPermissions.chat(VASSALS)).name, true)
                    put(plugin.factionPermissions.setRolePermission(plugin.factionPermissions.chat(ALLIES)).name, true)
                    put(plugin.factionPermissions.claim.name, true)
                    put(plugin.factionPermissions.unclaim.name, true)
                    put(plugin.factionPermissions.declareIndependence.name, true)
                    put(plugin.factionPermissions.swearFealty.name, true)
                    put(plugin.factionPermissions.grantIndependence.name, true)
                    put(plugin.factionPermissions.vassalize.name, true)
                    put(plugin.factionPermissions.declareWar.name, true)
                    put(plugin.factionPermissions.makePeace.name, true)
                    put(plugin.factionPermissions.changeName.name, true)
                    put(plugin.factionPermissions.changeDescription.name, true)
                    put(plugin.factionPermissions.changePrefix.name, true)
                    put(plugin.factionPermissions.disband.name, true)
                    plugin.flags.forEach { flag ->
                        put(plugin.factionPermissions.setFlag(flag).name, true)
                    }
                    put(plugin.factionPermissions.viewFlags.name, true)
                    put(plugin.factionPermissions.createGate.name, true)
                    put(plugin.factionPermissions.removeGate.name, true)
                    put(plugin.factionPermissions.setHome.name, true)
                    put(plugin.factionPermissions.goHome.name, true)
                    put(plugin.factionPermissions.viewInfo.name, true)
                    put(plugin.factionPermissions.viewStats.name, true)
                    put(plugin.factionPermissions.invite.name, true)
                    put(plugin.factionPermissions.invoke.name, true)
                    put(plugin.factionPermissions.kick.name, true)
                    put(plugin.factionPermissions.viewRole(member.id).name, true)
                    put(plugin.factionPermissions.viewRole(officer.id).name, true)
                    put(plugin.factionPermissions.viewRole(ownerId).name, true)
                    put(plugin.factionPermissions.modifyRole(member.id).name, true)
                    put(plugin.factionPermissions.modifyRole(officer.id).name, true)
                    put(plugin.factionPermissions.modifyRole(ownerId).name, true)
                    put(plugin.factionPermissions.setMemberRole(member.id).name, true)
                    put(plugin.factionPermissions.setMemberRole(officer.id).name, true)
                    put(plugin.factionPermissions.setMemberRole(ownerId).name, true)
                    put(plugin.factionPermissions.deleteRole(member.id).name, true)
                    put(plugin.factionPermissions.deleteRole(officer.id).name, true)
                    put(plugin.factionPermissions.deleteRole(ownerId).name, true)
                    put(plugin.factionPermissions.listMembers.name, true)
                    put(plugin.factionPermissions.createRole.name, true)
                    put(plugin.factionPermissions.setDefaultRole.name, true)
                    put(plugin.factionPermissions.approveApp.name, true)
                    put(plugin.factionPermissions.denyApp.name, true)
                    putAll(
                        plugin.factionPermissions.permissionsFor(factionId, listOf(member.id, officer.id, ownerId))
                            .map { permission -> plugin.factionPermissions.setRolePermission(permission).name to true }
                    )
                }
            )
            return MfFactionRoles(member.id, listOf(owner, officer, member))
        }
    }

    @JvmName("getRoleByRoleId")
    fun getRole(roleId: MfFactionRoleId) = roles.singleOrNull { it.id.value == roleId.value }

    @JvmName("getRoleByName")
    fun getRole(name: String) = getRole(MfFactionRoleId(name)) ?: roles.singleOrNull { it.name.equals(name, ignoreCase = true) }
}
