package com.dansplugins.factionsystem.command.faction.create

import com.dansplugins.factionsystem.MedievalFactions
import com.dansplugins.factionsystem.faction.MfFaction
import com.dansplugins.factionsystem.faction.MfFactionId
import com.dansplugins.factionsystem.faction.role.MfFactionRoles
import com.dansplugins.factionsystem.faction.withRole
import com.dansplugins.factionsystem.player.MfPlayer
import dev.forkhandles.result4k.onFailure
import org.bukkit.ChatColor.GREEN
import org.bukkit.ChatColor.RED
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import java.util.logging.Level.SEVERE

class MfFactionCreateCommand(private val plugin: MedievalFactions) : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("$RED${plugin.language["CommandFactionCreateNotAPlayer"]}")
            return true
        }
        if (!sender.hasPermission("mf.create")) {
            sender.sendMessage("$RED${plugin.language["CommandFactionCreateNoPermission"]}")
            return true
        }
        val maxFactionNameLength = plugin.config.getInt("factions.maxNameLength")
        plugin.server.scheduler.runTaskAsynchronously(
            plugin,
            Runnable {
                val playerService = plugin.services.playerService
                val mfPlayer = playerService.getPlayer(sender)
                    ?: playerService.save(MfPlayer(plugin, sender)).onFailure {
                        sender.sendMessage("$RED${plugin.language["CommandFactionCreateFailedToSavePlayer"]}")
                        plugin.logger.log(SEVERE, "Failed to save player: ${it.reason.message}", it.reason.cause)
                        return@Runnable
                    }
                val factionService = plugin.services.factionService
                val existingFaction = factionService.getFaction(mfPlayer.id)
                if (existingFaction != null) {
                    sender.sendMessage("$RED${plugin.language["CommandFactionCreateAlreadyInFaction"]}")
                    return@Runnable
                }
                if (args.isEmpty()) {
                    sender.sendMessage("$RED${plugin.language["CommandFactionCreateUsage"]}")
                    return@Runnable
                }
                val factionName = args.joinToString(" ")
                if (factionName.length > maxFactionNameLength) {
                    sender.sendMessage("$RED${plugin.language["CommandFactionCreateNameTooLong", maxFactionNameLength.toString()]}")
                    return@Runnable
                }
                if (factionService.getFaction(factionName) != null) {
                    sender.sendMessage("$RED${plugin.language["CommandFactionCreateFactionAlreadyExists"]}")
                    return@Runnable
                }
                val factionId = MfFactionId.generate()
                val roles = MfFactionRoles.defaults(plugin, factionId)
                val owner = roles.single { it.name == "Owner" }
                val faction = MfFaction(plugin, id = factionId, name = factionName, roles = roles, members = listOf(mfPlayer.withRole(owner)))
                val createdFaction = factionService.save(faction).onFailure { failure ->
                    sender.sendMessage("$RED${plugin.language["CommandFactionCreateFactionFailedToSave"]}")
                    plugin.logger.log(SEVERE, "Failed to save faction: ${failure.reason.message}", failure.reason.cause)
                    return@Runnable
                }
                sender.sendMessage("$GREEN${plugin.language["CommandFactionCreateSuccess", createdFaction.name]}")
                try {
                    factionService.cancelAllApplicationsForPlayer(mfPlayer)
                } catch (e: Exception) {
                    sender.sendMessage("${org.bukkit.ChatColor.RED}${plugin.language["CommandFactionCreateFailedToCancelApplications"]}") // TODO: add to language file
                    plugin.logger.log(SEVERE, "Failed to cancel applications: ${e.message}", e)
                }
            }
        )
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ) = emptyList<String>()
}
