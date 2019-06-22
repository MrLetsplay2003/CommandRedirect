package me.mrletsplay.commandredirect;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class Events implements Listener {

	@EventHandler
	public void onCmd(PlayerCommandPreprocessEvent e) {
		String cmd = null;
		for(String c2 : Config.getCMDs()) {
			if(e.getMessage().toLowerCase().startsWith(c2.toLowerCase()) && (cmd == null || c2.length() > cmd.length())) cmd = e.getMessage().substring(0, c2.length()); // Preserve original case
		}
		if(cmd == null) return;
		boolean cs = Config.isCaseSensitive(cmd, cmd.toLowerCase());
		if (Config.hasRedirection(e.getPlayer().getWorld().getName(), cs ? cmd : cmd.toLowerCase())) {
			e.setCancelled(true);
			e.getPlayer().chat(Config.getRedirection(e.getPlayer().getWorld().getName(), cs ? cmd : cmd.toLowerCase()) + e.getMessage().substring(cmd.length()));
		}
	}

}