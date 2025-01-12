package com.dansplugins.factionsystem.command.faction.apply.tasks

import com.dansplugins.factionsystem.MedievalFactions
import com.dansplugins.factionsystem.faction.MfFaction
import com.dansplugins.factionsystem.faction.MfFactionApplication
import com.dansplugins.factionsystem.faction.MfFactionService
import com.dansplugins.factionsystem.player.MfPlayer
import com.dansplugins.factionsystem.player.MfPlayerService
import dev.forkhandles.result4k.onFailure
import net.md_5.bungee.api.ChatColor
import org.bukkit.entity.Player
import java.util.logging.Level.SEVERE

class SendApplicationTask(
    private val plugin: MedievalFactions,
    private val sender: Player,
    private val targetFactionName: String
) : Runnable {

    override fun run() {
        val factionService = plugin.services.factionService
        val playerService = plugin.services.playerService

        val mfPlayer = getOrSavePlayer(playerService, sender) ?: return
        val faction = factionService.getFaction(mfPlayer.id)
        val target = getTargetFaction(factionService, targetFactionName, sender) ?: return

        if (faction != null && target.id.value == faction.id.value) {
            sender.sendMessage("${ChatColor.RED}${plugin.language["CommandFactionApplyCannotApplyToCurrentFaction"]}")
            return
        }

        saveFactionApplication(factionService, target, mfPlayer, sender)
    }

    private fun getOrSavePlayer(playerService: MfPlayerService, sender: Player): MfPlayer? {
        return playerService.getPlayer(sender) ?: playerService.save(MfPlayer(plugin, sender)).onFailure {
            sender.sendMessage("${ChatColor.RED}${plugin.language["CommandFactionApplyFailedToSavePlayer"]}")
            plugin.logger.log(SEVERE, "Failed to save player: ${it.reason.message}", it.reason.cause) as Nothing
        }
    }

    private fun getTargetFaction(factionService: MfFactionService, targetFactionName: String, sender: Player): MfFaction? {
        val target = factionService.getFaction(targetFactionName)
        if (target == null) {
            sender.sendMessage("${ChatColor.RED}${plugin.language["CommandFactionApplyInvalidTarget"]}")
        }
        return target
    }

    private fun saveFactionApplication(factionService: MfFactionService, target: MfFaction, mfPlayer: MfPlayer, sender: Player) {
        factionService.save(target.copy(applications = target.applications + MfFactionApplication(target.id, mfPlayer.id))).onFailure {
            sender.sendMessage("${ChatColor.RED}${plugin.language["CommandFactionApplyFailedToSaveFaction"]}")
            plugin.logger.log(SEVERE, "Failed to save faction: ${it.reason.message}", it.reason.cause) as Nothing
        }
    }
}
