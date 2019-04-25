package fr.vorion.authguard.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import fr.vorion.authguard.Main;

public class ChatEvent implements Listener
{

	public Main main;

	public ChatEvent(Main m)
	{
		main = m;
	}

	@EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(AsyncPlayerChatEvent event)
	{
		Player player = event.getPlayer();

		if(main.getUnconfirmedPlayers().contains(player))
		{
			event.setCancelled(true);
		}
	}

}
