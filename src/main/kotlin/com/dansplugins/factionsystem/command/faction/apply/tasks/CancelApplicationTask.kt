package com.dansplugins.factionsystem.command.faction.apply.tasks
import com.dansplugins.factionsystem.MedievalFactions
import com.dansplugins.factionsystem.player.MfPlayer
import dev.forkhandles.result4k.onFailure
import net.md_5.bungee.api.ChatColor
import org.bukkit.entity.Player
import java.util.logging.Level.SEVERE

class CancelApplicationTask(
    private val plugin: MedievalFactions,
    private val sender: Player,
    private val targetFactionName: String
) : Runnable {

    override fun run() {
        val factionService = plugin.services.factionService
        val playerService = plugin.services.playerService
        val mfPlayer = playerService.getPlayer(sender)
            ?: playerService.save(MfPlayer(plugin, sender)).onFailure {
                sender.sendMessage("${ChatColor.RED}${plugin.language["CommandFactionApplyCancelFailedToSaveFaction"]}")
                plugin.logger.log(SEVERE, "Failed to save player: ${it.reason.message}", it.reason.cause)
                return
            }
        val target = factionService.getFaction(targetFactionName)
        if (target == null) {
            sender.sendMessage("${ChatColor.RED}${plugin.language["CommandFactionApplyCancelInvalidTarget"]}")
            return
        }

        // if the player has not applied to the faction, do nothing
        if (!target.applications.any { it.applicantId == mfPlayer.id }) {
            sender.sendMessage("${ChatColor.RED}${plugin.language["CommandFactionApplyCancelNotApplied"]}")
            return
        }

        val updatedApplications = target.applications.filterNot { it.applicantId == mfPlayer.id }
        factionService.save(target.copy(applications = updatedApplications)).onFailure {
            sender.sendMessage("${ChatColor.RED}${plugin.language["CommandFactionApplyCancelFailedToSaveFaction"]}")
            plugin.logger.log(SEVERE, "Failed to save faction: ${it.reason.message}", it.reason.cause)
            return
        }
        sender.sendMessage("${ChatColor.GREEN}${plugin.language["CommandFactionApplyCancelSuccess", target.name]}")
    }
}
