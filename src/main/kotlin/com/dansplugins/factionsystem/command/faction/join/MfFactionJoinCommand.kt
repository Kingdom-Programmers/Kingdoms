package com.dansplugins.factionsystem.command.faction.join

import com.dansplugins.factionsystem.MedievalFactions
import com.dansplugins.factionsystem.faction.MfFaction
import com.dansplugins.factionsystem.faction.MfFactionId
import com.dansplugins.factionsystem.faction.MfFactionMember
import com.dansplugins.factionsystem.player.MfPlayer
import dev.forkhandles.result4k.onFailure
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.HoverEvent.Action.SHOW_TEXT
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.chat.hover.content.Text
import org.bukkit.ChatColor.GREEN
import org.bukkit.ChatColor.RED
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.util.logging.Level
import net.md_5.bungee.api.ChatColor as SpigotChatColor

class MfFactionJoinCommand(private val plugin: MedievalFactions) : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.hasPermission("mf.join")) {
            sender.sendMessage("$RED${plugin.language["CommandFactionJoinNoPermission"]}")
            return true
        }
        val hasUninvitedJoinPermission = sender.hasPermission("mf.force.join")
        var lastArgOffset = 0
        val force = if (hasUninvitedJoinPermission && args.lastOrNull() == "-f") {
            lastArgOffset = 1
            true
        } else {
            false
        }
        if (args.size <= lastArgOffset) {
            sender.sendMessage("$RED${plugin.language["CommandFactionJoinUsage"]}")
            return true
        }
        if (sender !is Player) {
            sender.sendMessage("$RED${plugin.language["CommandFactionJoinNotAPlayer"]}")
            return true
        }
        plugin.server.scheduler.runTaskAsynchronously(
            plugin,
            Runnable {
                val playerService = plugin.services.playerService
                val mfPlayer = playerService.getPlayer(sender)
                    ?: playerService.save(MfPlayer(plugin, sender)).onFailure {
                        sender.sendMessage("$RED${plugin.language["CommandFactionJoinFailedToSavePlayer"]}")
                        plugin.logger.log(Level.SEVERE, "Failed to save player: ${it.reason.message}", it.reason.cause)
                        return@Runnable
                    }
                val factionService = plugin.services.factionService
                val playerFaction = factionService.getFaction(mfPlayer.id)
                if (playerFaction != null) {
                    sender.sendMessage("$RED${plugin.language["CommandFactionJoinAlreadyInFaction", playerFaction.name]}")
                    return@Runnable
                }

                val faction = factionService.getFaction(MfFactionId(args.dropLast(lastArgOffset).joinToString(" ")))
                    ?: factionService.getFaction(args.dropLast(lastArgOffset).joinToString(" "))
                if (faction == null) {
                    sender.sendMessage("$RED${plugin.language["CommandFactionJoinInvalidFaction"]}")
                    return@Runnable
                }
                if (!faction.invites.any { it.playerId == mfPlayer.id } && !force) {
                    if (hasUninvitedJoinPermission) {
                        confirmJoin(sender, faction)
                    } else {
                        sender.sendMessage("$RED${plugin.language["CommandFactionJoinNotInvited"]}")
                    }
                    return@Runnable
                }
                val maxMembers = plugin.config.getInt("factions.maxMembers")
                if (maxMembers > 0 && faction.members.size >= maxMembers) {
                    sender.sendMessage("$RED${plugin.language["CommandFactionJoinFactionFull"]}")
                    return@Runnable
                }
                val updatedFaction = factionService.save(
                    faction.copy(
                        members = faction.members + MfFactionMember(mfPlayer.id, faction.roles.default),
                        invites = faction.invites.filter { it.playerId != mfPlayer.id }
                    )
                ).onFailure {
                    sender.sendMessage("$RED${plugin.language["CommandFactionJoinFailedToSaveFaction"]}")
                    plugin.logger.log(Level.SEVERE, "Failed to save faction: ${it.reason.message}", it.reason.cause)
                    return@Runnable
                }
                updatedFaction.sendMessage(
                    plugin.language["FactionNewMemberNotificationTitle", sender.name],
                    plugin.language["FactionNewMemberNotificationBody", sender.name]
                )
                sender.sendMessage(
                    "$GREEN${plugin.language["CommandFactionJoinSuccess", faction.name]}"
                )
                try {
                    factionService.cancelAllApplicationsForPlayer(mfPlayer)
                } catch (e: Exception) {
                    sender.sendMessage("${org.bukkit.ChatColor.RED}${plugin.language["CommandFactionApproveAppFailedToCancelApplications"]}") // TODO: add to language file
                    plugin.logger.log(Level.SEVERE, "Failed to cancel applications: ${e.message}", e)
                }
            }
        )
        return true
    }

    private fun confirmJoin(player: Player, faction: MfFaction) {
        player.sendMessage("$RED${plugin.language["CommandFactionJoinConfirmNoInvitation", faction.name]}")
        player.spigot().sendMessage(
            TextComponent(
                plugin.language["CommandFactionJoinConfirmNoInvitationConfirmButton"]
            ).apply {
                color = SpigotChatColor.GREEN
                hoverEvent = HoverEvent(SHOW_TEXT, Text(plugin.language["CommandFactionJoinConfirmNoInvitationConfirmButtonHover", faction.name]))
                clickEvent = ClickEvent(RUN_COMMAND, "/faction join ${faction.id.value} -f")
            }
        )
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): List<String> {
        val factionService = plugin.services.factionService
        return when {
            args.isEmpty() -> factionService.factions.map(MfFaction::name)
            args.size == 1 ->
                factionService.factions
                    .filter { it.name.lowercase().startsWith(args[0].lowercase()) }
                    .map(MfFaction::name)
            else -> emptyList()
        }
    }
}
