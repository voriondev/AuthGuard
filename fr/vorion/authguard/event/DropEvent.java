package fr.vorion.authguard.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import fr.vorion.authguard.Main;

public class DropEvent implements Listener
{

	public Main main;

	public DropEvent(Main m)
	{
		main = m;
	}

	@EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDropItem(PlayerDropItemEvent event)
	{
		Player player = event.getPlayer();

		if(main.getUnconfirmedPlayers().contains(player))
		{
			event.setCancelled(true);
		}
	}

}
