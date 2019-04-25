package fr.vorion.authguard.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

import fr.vorion.authguard.Main;

public class FoodEvent implements Listener
{

	public Main main;

	public FoodEvent(Main m)
	{
		main = m;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onFoodLevelChange(FoodLevelChangeEvent event)
	{
		Player player = (Player) event.getEntity();
		if(main.getUnconfirmedPlayers().contains(player))
		{
			event.setCancelled(true);
		}
	}

}
