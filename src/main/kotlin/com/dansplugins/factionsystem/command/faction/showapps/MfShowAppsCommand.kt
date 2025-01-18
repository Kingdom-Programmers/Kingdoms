package com.dansplugins.factionsystem.command.faction.showapps

import com.dansplugins.factionsystem.MedievalFactions
import com.dansplugins.factionsystem.command.faction.showapps.tasks.ShowAppsForPlayersFactionTask
import net.md_5.bungee.api.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

/**
 * Shows the applications for the sender's faction.
 */
class MfShowAppsCommand(private val plugin: MedievalFactions) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.hasPermission("mf.showapps")) {
            sender.sendMessage("${ChatColor.RED}${plugin.language["CommandFactionShowAppsNoPermission"]}")
            return true
        }
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}${plugin.language["CommandFactionShowAppsNotAPlayer"]}")
            return true
        }
        showApps(sender)
        return true
    }

    private fun showApps(sender: Player) {
        plugin.logger.info("Player " + sender.name + " is viewing applications for their faction")
        plugin.server.scheduler.runTaskAsynchronously(
            plugin,
            ShowAppsForPlayersFactionTask(plugin, sender)
        )
    }
}
