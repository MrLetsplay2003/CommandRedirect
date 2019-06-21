package me.mrletsplay.commandredirect;

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
			config.options().copyDefaults(true);
			save();
		}
	}

	public static void reloadConfig() {
		Main.getPlugin().reloadConfig();
		config = Main.getPlugin().getConfig();
	}
	
	public static Set<String> getCMDs() {
		return ((MemorySection) config.get("redirections")).getKeys(false);
	}

	public static void addRedirection(String world, String cmd, String toCmd) {
		config.set("redirections." + cmd + "." + world, toCmd);
		save();
	}

	public static boolean isCaseSensitive(String world, String cmdLC) {
		return config.getBoolean("redirections." + cmdLC + ".case-sensitive");
	}

	public static boolean hasRedirection(String world, String cmd) {
		return getRedirection(world, cmd) != null;
	}

	public static String getRedirection(String world, String cmd) {
		return config.getString("redirections." + cmd + "." + world);
	}

}
