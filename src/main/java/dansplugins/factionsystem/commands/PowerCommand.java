package dansplugins.factionsystem.commands;

import dansplugins.factionsystem.data.PersistentData;
import dansplugins.factionsystem.objects.PlayerPowerRecord;
import dansplugins.factionsystem.utils.Utilities;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PowerCommand {

    public boolean powerCheck(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("mf.power") || sender.hasPermission("mf.default"))
            {
            	if (args.length > 1)
            	{
            		UUID playerUUID = Utilities.getInstance().findUUIDBasedOnPlayerName(args[1]);
            		if (playerUUID != null)
            		{
	            		PlayerPowerRecord record = Utilities.getInstance().getPlayersPowerRecord(playerUUID, PersistentData.getInstance().getPlayerPowerRecords());
	                    player.sendMessage(ChatColor.AQUA + args[1] + "'s current power level is " + record.getPowerLevel() + "/" + record.maxPower() + ".");
	                    return true;
            		}
            		else
            		{
            			sender.sendMessage(ChatColor.RED + "Sorry! Player by the name of '" + args[1] + "' could not be found.");
            			return false;
            		}
            	}
            	else
            	{
                    PlayerPowerRecord record = Utilities.getInstance().getPlayersPowerRecord(player.getUniqueId(), PersistentData.getInstance().getPlayerPowerRecords());
                    player.sendMessage(ChatColor.AQUA + "Your current power level is " + record.getPowerLevel() + "/" + record.maxPower() + ".");
                    return true;
            	}
            }
            else {
                sender.sendMessage(ChatColor.RED + "Sorry! You need the following permission to use this command: 'mf.power'");
            }
        }
        return false;
    }
}