package me.mrletsplay.commandredirect;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class Events implements Listener {

	@EventHandler
	public void onCmd(PlayerCommandPreprocessEvent e) {
		String[] spl = e.getMessage().split(" ");
		String cmd = spl[0];
		boolean cs = Config.isCaseSensitive(cmd, cmd.toLowerCase());
		if (Config.hasRedirection(e.getPlayer().getWorld().getName(), cs ? cmd : cmd.toLowerCase())) {
			e.setCancelled(true);
			e.getPlayer().chat(Config.getRedirection(e.getPlayer().getWorld().getName(), cs ? cmd : cmd.toLowerCase()) + e.getMessage().substring(cmd.length()));
		}
	}

}