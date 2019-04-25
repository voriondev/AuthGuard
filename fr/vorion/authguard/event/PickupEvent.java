package fr.vorion.authguard.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import fr.vorion.authguard.Main;

public class PickupEvent implements Listener
{

	public Main main;

	public PickupEvent(Main m)
	{
		main = m;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerPickupItem(PlayerPickupItemEvent event)
	{
		Player player = event.getPlayer();

		if(main.getUnconfirmedPlayers().contains(player))
		{
			event.setCancelled(true);
		}
	}

}
