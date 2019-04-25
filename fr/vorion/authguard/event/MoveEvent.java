package fr.vorion.authguard.event;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.vorion.authguard.Main;

public class MoveEvent implements Listener
{

	public Main main;

	public MoveEvent(Main m)
	{
		main = m;
	}

	@EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerMove(PlayerMoveEvent event)
    {
		Player player = event.getPlayer();

		if(main.getUnconfirmedPlayers().contains(player))
		{
			Location loc = event.getPlayer().getLocation();
			event.getPlayer().teleport(loc);
		}
    }

}
