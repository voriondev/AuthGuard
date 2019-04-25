package fr.vorion.authguard.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import fr.vorion.authguard.Main;
import fr.xephi.authme.events.LoginEvent;

public class LogEvent implements Listener
{

	public Main main;

	public LogEvent(Main m)
	{
		main = m;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerAuth(LoginEvent event)
	{
		Player player = event.getPlayer();

		if(!main.credentials.getSecretKey(player.getName()).equals("NullPointerExceptionOccured"))
		{
			String ipAddress = player.getAddress().getAddress().toString().replaceAll("/", "");
			if(!main.credentials.checkAddress(player.getName(), ipAddress))
			{
				main.getUnconfirmedPlayers().add(player);
				player.sendMessage("§eVous accédez à votre compte depuis une nouvelle adresse IP.");
				player.sendMessage("§eAfin de pouvoir vous connecter, vous devez entrer votre code de sécurité.");
				player.sendMessage("§eVotre code de sécurité doit s'afficher sur votre téléphone.");
				player.sendMessage("§eOuvrez l'application Google Authenticator pour le récupérer.");
				player.sendMessage("§ePour se faire, utiliser /code <code> !");
			}
			else
			{
				player.sendMessage("§aVotre adresse IP a été reconnue, bon jeu !");
			}
		}
	}

}
