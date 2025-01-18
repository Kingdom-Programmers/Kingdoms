package com.dansplugins.factionsystem.faction.permission

import com.dansplugins.factionsystem.MedievalFactions
import com.dansplugins.factionsystem.chat.MfFactionChatChannel
import com.dansplugins.factionsystem.faction.MfFaction
import com.dansplugins.factionsystem.faction.MfFactionId
import com.dansplugins.factionsystem.faction.flag.MfFlag
import com.dansplugins.factionsystem.faction.permission.permissions.Chat
import com.dansplugins.factionsystem.faction.permission.permissions.DeleteRole
import com.dansplugins.factionsystem.faction.permission.permissions.ModifyRole
import com.dansplugins.factionsystem.faction.permission.permissions.SetFlag
import com.dansplugins.factionsystem.faction.permission.permissions.SetMemberRole
import com.dansplugins.factionsystem.faction.permission.permissions.SetRolePermission
import com.dansplugins.factionsystem.faction.permission.permissions.ViewRole
import com.dansplugins.factionsystem.faction.role.MfFactionRoleId
import com.dansplugins.factionsystem.faction.role.MfFactionRoles
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

class MfFactionPermissions(private val plugin: MedievalFactions) {

    private val _permissionTypes = CopyOnWriteArrayList<MfFactionPermissionType>()
    val permissionTypes: List<MfFactionPermissionType>
        get() = Collections.unmodifiableList(_permissionTypes)

    init {
        _permissionTypes.addAll(
            listOf(
                wrapSimplePermission("ADD_LAW", plugin.language["FactionPermissionAddLaw"], false),
                wrapSimplePermission("EDIT_LAW", plugin.language["FactionPermissionEditLaw"], false),
                wrapSimplePermission("MOVE_LAW", plugin.language["FactionPermissionMoveLaw"], false),
                wrapSimplePermission("REMOVE_LAW", plugin.language["FactionPermissionRemoveLaw"], false),
                wrapSimplePermission("LIST_LAWS", plugin.language["FactionPermissionListLaws"], true),
                wrapSimplePermission("REQUEST_ALLIANCE", plugin.language["FactionPermissionRequestAlliance"], false),
                wrapSimplePermission("BREAK_ALLIANCE", plugin.language["FactionPermissionBreakAlliance"], false),
                wrapSimplePermission("TOGGLE_AUTOCLAIM", plugin.language["FactionPermissionToggleAutoclaim"], false),
                Chat(plugin),
                wrapSimplePermission("CHAT_HISTORY", plugin.language["FactionPermissionChatHistory"], true),
                wrapSimplePermission("CLAIM", plugin.language["FactionPermissionClaim"], false),
                wrapSimplePermission("UNCLAIM", plugin.language["FactionPermissionUnclaim"], false),
                wrapSimplePermission("DECLARE_INDEPENDENCE", plugin.language["FactionPermissionDeclareIndependence"], false),
                wrapSimplePermission("SWEAR_FEALTY", plugin.language["FactionPermissionSwearFealty"], false),
                wrapSimplePermission("GRANT_INDEPENDENCE", plugin.language["FactionPermissionGrantIndependence"], false),
                wrapSimplePermission("VASSALIZE", plugin.language["FactionPermissionVassalize"], false),
                wrapSimplePermission("DECLARE_WAR", plugin.language["FactionPermissionDeclareWar"], false),
                wrapSimplePermission("MAKE_PEACE", plugin.language["FactionPermissionMakePeace"], false),
                wrapSimplePermission("CHANGE_NAME", plugin.language["FactionPermissionChangeName"], false),
                wrapSimplePermission("CHANGE_DESCRIPTION", plugin.language["FactionPermissionChangeDescription"], false),
                wrapSimplePermission("CHANGE_PREFIX", plugin.language["FactionPermissionChangePrefix"], false),
                wrapSimplePermission("DISBAND", plugin.language["FactionPermissionDisband"], false),
                SetFlag(plugin),
                wrapSimplePermission("VIEW_FLAGS", plugin.language["FactionPermissionViewFlags"], true),
                wrapSimplePermission("CREATE_GATE", plugin.language["FactionPermissionCreateGate"], false),
                wrapSimplePermission("REMOVE_GATE", plugin.language["FactionPermissionRemoveGate"], false),
                wrapSimplePermission("SET_HOME", plugin.language["FactionPermissionSetHome"], false),
                wrapSimplePermission("GO_HOME", plugin.language["FactionPermissionGoHome"], true),
                wrapSimplePermission("VIEW_INFO", plugin.language["FactionPermissionViewInfo"], true),
                wrapSimplePermission("VIEW_STATS", plugin.language["FactionPermissionViewStats"], true),
                wrapSimplePermission("INVITE", plugin.language["FactionPermissionInvite"], true),
                wrapSimplePermission("INVOKE", plugin.language["FactionPermissionInvoke"], false),
                wrapSimplePermission("KICK", plugin.language["FactionPermissionKick"], false),
                ViewRole(plugin),
                ModifyRole(plugin),
                SetMemberRole(plugin),
                DeleteRole(plugin),
                wrapSimplePermission("LIST_ROLES", plugin.language["FactionPermissionListRoles"], true),
                wrapSimplePermission("LIST_MEMBERS", plugin.language["FactionPermissionListMembers"], true),
                wrapSimplePermission("CREATE_ROLE", plugin.language["FactionPermissionCreateRole"], false),
                wrapSimplePermission("SET_DEFAULT_ROLE", plugin.language["FactionPermissionSetDefaultRole"], false),
                wrapSimplePermission("APPROVE_APP", plugin.language["FactionPermissionApproveApp"], false),
                SetRolePermission(plugin)
            )
        )
    }

    fun addPermissionType(permissionType: MfFactionPermissionType) {
        _permissionTypes.add(permissionType)
    }

    val addLaw = parse("ADD_LAW")!!
    val editLaw = parse("EDIT_LAW")!!
    val moveLaw = parse("MOVE_LAW")!!
    val removeLaw = parse("REMOVE_LAW")!!
    val listLaws = parse("LIST_LAWS")!!
    val requestAlliance = parse("REQUEST_ALLIANCE")!!
    val breakAlliance = parse("BREAK_ALLIANCE")!!
    val toggleAutoclaim = parse("TOGGLE_AUTOCLAIM")!!
    fun chat(chatChannel: MfFactionChatChannel) = parse("CHAT(${chatChannel.name})")!!
    val chatHistory = parse("CHAT_HISTORY")!!
    val claim = parse("CLAIM")!!
    val unclaim = parse("UNCLAIM")!!
    val declareIndependence = parse("DECLARE_INDEPENDENCE")!!
    val swearFealty = parse("SWEAR_FEALTY")!!
    val grantIndependence = parse("GRANT_INDEPENDENCE")!!
    val vassalize = parse("VASSALIZE")!!
    val declareWar = parse("DECLARE_WAR")!!
    val makePeace = parse("MAKE_PEACE")!!
    val changeName = parse("CHANGE_NAME")!!
    val changeDescription = parse("CHANGE_DESCRIPTION")!!
    val changePrefix = parse("CHANGE_PREFIX")!!
    val disband = parse("DISBAND")!!
    fun setFlag(flag: MfFlag<out Any>) = parse("SET_FLAG(${flag.name})")!!
    val viewFlags = parse("VIEW_FLAGS")!!
    val createGate = parse("CREATE_GATE")!!
    val removeGate = parse("REMOVE_GATE")!!
    val setHome = parse("SET_HOME")!!
    val goHome = parse("GO_HOME")!!
    val viewInfo = parse("VIEW_INFO")!!
    val viewStats = parse("VIEW_STATS")!!
    val invite = parse("INVITE")!!
    val invoke = parse("INVOKE")!!
    val kick = parse("KICK")!!
    fun viewRole(roleId: MfFactionRoleId) = parse("VIEW_ROLE(${roleId.value})")!!
    fun modifyRole(roleId: MfFactionRoleId) = parse("MODIFY_ROLE(${roleId.value})")!!
    fun setMemberRole(roleId: MfFactionRoleId) = parse("SET_MEMBER_ROLE(${roleId.value})")!!
    fun deleteRole(roleId: MfFactionRoleId) = parse("DELETE_ROLE(${roleId.value})")!!
    val listRoles = parse("LIST_ROLES")!!
    val listMembers = parse("LIST_MEMBERS")!!
    val createRole = parse("CREATE_ROLE")!!
    val setDefaultRole = parse("SET_DEFAULT_ROLE")!!
    val approveApp = parse("APPROVE_APP")!! // TODO: fix null exception
    val denyApp = parse("DENY_APP")!!
    fun setRolePermission(permission: MfFactionPermission) = parse("SET_ROLE_PERMISSION(${permission.name})")!!

    fun permissionsFor(factionId: MfFactionId, roles: MfFactionRoles): List<MfFactionPermission> = permissionsFor(factionId, roles.map { it.id })
    fun permissionsFor(faction: MfFaction) = permissionsFor(faction.id, faction.roles)
    fun permissionsFor(factionId: MfFactionId, roleIds: List<MfFactionRoleId>) = permissionTypes.flatMap { type -> type.permissionsFor(factionId, roleIds) }

    fun parse(name: String) = permissionTypes.firstNotNullOfOrNull { type -> type.parse(name) }

    fun wrapSimplePermission(name: String, translation: String, default: Boolean) = wrapSimplePermission(name, { translation }, default)

    fun wrapSimplePermission(name: String, translate: (faction: MfFaction) -> String, default: Boolean) = object : MfFactionPermissionType() {
        val permission = MfFactionPermission(name, translate, default)
        override fun parse(name: String): MfFactionPermission? {
            return if (name == permission.name) permission else null
        }

        override fun permissionsFor(factionId: MfFactionId, roleIds: List<MfFactionRoleId>): List<MfFactionPermission> {
            return listOf(permission)
        }
    }
}
