package com.dansplugins.factionsystem.command.faction

import com.dansplugins.factionsystem.MedievalFactions
import com.dansplugins.factionsystem.command.faction.addmember.MfFactionAddMemberCommand
import com.dansplugins.factionsystem.command.faction.ally.MfFactionAllyCommand
import com.dansplugins.factionsystem.command.faction.apply.MfFactionApplyCommand
import com.dansplugins.factionsystem.command.faction.approveapp.MfFactionApproveAppCommand
import com.dansplugins.factionsystem.command.faction.bonuspower.MfFactionBonusPowerCommand
import com.dansplugins.factionsystem.command.faction.breakalliance.MfFactionBreakAllianceCommand
import com.dansplugins.factionsystem.command.faction.bypass.MfFactionBypassCommand
import com.dansplugins.factionsystem.command.faction.chat.MfFactionChatCommand
import com.dansplugins.factionsystem.command.faction.claim.MfFactionClaimCommand
import com.dansplugins.factionsystem.command.faction.create.MfFactionCreateCommand
import com.dansplugins.factionsystem.command.faction.declareindependence.MfFactionDeclareIndependenceCommand
import com.dansplugins.factionsystem.command.faction.declarewar.MfFactionDeclareWarCommand
import com.dansplugins.factionsystem.command.faction.denyapp.MfFactionDenyAppCommand
import com.dansplugins.factionsystem.command.faction.dev.MfFactionDevCommand
import com.dansplugins.factionsystem.command.faction.disband.MfFactionDisbandCommand
import com.dansplugins.factionsystem.command.faction.flag.MfFactionFlagCommand
import com.dansplugins.factionsystem.command.faction.grantindependence.MfFactionGrantIndependenceCommand
import com.dansplugins.factionsystem.command.faction.help.MfFactionHelpCommand
import com.dansplugins.factionsystem.command.faction.home.MfFactionHomeCommand
import com.dansplugins.factionsystem.command.faction.info.MfFactionInfoCommand
import com.dansplugins.factionsystem.command.faction.invite.MfFactionInviteCommand
import com.dansplugins.factionsystem.command.faction.invoke.MfFactionInvokeCommand
import com.dansplugins.factionsystem.command.faction.join.MfFactionJoinCommand
import com.dansplugins.factionsystem.command.faction.kick.MfFactionKickCommand
import com.dansplugins.factionsystem.command.faction.law.MfFactionLawCommand
import com.dansplugins.factionsystem.command.faction.leave.MfFactionLeaveCommand
import com.dansplugins.factionsystem.command.faction.list.MfFactionListCommand
import com.dansplugins.factionsystem.command.faction.makepeace.MfFactionMakePeaceCommand
import com.dansplugins.factionsystem.command.faction.map.MfFactionMapCommand
import com.dansplugins.factionsystem.command.faction.members.MfFactionMembersCommand
import com.dansplugins.factionsystem.command.faction.power.MfFactionPowerCommand
import com.dansplugins.factionsystem.command.faction.relationship.MfFactionRelationshipCommand
import com.dansplugins.factionsystem.command.faction.role.MfFactionRoleCommand
import com.dansplugins.factionsystem.command.faction.set.MfFactionSetCommand
import com.dansplugins.factionsystem.command.faction.set.name.MfFactionSetNameCommand
import com.dansplugins.factionsystem.command.faction.sethome.MfFactionSetHomeCommand
import com.dansplugins.factionsystem.command.faction.showapps.MfShowAppsCommand
import com.dansplugins.factionsystem.command.faction.swearfealty.MfFactionSwearFealtyCommand
import com.dansplugins.factionsystem.command.faction.unclaim.MfFactionUnclaimCommand
import com.dansplugins.factionsystem.command.faction.unclaimall.MfFactionUnclaimAllCommand
import com.dansplugins.factionsystem.command.faction.vassalize.MfFactionVassalizeCommand
import com.dansplugins.factionsystem.command.faction.who.MfFactionWhoCommand
import org.bukkit.ChatColor.AQUA
import org.bukkit.ChatColor.GRAY
import org.bukkit.ChatColor.YELLOW
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class MfFactionCommand(private val plugin: MedievalFactions) : CommandExecutor, TabCompleter {

    private val factionHelpCommand = MfFactionHelpCommand(plugin)
    private val factionCreateCommand = MfFactionCreateCommand(plugin)
    private val factionClaimCommand = MfFactionClaimCommand(plugin)
    private val factionLawCommand = MfFactionLawCommand(plugin)
    private val factionAllyCommand = MfFactionAllyCommand(plugin)
    private val factionBreakAllianceCommand = MfFactionBreakAllianceCommand(plugin)
    private val factionInviteCommand = MfFactionInviteCommand(plugin)
    private val factionJoinCommand = MfFactionJoinCommand(plugin)
    private val factionDeclareWarCommand = MfFactionDeclareWarCommand(plugin)
    private val factionMakePeaceCommand = MfFactionMakePeaceCommand(plugin)
    private val factionInfoCommand = MfFactionInfoCommand(plugin)
    private val factionMembersCommand = MfFactionMembersCommand(plugin)
    private val factionRoleCommand = MfFactionRoleCommand(plugin)
    private val factionListCommand = MfFactionListCommand(plugin)
    private val factionUnclaimCommand = MfFactionUnclaimCommand(plugin)
    private val factionUnclaimAllCommand = MfFactionUnclaimAllCommand(plugin)
    private val factionPowerCommand = MfFactionPowerCommand(plugin)
    private val factionWhoCommand = MfFactionWhoCommand(plugin)
    private val factionDisbandCommand = MfFactionDisbandCommand(plugin)
    private val factionInvokeCommand = MfFactionInvokeCommand(plugin)
    private val factionLeaveCommand = MfFactionLeaveCommand(plugin)
    private val factionSetCommand = MfFactionSetCommand(plugin)
    private val factionSetNameCommand = MfFactionSetNameCommand(plugin)
    private val factionVassalizeCommand = MfFactionVassalizeCommand(plugin)
    private val factionSwearFealtyCommand = MfFactionSwearFealtyCommand(plugin)
    private val factionGrantIndependenceCommand = MfFactionGrantIndependenceCommand(plugin)
    private val factionDeclareIndependenceCommand = MfFactionDeclareIndependenceCommand(plugin)
    private val factionKickCommand = MfFactionKickCommand(plugin)
    private val factionMapCommand = MfFactionMapCommand(plugin)
    private val factionSetHomeCommand = MfFactionSetHomeCommand(plugin)
    private val factionHomeCommand = MfFactionHomeCommand(plugin)
    private val factionFlagCommand = MfFactionFlagCommand(plugin)
    private val factionBypassCommand = MfFactionBypassCommand(plugin)
    private val factionChatCommand = MfFactionChatCommand(plugin)
    private val factionBonusPowerCommand = MfFactionBonusPowerCommand(plugin)
    private val factionRelationshipCommand = MfFactionRelationshipCommand(plugin)
    private val factionAddMemberCommand = MfFactionAddMemberCommand(plugin)
    private val factionDevCommand = MfFactionDevCommand(plugin)
    private val factionApplyCommand = MfFactionApplyCommand(plugin)
    private val factionShowAppsCommand = MfShowAppsCommand(plugin)
    private val factionApproveAppCommand = MfFactionApproveAppCommand(plugin)
    private val factionDenyAppCommand = MfFactionDenyAppCommand(plugin)

    private val helpAliases = listOf("help", plugin.language["CmdFactionHelp"])
    private val createAliases = listOf("create", plugin.language["CmdFactionCreate"])
    private val claimAliases = listOf("claim", plugin.language["CmdFactionClaim"])
    private val lawAliases = listOf("law", plugin.language["CmdFactionLaw"])
    private val allyAliases = listOf("ally", plugin.language["CmdFactionAlly"])
    private val breakAllianceAliases = listOf("breakalliance", "ba", plugin.language["CmdFactionBreakAlliance"])
    private val inviteAliases = listOf("invite", plugin.language["CmdFactionInvite"])
    private val joinAliases = listOf("join", plugin.language["CmdFactionJoin"])
    private val declareWarAliases = listOf("declarewar", "dw", plugin.language["CmdFactionDeclareWar"])
    private val makePeaceAliases = listOf("makepeace", "mp", plugin.language["CmdFactionMakePeace"])
    private val infoAliases = listOf("info", plugin.language["CmdFactionInfo"])
    private val membersAliases = listOf("members", plugin.language["CmdFactionMembers"])
    private val roleAliases = listOf("role", plugin.language["CmdFactionRole"])
    private val listAliases = listOf("list", plugin.language["CmdFactionList"])
    private val unclaimAliases = listOf("unclaim", plugin.language["CmdFactionUnclaim"])
    private val unclaimAllAliases = listOf("unclaimall", plugin.language["CmdFactionUnclaimAll"])
    private val powerAliases = listOf("power", plugin.language["CmdFactionPower"])
    private val whoAliases = listOf("who", plugin.language["CmdFactionWho"])
    private val disbandAliases = listOf("disband", plugin.language["CmdFactionDisband"])
    private val invokeAliases = listOf("invoke", plugin.language["CmdFactionInvoke"])
    private val leaveAliases = listOf("leave", plugin.language["CmdFactionLeave"])
    private val setAliases = listOf("set", plugin.language["CmdFactionSet"])
    private val renameAliases = listOf("rename")
    private val vassalizeAliases = listOf("vassalize", "vassalise", plugin.language["CmdFactionVassalize"])
    private val swearFealtyAliases = listOf("swearfealty", plugin.language["CmdFactionSwearFealty"])
    private val grantIndependenceAliases = listOf("grantindependence", plugin.language["CmdFactionGrantIndependence"])
    private val declareIndependenceAliases = listOf("declareindependence", plugin.language["CmdFactionDeclareIndependence"])
    private val kickAliases = listOf("kick", plugin.language["CmdFactionKick"])
    private val mapAliases = listOf("map", plugin.language["CmdFactionMap"])
    private val setHomeAliases = listOf("sethome", plugin.language["CmdFactionSetHome"])
    private val homeAliases = listOf("home", plugin.language["CmdFactionHome"])
    private val flagAliases = listOf("flag", "flags", plugin.language["CmdFactionFlag"])
    private val bypassAliases = listOf("bypass", plugin.language["CmdFactionBypass"])
    private val chatAliases = listOf("chat", plugin.language["CmdFactionChat"])
    private val bonusPowerAliases = listOf("bonuspower", plugin.language["CmdFactionBonusPower"])
    private val relationshipAliases = listOf("relationship", plugin.language["CmdFactionRelationship"])
    private val addMemberAliases = listOf("addmember", plugin.language["CmdFactionAddMember"])
    private val devAliases = if (plugin.config.getBoolean("dev.enableDevCommands")) listOf("dev") else emptyList()
    private val applyAliases = listOf("apply", plugin.language["CmdFactionApply"])
    private val showAppsAliases = listOf("showapps", plugin.language["CmdFactionShowApps"])
    private val approveAppAliases = listOf("approveapp", plugin.language["CmdFactionApproveApp"])
    private val denyAppAliases = listOf("denyapp", plugin.language["CmdFactionDenyApp"])

    private val subcommands = helpAliases +
        createAliases +
        claimAliases +
        lawAliases +
        allyAliases +
        breakAllianceAliases +
        inviteAliases +
        joinAliases +
        declareWarAliases +
        makePeaceAliases +
        infoAliases +
        membersAliases +
        roleAliases +
        listAliases +
        unclaimAliases +
        unclaimAllAliases +
        powerAliases +
        whoAliases +
        disbandAliases +
        invokeAliases +
        leaveAliases +
        setAliases +
        renameAliases +
        vassalizeAliases +
        swearFealtyAliases +
        grantIndependenceAliases +
        declareIndependenceAliases +
        kickAliases +
        mapAliases +
        setHomeAliases +
        homeAliases +
        flagAliases +
        bypassAliases +
        chatAliases +
        bonusPowerAliases +
        relationshipAliases +
        addMemberAliases +
        devAliases +
        applyAliases +
        showAppsAliases +
        approveAppAliases +
        denyAppAliases

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return when (args.firstOrNull()?.lowercase()) {
            in helpAliases -> factionHelpCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in createAliases -> factionCreateCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in claimAliases -> factionClaimCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in lawAliases -> factionLawCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in allyAliases -> factionAllyCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in breakAllianceAliases -> factionBreakAllianceCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in inviteAliases -> factionInviteCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in joinAliases -> factionJoinCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in declareWarAliases -> factionDeclareWarCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in makePeaceAliases -> factionMakePeaceCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in infoAliases -> factionInfoCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in membersAliases -> factionMembersCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in roleAliases -> factionRoleCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in listAliases -> factionListCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in unclaimAliases -> factionUnclaimCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in unclaimAllAliases -> factionUnclaimAllCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in powerAliases -> factionPowerCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in whoAliases -> factionWhoCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in disbandAliases -> factionDisbandCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in invokeAliases -> factionInvokeCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in leaveAliases -> factionLeaveCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in setAliases -> factionSetCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in renameAliases -> factionSetNameCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in vassalizeAliases -> factionVassalizeCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in swearFealtyAliases -> factionSwearFealtyCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in grantIndependenceAliases -> factionGrantIndependenceCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in declareIndependenceAliases -> factionDeclareIndependenceCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in kickAliases -> factionKickCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in mapAliases -> factionMapCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in setHomeAliases -> factionSetHomeCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in homeAliases -> factionHomeCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in flagAliases -> factionFlagCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in bypassAliases -> factionBypassCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in chatAliases -> factionChatCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in bonusPowerAliases -> factionBonusPowerCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in relationshipAliases -> factionRelationshipCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in addMemberAliases -> factionAddMemberCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in devAliases -> factionDevCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in applyAliases -> factionApplyCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in showAppsAliases -> factionShowAppsCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in approveAppAliases -> factionApproveAppCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            in denyAppAliases -> factionDenyAppCommand.onCommand(sender, command, label, args.drop(1).toTypedArray())
            else -> {
                sender.sendMessage("$AQUA${plugin.language["MedievalFactionsTitle", plugin.description.version]}")
                sender.sendMessage("$GRAY${plugin.language["DeveloperList", plugin.description.authors.joinToString()]}")
                sender.sendMessage("$GRAY${plugin.language["WikiLink"]}")
                sender.sendMessage("$GRAY${plugin.language["CurrentLanguage", plugin.config.getString("language") ?: "en_US"]}")
                sender.sendMessage("$YELLOW${plugin.language["CommandFactionUsage"]}")
                true
            }
        }
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ) = when {
        args.isEmpty() -> subcommands
        args.size == 1 -> subcommands.filter { it.startsWith(args[0].lowercase()) }
        else -> when (args.first().lowercase()) {
            in helpAliases -> factionHelpCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in createAliases -> factionCreateCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in lawAliases -> factionLawCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in allyAliases -> factionAllyCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in breakAllianceAliases -> factionBreakAllianceCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in inviteAliases -> factionInviteCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in joinAliases -> factionJoinCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in declareWarAliases -> factionDeclareWarCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in makePeaceAliases -> factionMakePeaceCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in infoAliases -> factionInfoCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in membersAliases -> factionMembersCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in roleAliases -> factionRoleCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in listAliases -> factionListCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in claimAliases -> factionClaimCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in unclaimAliases -> factionUnclaimCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in unclaimAllAliases -> factionUnclaimAllCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in powerAliases -> factionPowerCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in whoAliases -> factionWhoCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in disbandAliases -> factionDisbandCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in invokeAliases -> factionInvokeCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in leaveAliases -> factionLeaveCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in setAliases -> factionSetCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in renameAliases -> factionSetNameCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in vassalizeAliases -> factionVassalizeCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in swearFealtyAliases -> factionSwearFealtyCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in grantIndependenceAliases -> factionGrantIndependenceCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in declareIndependenceAliases -> factionDeclareIndependenceCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in kickAliases -> factionKickCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in mapAliases -> factionMapCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in setHomeAliases -> factionSetHomeCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in homeAliases -> factionHomeCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in flagAliases -> factionFlagCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in bypassAliases -> factionBypassCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in chatAliases -> factionChatCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in bonusPowerAliases -> factionBonusPowerCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in relationshipAliases -> factionRelationshipCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in addMemberAliases -> factionAddMemberCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in devAliases -> factionDevCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in applyAliases -> factionApplyCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in approveAppAliases -> factionApproveAppCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            in denyAppAliases -> factionDenyAppCommand.onTabComplete(sender, command, label, args.drop(1).toTypedArray())
            else -> emptyList()
        }
    }
}
