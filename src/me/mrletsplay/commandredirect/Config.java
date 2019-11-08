package me.mrletsplay.commandredirect;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

	public static FileConfiguration config = Main.getPlugin().getConfig();

	public static void save() {
		Main.getPlugin().saveConfig();
	}

	public static void init() {
		if(!config.getBoolean("init")) {
			config.addDefault("init", true);
			config.addDefault("redirections./plugins.world", "/help");
			config.addDefault("redirections./plugins.case-sensitive", false);
			config.addDefault("redirections./reload.world", "/help");
			config.addDefault("redirections./reload.case-sensitive", false);
			config.addDefault("global-redirections./mycommand.redirection", "/help");
			config.addDefault("global-redirections./mycommand.case-sensitive", false);
			config.options().copyDefaults(true);
			save();
		}
	}

	public static void reloadConfig() {
		Main.getPlugin().reloadConfig();
		config = Main.getPlugin().getConfig();
	}
	
	public static Set<String> getRedirectedCommands() {
		Set<String> strs = new HashSet<>();
		MemorySection reds = (MemorySection) config.get("redirections");
		if(reds != null) strs.addAll(reds.getKeys(false));
		MemorySection gReds = (MemorySection) config.get("global-redirections");
		if(gReds != null) strs.addAll(gReds.getKeys(false));
		return strs;
	}
	
	public static void addGlobalRedirection(String cmd, String toCmd) {
		config.set("global-redirections." + cmd + ".redirection", toCmd);
	}

	public static void addRedirection(String world, String cmd, String toCmd) {
		config.set("redirections." + cmd + "." + world, toCmd);
		save();
	}

	public static boolean isGlobalCaseSensitive(String cmdLC) {
		return config.getBoolean("global-redirections." + cmdLC + ".case-sensitive");
	}

	public static boolean isCaseSensitive(String cmdLC) {
		return config.getBoolean("redirections." + cmdLC + ".case-sensitive");
	}

	public static boolean hasRedirection(String world, String cmd) {
		return getRedirection(world, cmd) != null
				|| getGlobalRedirection(cmd) != null;
	}

	public static String getGlobalRedirection(String cmd) {
		return config.getString("global-redirections." + (isGlobalCaseSensitive(cmd.toLowerCase()) ? cmd : cmd.toLowerCase()) + ".redirection");
	}

	public static String getRedirection(String world, String cmd) {
		String red = config.getString("redirections." + (isCaseSensitive(cmd.toLowerCase()) ? cmd : cmd.toLowerCase()) + "." + world);
		if(red != null) return red;
		return getGlobalRedirection(cmd);
	}

}
