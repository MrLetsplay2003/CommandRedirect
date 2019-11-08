package me.mrletsplay.commandredirect;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class Events implements Listener {

	@EventHandler
	public void onCmd(PlayerCommandPreprocessEvent e) {
		String cmd = null;
		for(String c2 : Config.getRedirectedCommands()) {
			if((e.getMessage().toLowerCase().startsWith(c2.toLowerCase() + " ") || e.getMessage().toLowerCase().equals(c2.toLowerCase())) && (cmd == null || c2.length() > cmd.length())) cmd = e.getMessage().substring(0, c2.length()); // Preserve original case
		}
		if(cmd == null) return;
		if(Config.hasRedirection(e.getPlayer().getWorld().getName(), cmd)) {
			e.setCancelled(true);
			e.getPlayer().chat(Config.getRedirection(e.getPlayer().getWorld().getName(), cmd) + e.getMessage().substring(cmd.length()));
		}
	}

}