package me.mrletsplay.commandredirect;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	private static Plugin plugin;

	public void onEnable(){
		plugin = this;
		Config.init();
		Bukkit.getPluginManager().registerEvents(new Events(), this);
		getLogger().info("Enabled");
	}
	
	public void onDisable(){
		getLogger().info("Disabled");
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		if(command.getName().equalsIgnoreCase("reloadcommandredirect")){
			if(sender.hasPermission("commandredirect.reload")){
				Config.reloadConfig();
				Bukkit.getPluginManager().disablePlugin(this);
				Bukkit.getPluginManager().enablePlugin(this);
				sender.sendMessage("§aReload successful");
				return true;
			}else{
				sender.sendMessage("§cNope");
				return true;
			}
		}
		return false;
	}
	
}