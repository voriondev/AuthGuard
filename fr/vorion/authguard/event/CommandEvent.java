package fr.vorion.authguard.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import fr.vorion.authguard.Main;

public class CommandEvent implements Listener
{

	public Main main;

	public CommandEvent(Main m)
	{
		main = m;
	}

	@EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
	{
		Player player = event.getPlayer();

		if(main.getUnconfirmedPlayers().contains(player))
		{
			if(!event.getMessage().startsWith("/code"))
			{
				event.setCancelled(true);
			}
		}
	}

}
