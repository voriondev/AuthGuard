package fr.vorion.authguard.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import fr.vorion.authguard.Main;

public class DamageEvent implements Listener
{

	public Main main;

	public DamageEvent(Main m)
	{
		main = m;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onPlayerDamage(EntityDamageEvent event)
	{
		if(event.getEntity() instanceof Player)
		{
			Player player = (Player) event.getEntity();

			if(main.getUnconfirmedPlayers().contains(player))
			{
				event.setCancelled(true);
			}
		}
		else
		{
			event.setCancelled(false);
		}
	}


}
