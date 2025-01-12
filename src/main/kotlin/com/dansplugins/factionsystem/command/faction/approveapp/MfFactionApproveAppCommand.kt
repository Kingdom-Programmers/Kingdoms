package com.dansplugins.factionsystem.command.faction.approveapp

import com.dansplugins.factionsystem.MedievalFactions
import com.dansplugins.factionsystem.command.faction.approveapp.tasks.ApproveApplicationTask
import net.md_5.bungee.api.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class MfFactionApproveAppCommand(private val plugin: MedievalFactions) : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {
        if (!sender.hasPermission("mf.approveapp")) {
            sender.sendMessage("${ChatColor.RED}${plugin.language["CommandFactionApproveAppNoPermission"]}")
            return true
        }
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}${plugin.language["CommandFactionApproveAppNotAPlayer"]}")
            return true
        }
        if (args == null || args.size != 1) {
            sender.sendMessage("${ChatColor.RED}${plugin.language["CommandFactionApproveAppUsage"]}")
            return true
        }
        val targetPlayerName = args[0]
        plugin.logger.info("Player ${sender.name} is approving application for player $targetPlayerName")
        plugin.server.scheduler.runTaskAsynchronously(
            plugin,
            ApproveApplicationTask(plugin, sender, targetPlayerName)
        )
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>?
    ): MutableList<String>? {
        if (!sender.hasPermission("mf.approveapp")) {
            return mutableListOf()
        }
        if (sender !is Player) {
            return mutableListOf()
        }
        if (command.name != "approveapp") {
            return mutableListOf()
        }
        if (args == null || args.isEmpty() || args[0].isNotEmpty()) {
            return mutableListOf()
        }
        val onlinePlayers = plugin.server.onlinePlayers.map { it.name }
        return onlinePlayers.filter { it.startsWith(args[0], ignoreCase = true) }.toMutableList()
    }
}
