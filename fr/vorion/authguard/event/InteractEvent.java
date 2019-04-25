package fr.vorion.authguard.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.vorion.authguard.Main;

public class InteractEvent implements Listener
{

	public Main main;

	public InteractEvent(Main m)
	{
		main = m;
	}

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent event)
    {
		Player player = event.getPlayer();

    	if(main.getUnconfirmedPlayers().contains(player))
    	{
    		event.setCancelled(true);
    	}
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event)
    {
		Player player = event.getPlayer();

    	if(main.getUnconfirmedPlayers().contains(player))
    	{
    		event.setCancelled(true);
    	}
    }

}
