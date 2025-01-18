package com.dansplugins.factionsystem.command.faction.showapps.tasks

import com.dansplugins.factionsystem.MedievalFactions
import net.md_5.bungee.api.ChatColor
import org.bukkit.entity.Player

class ShowAppsForPlayersFactionTask(
    private val plugin: MedievalFactions,
    private val sender: Player
) : Runnable {

    override fun run() {
        val factionService = plugin.services.factionService
        val playerService = plugin.services.playerService

        val mfPlayer = playerService.getPlayer(sender)
        if (mfPlayer == null) {
            sender.sendMessage("${ChatColor.RED}${plugin.language["CommandFactionShowAppsPlayerNotFound"]}")
            return
        }

        val faction = factionService.getFaction(mfPlayer.id)
        if (faction == null) {
            sender.sendMessage("${ChatColor.RED}${plugin.language["CommandFactionShowAppsFactionNotFound"]}")
            return
        }

        val applications = faction.applications
        if (applications.isEmpty()) {
            sender.sendMessage("${ChatColor.YELLOW}${plugin.language["CommandFactionShowAppsNoApplications"]}")
            return
        }

        sender.sendMessage("${ChatColor.GREEN}${plugin.language["CommandFactionShowAppsHeader"]}")
        for (application in applications) {
            val applicant = playerService.getPlayer(application.applicantId)
            if (applicant != null) {
                sender.sendMessage("${ChatColor.AQUA}${applicant.name}")
            }
        }
    }
}
