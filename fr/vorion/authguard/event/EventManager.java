package fr.vorion.authguard.event;

import org.bukkit.plugin.PluginManager;

import fr.vorion.authguard.Main;

public class EventManager
{

	public Main main;

	public EventManager(Main m)
	{
		main = m;
	}

	public void registerEvents()
	{
		PluginManager pm = main.getServer().getPluginManager();

		pm.registerEvents(new ChatEvent(main), main);
		pm.registerEvents(new CommandEvent(main), main);
		pm.registerEvents(new DamageEvent(main), main);
		pm.registerEvents(new DropEvent(main), main);
		pm.registerEvents(new FoodEvent(main), main);
		pm.registerEvents(new InteractEvent(main), main);
		pm.registerEvents(new LogEvent(main), main);
		pm.registerEvents(new MoveEvent(main), main);
		pm.registerEvents(new PickupEvent(main), main);

	}

}
