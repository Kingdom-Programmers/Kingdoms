package com.dansplugins.factionsystem.command.faction.approveapp.tasks

import com.dansplugins.factionsystem.MedievalFactions
import com.dansplugins.factionsystem.faction.MfFactionMember
import com.dansplugins.factionsystem.player.MfPlayer
import dev.forkhandles.result4k.onFailure
import net.md_5.bungee.api.ChatColor
import org.bukkit.entity.Player
import java.util.logging.Level

class ApproveApplicationTask(
    private val plugin: MedievalFactions,
    private val sender: Player,
    private val targetPlayerName: String
) : Runnable {

    override fun run() {
        val factionService = plugin.services.factionService
        val playerService = plugin.services.playerService
        val mfPlayer = playerService.getPlayer(sender)
            ?: playerService.save(MfPlayer(plugin, sender)).onFailure {
                sender.sendMessage("${ChatColor.RED}${plugin.language["CommandFactionApproveAppFailedToSavePlayer"]}")
                plugin.logger.log(Level.SEVERE, "Failed to save player: ${it.reason.message}", it.reason.cause)
                return
            }
        val faction = factionService.getFaction(mfPlayer.id)
        val targetPlayer = plugin.server.getOfflinePlayer(targetPlayerName)
        if (!targetPlayer.isOnline && !targetPlayer.hasPlayedBefore()) {
            sender.sendMessage("${org.bukkit.ChatColor.RED}${plugin.language["CommandFactionApproveAppInvalidTarget"]}")
            return
        }
        val targetMfPlayer = playerService.getPlayer(targetPlayer)
            ?: playerService.save(MfPlayer(plugin, targetPlayer)).onFailure {
                sender.sendMessage("${org.bukkit.ChatColor.RED}${plugin.language["CommandFactionApproveAppFailedToSaveTargetPlayer"]}")
                plugin.logger.log(Level.SEVERE, "Failed to save player: ${it.reason.message}", it.reason.cause)
                return
            }
        val application = faction?.applications?.find { it.applicantId == targetMfPlayer.id }
        if (application == null) {
            sender.sendMessage("${org.bukkit.ChatColor.RED}${plugin.language["CommandFactionApproveAppNoApplication"]}")
            return
        }
        val role = faction.getRole(mfPlayer.id)
        if (role == null || !role.hasPermission(faction, plugin.factionPermissions.approveApp)) {
            sender.sendMessage("${org.bukkit.ChatColor.RED}${plugin.language["CommandFactionApproveAppNoPermission"]}") // TODO: add to language file
            return
        }
        if (faction.members.size >= plugin.config.getInt("max-members")) {
            sender.sendMessage("${org.bukkit.ChatColor.RED}${plugin.language["CommandFactionApproveAppMaxMembers"]}")
            return
        }
        val updatedFaction = factionService.save(
            faction.copy(
                members = faction.members + MfFactionMember(targetMfPlayer.id, faction.roles.default),
                applications = faction.applications.filter { it.applicantId != targetMfPlayer.id }
            )
        ).onFailure {
            sender.sendMessage("${org.bukkit.ChatColor.RED}${plugin.language["CommandFactionApproveAppFailedToSaveFaction"]}")
            plugin.logger.log(Level.SEVERE, "Failed to save faction: ${it.reason.message}", it.reason.cause)
            return
        }
        targetPlayer.player?.sendMessage("${org.bukkit.ChatColor.GREEN}${plugin.language["CommandFactionApproveAppApplicationAccepted"]}")
        sender.sendMessage("${org.bukkit.ChatColor.GREEN}${plugin.language["CommandFactionApproveAppApplicationAccepted"]} ${faction.name}")
        updatedFaction.sendMessage(
            "${org.bukkit.ChatColor.GREEN}${plugin.language["CommandFactionApproveAppApplicationAccepted"]}",
            "${targetMfPlayer.name} ${plugin.language["CommandFactionApproveAppApplicationAcceptedMessage"]}"
        )
        try {
            factionService.cancelAllApplicationsForPlayer(mfPlayer)
        } catch (e: Exception) {
            sender.sendMessage("${org.bukkit.ChatColor.RED}${plugin.language["CommandFactionApproveAppFailedToCancelApplications"]}") // TODO: add to language file
            plugin.logger.log(Level.SEVERE, "Failed to cancel applications: ${e.message}", e)
        }
    }
}
