package me.dylangunn.fortuneplus;

import java.util.logging.Level;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class FortunePlus extends JavaPlugin {
	public void onEnable() {
		getLogger().log(Level.INFO, String.valueOf(getDescription().getName()) + " v" + getDescription().getVersion()
				+ " has been enabled!");
		getServer().getPluginManager().registerEvents(new FortunePlusListener(), (Plugin) this);
	}

	public void onDisable() {
		getLogger().log(Level.INFO, String.valueOf(getDescription().getName()) + " v" + getDescription().getVersion()
				+ " has been disabled!");
	}

	public boolean onCommand(CommandSender sender, Command com, String command, String[] args) {
		if (sender instanceof Player) {
			if (command.equalsIgnoreCase("fortuneplus") || command.equalsIgnoreCase("fp")) {
				if (((Player) sender).hasPermission("fortuneplus.iron_ore")) {
					((Player) sender).sendMessage(ChatColor.GREEN + "FortunePlus works for " + ChatColor.GRAY + "IRON"
							+ ChatColor.GREEN + "!");
				} else {
					((Player) sender).sendMessage(
							ChatColor.RED + "FortunePlus works for " + ChatColor.GRAY + "IRON" + ChatColor.RED + "!");
				}
				if (((Player) sender).hasPermission("fortuneplus.gold_ore")) {
					((Player) sender).sendMessage(ChatColor.GREEN + "FortunePlus works for " + ChatColor.GOLD + "GOLD"
							+ ChatColor.GREEN + "!");
				} else {
					((Player) sender).sendMessage(
							ChatColor.RED + "FortunePlus works for " + ChatColor.GOLD + "GOLD" + ChatColor.RED + "!");
				}
				return true;
			}
			return false;
		}
		getLogger().log(Level.WARNING, "Only Players can use this command!");
		return true;
	}
}
