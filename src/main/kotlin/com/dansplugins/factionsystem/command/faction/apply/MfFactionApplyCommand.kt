package com.dansplugins.factionsystem.command.faction.apply

import com.dansplugins.factionsystem.MedievalFactions
import com.dansplugins.factionsystem.command.faction.apply.tasks.CancelApplicationTask
import com.dansplugins.factionsystem.command.faction.apply.tasks.SendApplicationTask
import net.md_5.bungee.api.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class MfFactionApplyCommand(private val plugin: MedievalFactions) : CommandExecutor, TabCompleter {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (!sender.hasPermission("mf.apply")) {
            sender.sendMessage("${ChatColor.RED}${plugin.language["CommandFactionApplyNoPermission"]}")
            return true
        }
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}${plugin.language["CommandFactionApplyNotAPlayer"]}")
            return true
        }
        if (args.isEmpty()) {
            sender.sendMessage("${ChatColor.RED}${plugin.language["CommandFactionApplyUsage"]}")
            return true
        }
        val targetFactionName = args.dropLast(1).joinToString(" ")
        val lastArg = args.last()
        if (lastArg.equals("cancel", ignoreCase = true)) {
            cancelApplication(sender, targetFactionName)
        } else {
            sendApplication(sender, args.joinToString(" "))
        }
        return true
    }

    private fun sendApplication(sender: Player, targetFactionName: String) {
        plugin.logger.info("Player " + sender.name + " is applying to faction " + targetFactionName)
        plugin.server.scheduler.runTaskAsynchronously(
            plugin,
            SendApplicationTask(plugin, sender, targetFactionName)
        )
    }

    private fun cancelApplication(sender: Player, targetFactionName: String) {
        plugin.logger.info("Player " + sender.name + " is cancelling application to faction " + targetFactionName)
        plugin.server.scheduler.runTaskAsynchronously(
            plugin,
            CancelApplicationTask(plugin, sender, targetFactionName)
        )
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        if (args.size == 1) {
            val factionService = plugin.services.factionService
            val factions = factionService.factions
            return factions.map { it.name }.filter { it.startsWith(args[0], ignoreCase = true) }.toMutableList()
        }
        return null
    }
}
