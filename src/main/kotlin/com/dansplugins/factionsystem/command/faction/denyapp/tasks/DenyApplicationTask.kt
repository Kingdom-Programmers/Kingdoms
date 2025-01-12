package com.dansplugins.factionsystem.command.faction.denyapp.tasks

import com.dansplugins.factionsystem.MedievalFactions
import com.dansplugins.factionsystem.player.MfPlayer
import dev.forkhandles.result4k.onFailure
import net.md_5.bungee.api.ChatColor
import org.bukkit.entity.Player
import java.util.logging.Level

class DenyApplicationTask(
    private val plugin: MedievalFactions,
    private val sender: Player,
    private val targetPlayerName: String
) : Runnable {

    override fun run() {
        val factionService = plugin.services.factionService
        val playerService = plugin.services.playerService
        val mfPlayer = playerService.getPlayer(sender)
            ?: playerService.save(MfPlayer(plugin, sender)).onFailure {
                sender.sendMessage("${ChatColor.RED}${plugin.language["CommandFactionDenyAppFailedToSavePlayer"]}")
                plugin.logger.log(Level.SEVERE, "Failed to save player: ${it.reason.message}", it.reason.cause)
                return
            }
        val faction = factionService.getFaction(mfPlayer.id)
        val targetPlayer = plugin.server.getOfflinePlayer(targetPlayerName)
        if (!targetPlayer.isOnline && !targetPlayer.hasPlayedBefore()) {
            sender.sendMessage("${org.bukkit.ChatColor.RED}${plugin.language["CommandFactionDenyAppInvalidTarget"]}")
            return
        }
        val targetMfPlayer = playerService.getPlayer(targetPlayer)
            ?: playerService.save(MfPlayer(plugin, targetPlayer)).onFailure {
                sender.sendMessage("${org.bukkit.ChatColor.RED}${plugin.language["CommandFactionDenyAppFailedToSaveTargetPlayer"]}")
                plugin.logger.log(Level.SEVERE, "Failed to save player: ${it.reason.message}", it.reason.cause)
                return
            }
        val application = faction?.applications?.find { it.applicantId == targetMfPlayer.id }
        if (application == null) {
            sender.sendMessage("${org.bukkit.ChatColor.RED}${plugin.language["CommandFactionDenyAppNoApplication"]}")
            return
        }
        val role = faction.getRole(mfPlayer.id)
        if (role == null || !role.hasPermission(faction, plugin.factionPermissions.denyApp)) {
            sender.sendMessage("${org.bukkit.ChatColor.RED}${plugin.language["CommandFactionDenyAppNoPermission"]}") // TODO: add to language file
            return
        }

        // Remove the player's application record from the faction
        val updatedFaction = faction.copy(applications = faction.applications.filter { it.applicantId != targetMfPlayer.id })
        factionService.save(updatedFaction).onFailure {
            sender.sendMessage("${org.bukkit.ChatColor.RED}${plugin.language["CommandFactionDenyAppFailedToRemoveApplication"]}")
            plugin.logger.log(Level.SEVERE, "Failed to remove application: ${it.reason.message}", it.reason.cause)
            return
        }

        // Inform the applicant that their application has been denied
        if (targetPlayer.isOnline) {
            targetPlayer.player?.sendMessage("${org.bukkit.ChatColor.RED}${plugin.language["CommandFactionDenyAppApplicationDenied"]} ${faction.name}")
        }
        sender.sendMessage("${org.bukkit.ChatColor.GREEN}${plugin.language["CommandFactionDenyAppApplicationDeniedMessage"]} $targetPlayerName")
    }
}
